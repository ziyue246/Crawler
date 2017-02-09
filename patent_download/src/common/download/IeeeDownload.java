package common.download;

import java.util.ArrayList;
import java.util.List;

import common.bean.IEEEinfo;
import common.extractor.IeeeExtract;
import common.http.HtmlInfo;
import common.http.sub.Httpieee;
import common.service.mysql.ConnectSqlIeee_data;
import common.system.SystemCommon;
import common.util.MD5Util;
import net.sf.json.JSONObject;

public class IeeeDownload{

	public void getContentStepOne(HtmlInfo html, Httpieee http, String keyword) {

		html.setEncode("UTF-8");
		html.setUa(SystemCommon.User_Agent);
		if (html.getCookie() == null)
			http.getCookie(html);

		String searchUrl = "http://ieeexplore.ieee.org/search/searchresult.jsp?"
				+ "action=search&sortType=&rowsPerPage=&searchField=Search_All&matchBoolean=true&"
				+ "queryText=(%22Document%20Title%22:" + SystemCommon.urlEncode(keyword, "utf-8")
				+ ")&refinements=4291944822";
		// searchUrl = searchUrl.replace(" ","%20");
		// searchUrl = URLEncoder.encode("searchUrl", "utf-8");
		html.setRealUrl(searchUrl);
		http.simpleGet(html);
		System.out.println("2 get cookie:" + html.getCookie());
		getListPageContent(html, http, keyword, 1);
	}

	public void getListPageContent(HtmlInfo html, Httpieee http, String keyword, int pageNum) {
		String postJsonUrl = "http://ieeexplore.ieee.org/rest/search";
		html.setRealUrl(postJsonUrl);
		JSONObject obj = new JSONObject();
		obj.element("queryText", "(\"Document Title\":" + keyword + ")");
		obj.element("refinements", "[\"4291944822\"]");
		obj.element("matchBoolean", "true");
		obj.element("searchField", "Search_All");
		obj.element("rowsPerPage", "100");
		obj.element("pageNumber", pageNum + "");
		http.postJson(html, obj.toString());
	}

	public void getIeeeList(List<String> kw) {

		HtmlInfo html = new HtmlInfo();
		Httpieee http = new Httpieee();
		IeeeExtract IeeeExtract = new IeeeExtract();
		// String keyword = "Publications Access Articles";// Access Articles
		for (String keyword : kw) {
			try {
				SystemCommon.printLog("Collecting Keyword: " + keyword);
				getContentStepOne(html, http, keyword);
				List<IEEEinfo> allList = new ArrayList<>();
				List<IEEEinfo> list = new ArrayList<>();
				IeeeExtract.JsonParserOnePageList(html.getContent(), list);
				int pageNum = 1;
				while (list.size() > 80) {
					SystemCommon.sleeps(20);
					for (IEEEinfo ieeEinfo : list) {
						allList.add(ieeEinfo);
					}
					list.clear();
					getListPageContent(html, http, keyword, ++pageNum);
					IeeeExtract.JsonParserOnePageList(html.getContent(), list);
				}
				for (IEEEinfo ieeEinfo : list) {
					allList.add(ieeEinfo);
				}
				for (IEEEinfo ieeeInfo : allList) {
					SystemCommon.sleeps(30);
					IeeeExtract.getAbstractPageContent(html, http, ieeeInfo);
					IeeeExtract.parserReferNum(html, http, ieeeInfo);
					ieeeInfo.setSearchKeyword(keyword);
					String md5 = MD5Util.MD5(ieeeInfo.getUrl() + ieeeInfo.getAuthors() + 
							ieeeInfo.getDoi()+ieeeInfo.getSearchKeyword());
					ieeeInfo.setMd5(md5);
					ConnectSqlIeee_data.insertMessage(ieeeInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
