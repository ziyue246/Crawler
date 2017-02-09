package common.download;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.http.HtmlInfo;
import common.http.sub.HttpCnki;
import common.system.FileOperation;
import common.system.SystemCommon;
import common.util.DomTree;

public class CnkiDownload {

	public void download(List<String> message) {
		HtmlInfo html = new HtmlInfo();
		HttpCnki http = new HttpCnki();
		String ua = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36";

		html.setUa(ua);
		html.setEncode("utf-8");
		String loginUrl = "http://epub.cnki.net/kns/brief/result.aspx?dbPrefix=scdb&action=scdbsearch&db_opt=SCDB";
		
		
		html.setOrignUrl(loginUrl);
		http.getCookie(html);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT+0800 (中国标准时间)'", Locale.US);
		boolean retryMark = true;
		
		
		String savepdf = "downloadPDF/";
		for (int i = 0; i < message.size(); i++) {
			try {
				String[] line = message.get(i).split("##");
				if (line.length <3 || line.length>4) {
					SystemCommon.printLog("id,title,category occour wrong");
					continue;
				}
				String id = line[0];
				String title = line[1];
				String category = line[2];
				String author = "";
				if(line.length==4){
					author = line[3];
					if(author.contains(";")){
						author = author.split(";")[0];
					}
				}
				
				SystemCommon.printLog((i + 1) + ": " + title + "  is  collecting!!!");
				//SystemCommon.sleeps(60 * 1);
				String 
				searchUrl = "http://epub.cnki.net/kns/request/SearchHandler.ashx?action=&NaviCode=*&ua=1.21&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&"
						+ "ConfigFile=SCDB.xml&db_opt=CJFQ%2CCJFN%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND%2CCCJD&base_special1=%25&magazine_special1=%25&txt_1_sel=SU"
						+ "&txt_1_value1=" + SystemCommon.urlEncode(title, "utf-8")
						+ "&txt_1_relation=%23CNKI_AND&txt_1_special1=%3D&his=0&" + "__="
						+ SystemCommon.urlEncode(sdf.format(new Date()), "utf-8");

				
				
				searchUrl = "http://epub.cnki.net/kns/request/SearchHandler.ashx?action=&NaviCode=*&ua=1.21&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&"
						+ "ConfigFile=SCDB.xml&db_opt=CJFQ%2CCJFN%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND%2CCCJD&base_special1=%25&magazine_special1=%25&txt_1_sel=TI&"
						+ "txt_1_value1=" + SystemCommon.urlEncode(title, "utf-8")
						+ "&txt_1_relation=%23CNKI_AND&txt_1_special1=%3D&au_1_sel=AU&au_1_sel2=AF"
						+ "&au_1_value1=" + SystemCommon.urlEncode(author, "utf-8")
						+ "&au_1_special1=%25&au_1_special2=%25&his=0&__="
						+ SystemCommon.urlEncode(sdf.format(new Date()), "utf-8");
				
				
				html.setOrignUrl(searchUrl);
				html.setReferUrl(loginUrl);
				http.simpleGet(html);

				if (!html.getContent().startsWith("ASP.brief")) {
					SystemCommon.printLog(title + " crawler ASP.brief page is wrong!!!");
					if (retryMark) {
						--i;
						html.setOrignUrl(loginUrl);
						http.getCookie(html);
						retryMark = false;
					}
					continue;
				}
				searchUrl = "http://epub.cnki.net/kns/brief/brief.aspx?" + "pagename=" + html.getContent() + "&t="
						+ System.currentTimeMillis() + "&keyValue=" + SystemCommon.urlEncode(title, "utf-8")
						+ "&S=1&recordsperpage=10";
				html.setReferUrl(loginUrl);
				html.setOrignUrl(searchUrl);
				http.simpleGet(html);
				if (html.getContent().contains("对不起，服务器上不存在此用户！可能已经被剔除或参数错误")) {
					SystemCommon.printLog(title + " 对不起，服务器上不存在此用户！可能已经被剔除或参数错误!!!");
					if (retryMark) {
						--i;
						html.setOrignUrl(loginUrl);
						http.getCookie(html);
						retryMark = false;
					}
					continue;
				}
				retryMark = true;

				Node node = DomTree.getNode(html.getContent(), html.getEncode());

				String urlXpath = "//TR//A[@class='fz14']/@href";
				NodeList urlNl = DomTree.commonList(urlXpath, node);
				if (urlNl == null || urlNl.getLength() < 1) {
					continue;
				}
				SystemCommon.sleeps(60 * 2);
				String keywordUrl = urlNl.item(0).getTextContent().trim();
				
				keywordUrl = "http://epub.cnki.net" + keywordUrl;
				html.setReferUrl(searchUrl);
				html.setOrignUrl(keywordUrl);
				http.simpleGet(html);

				if (html.getRealUrl() != null) {
					html.setReferUrl(searchUrl);
					html.setOrignUrl(html.getRealUrl());
					html.setRealUrl(null);
					http.simpleGet(html);
				}

				// get pdf download url
				Node downLoadNode = DomTree.getNode(html.getContent(), html.getEncode());

				String downLoadXpath = "//DIV[@id='QK_nav']//" + "LI[contains(@class,'pdf')]" + "/A/@href";
				NodeList downLoadNl = DomTree.commonList(downLoadXpath, downLoadNode);
				String downLoadUrl = "";
				if (downLoadNl != null && downLoadNl.getLength() == 1) {
					downLoadUrl = downLoadNl.item(0).getTextContent().trim();
					downLoadUrl = "http://www.cnki.net" + downLoadUrl;
					html.setReferUrl(html.getOrignUrl());
					html.setOrignUrl(downLoadUrl);
				}
				if (downLoadUrl == "") {
					continue;
				}
				
				while (true) {
					http.simpleGet(html);
		
					if(html.getContent()!=null&&(html.getContent().contains("对不起，贵单位没有订购该专辑，请您与贵单位图书馆联系订购！")
							||html.getContent().contains("对不起，本刊只收录题录信息，暂不提供原文下载。给您造成不便请谅解！")
							||html.getContent().contains("请您与贵单位管理员联系订购！")
							||html.getContent().contains("贵单位没有订购"))){
						//FileOperation.read("file/notGet");
						FileOperation.appendWrite("file/notGet", title+"\n");
						SystemCommon.printLog(title+" 未订购！");
						return ;
					}
					if (html.getRealUrl() != null) {
						String orignUrl = html.getOrignUrl();
						html.setOrignUrl(html.getRealUrl());
						if(!html.getOrignUrl().startsWith("http")){
							html.setOrignUrl("http://www.cnki.net"+html.getOrignUrl());
						}
						html.setRealUrl(null);
						if (orignUrl.contains("docdownload.cnki.net")) {
							String fileName = savepdf + category;
							File folder = new File(fileName);
							fileName = fileName + "/" + id + "_" + title + ".pdf";
							
							if (!(folder.exists() && folder.isDirectory())) {
								folder.mkdirs();
							}
							
							int status = DownloadBinary.download(html, fileName);
							if(status==1){
								//System.out.println(fileName);
								SystemCommon.printLog(fileName + " collect success!!!");
							}
							else{
								SystemCommon.printLog(fileName + " collect fail!!!");
							}
							break;
						}
					} else {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
