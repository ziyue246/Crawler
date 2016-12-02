package common.extractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.bean.HtmlInfo;
import common.bean.WebData;
import common.siteinfo.CollectDataType;
import common.siteinfo.CommonComponent;
import common.siteinfo.Component;
import common.siteinfo.Siteinfo;
import common.system.Systemconfig;
import common.util.DOMUtil;
import common.util.MD5Util;
import common.util.StringUtil;

/**
 * 抽取实现类
 * 
 * @author grs
 */
public class XpathExtractor {
	protected SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	protected SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	 * @param list
	 *            解析的列表数据
	 * @param content
	 *            页面内容
	 * @param page
	 *            采集的当前页数
	 * @param siteFlag
	 *            站点标识
	 * @param collectType
	 *            采集类型
	 * @param keyword
	 *            可添加的参数
	 * @return
	 */
	ExtractorFactory ef = new ExtractorFactory();

	public String templateList(List<WebData> list, HtmlInfo html, int page, String siteFlag, String collectFlag,
			String... keyword) {
		XpathExtractor extractor = ExtractorFactory.createExtractor(siteFlag);
		list.clear();
		Siteinfo siteinfo = Systemconfig.allSiteinfos.get(siteFlag);
		// create(content);
		Node domtree = getRealDOM(html);
		if (domtree == null) {
			Systemconfig.sysLog.log("DOM解析为NULL！！");
			return null;
		}
		CommonComponent comp = getRealComp(siteinfo, collectFlag);// 得到元数据的配置组件

		extractor.parseTitle(list, domtree, comp.getComponents().get("title"));

		if (list.size() == 0)
			return null;
		extractor.parseTime(list, domtree, comp.getComponents().get("time"));
		extractor.parseUrl(list, domtree, comp.getComponents().get("url"));
		for (WebData wd : list) {
			wd.setMd5(MD5Util.MD5(wd.getTitle() + wd.getPubyear()));
		}
		String nextPage = extractor.parseNext(domtree, comp.getComponents().get("next"), new String[] { keyword[1],
				page + "" });
		return nextPage;
	}

	public void templateSingle(HtmlInfo html, String siteFlag, WebData webData) {
		Siteinfo siteinfo = Systemconfig.allSiteinfos.get(siteFlag);
		// create(content);
		Node domtree = getRealDOM(html);
		if (domtree == null) {
			Systemconfig.sysLog.log("DOM解析为NULL！！");
			return;
		}
		webData.setInserttime(new Date());
		webData.setSitename(siteinfo.getSiteName());
		webData.setSite_id(siteinfo.getSiteFlag());

		CommonComponent comp = getRealComp(siteinfo, CollectDataType.DATA.name());// 得到元数据的配置组件
		XpathExtractor extractor = ExtractorFactory.createExtractor(siteFlag);
		if (webData.getTitle() == null) {
			extractor.parsePageTitle(webData, domtree, comp.getComponents().get("pageTitle"), html.getContent());
		}
		extractor.parseJournal(webData, 	domtree, comp.getComponents().get("journal"), html.getContent());
		extractor.parseEmail(webData, 		domtree, comp.getComponents().get("email"), html.getContent());
		extractor.parseFund(webData, 		domtree, comp.getComponents().get("fund"), html.getContent());
		extractor.parseCategory(webData, 	domtree, comp.getComponents().get("category"), html.getContent());
		extractor.parseBrief(webData, 		domtree, comp.getComponents().get("brief"), html.getContent());
		extractor.parseAuthor(webData, 		domtree, comp.getComponents().get("author"), html.getContent());
		extractor.parsePubtime(webData, 	domtree, comp.getComponents().get("pubtime"), html.getContent());
		extractor.parseAddress(webData, 	domtree, comp.getComponents().get("address"), html.getContent());
		extractor.parseKeyword(webData, 	domtree, comp.getComponents().get("keyword"), html.getContent());
		extractor.parsePageReferurl(webData, domtree, comp.getComponents().get("pageReferurl"), html.getContent());
		extractor.parseCiteurl(webData, 	domtree, comp.getComponents().get("citeurl"), html.getContent());
		extractor.parseRefernum(webData,	domtree, comp.getComponents().get("refernum"), html.getContent());
		extractor.parseCitenum(webData, 	domtree, comp.getComponents().get("citenum"), html.getContent());
		if (webData.getMd5() == null)
			webData.setMd5(MD5Util.MD5(webData.getTitle() + webData.getPubyear()));
	}

	public String parseNext(Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return null;
		String url = null;
		if (nl.item(0) != null) {
			url = nl.item(0).getTextContent();
			if (component.getPrefix() != null)
				url = component.getPrefix() + url;
			if (component.getPostfix() != null)
				url += component.getPostfix();
		}
		return url;
	}

	private void parseTime(List<WebData> list, Node dom, Component component) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		judge(list.size(), nl.getLength());
		for (int i = 0; i < nl.getLength(); i++) {
			list.get(i).setPubyear(nl.item(i).getTextContent());
		}
	}

	public void parseUrl(List<WebData> list, Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		judge(list.size(), nl.getLength());
		for (int i = 0; i < nl.getLength(); i++) {
			String url = nl.item(i).getTextContent();
			if (component.getPrefix() != null)
				url = component.getPrefix() + url;
			if (component.getPostfix() != null)
				url = component.getPostfix() + url;
			list.get(i).setUrl(url);
		}
	}

	public void parseTitle(List<WebData> list, Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		judge(list.size(), nl.getLength(), true);
		for (int i = 0; i < nl.getLength(); i++) {
			WebData vd = new WebData();
			vd.setTitle(StringUtil.format(nl.item(i).getTextContent()));
			list.add(vd);
		}
	}

	public void parsePubtime(WebData videoData, Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null) {
			videoData.setPubyear(StringUtil.format(nl.item(0).getTextContent()).replace("Published:", ""));
		}
	}

	public void parseAuthor(WebData videoData, Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String author = "";
		for (int i = 0; i < nl.getLength(); i++) {
			author += nl.item(i).getTextContent();
			if (i < nl.getLength() - 1)
				author += ";";
		}
		videoData.setAuthor(StringUtil.format(author));
	}

	public void parseBrief(WebData videoData, Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String brief = "";
		for (int i = 0; i < nl.getLength(); i++) {
			brief += nl.item(i).getTextContent();
		}
		videoData.setBrief(StringUtil.format(brief));
	}

	public void parseFund(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String brief = "";
		for (int i = 0; i < nl.getLength(); i++) {
			brief += nl.item(i).getTextContent();
			if (i < nl.getLength() - 1)
				brief += ";";
		}
		webData.setFund(brief);
	}

	public void parseCategory(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null)
			webData.setCategory(nl.item(0).getTextContent());
	}

	public void parseJournal(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null)
			webData.setJournal(StringUtil.format(nl.item(0).getTextContent()));
	}

	public void parseEmail(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String email = "";
		for (int i = 0; i < nl.getLength(); i++) {
			email += nl.item(i).getTextContent();
			if (i < nl.getLength() - 1)
				email += ";";
		}
		webData.setEmail(email);
	}

	public void parseCitenum(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null)
			webData.setCite_num(Integer.parseInt(nl.item(0).getTextContent()));
	}

	public void parseRefernum(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null)
			webData.setRefer_num(Integer.parseInt(nl.item(0).getTextContent()));
	}

	public void parseCiteurl(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null)
			webData.setCite_url(nl.item(0).getTextContent());
	}

	public void parsePageReferurl(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null)
			webData.setRefer_url(nl.item(0).getTextContent());
	}

	public void parseAddress(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String brief = "";
		for (int i = 0; i < nl.getLength(); i++) {
			brief += nl.item(i).getTextContent();
			if (i < nl.getLength() - 1)
				brief += "*;";
		}
		webData.setAddress(StringUtil.format(brief).replace("Author Address:", ""));
	}

	public void parseKeyword(WebData webData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String brief = "";
		for (int i = 0; i < nl.getLength(); i++) {
			brief += nl.item(i).getTextContent();
			if (i < nl.getLength() - 1)
				brief += ";";
		}
		webData.setKeyword(StringUtil.format(brief));
	}

	public void parsePageTitle(WebData videoData, Node dom, Component component, String content) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		if (nl.item(0) != null) {
			videoData.setTitle(StringUtil.format(nl.item(0).getTextContent()));
		}
	}

	protected void judge(int len1, int len2, boolean first) {
		if (first)
			return;
		if (len1 != len2) {
			System.err.println("抽取属性数量不一致");
		}
	}

	protected void judge(int len1, int len2) {
		judge(len1, len2, false);
	}

	protected NodeList commonList(String xpath, Node domtree) {
		if (xpath == null || xpath.equals(""))
			return null;
		NodeList list = null;
		try {
			list = XPathAPI.selectNodeList(domtree, xpath);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解析页面数据，适用于详细页面解析，单个数据
	 * 
	 * @param content
	 * @param siteinfo
	 * @param metaOrData
	 * @param collectType
	 * @param keyword
	 * @return
	 */
	public String regMatch(String content, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	/**
	 * 获得真实内容
	 * 
	 * @param content
	 * @param siteinfo
	 * @return
	 */
	protected Node getRealDOM(HtmlInfo html) {
		DOMUtil dom = new DOMUtil();
		return dom.ini(html.getContent(), html.getEncode());
	}

	/**
	 * 获得真实组件信息
	 * 
	 * @param mode
	 * @param siteinfo
	 * @return
	 */
	protected CommonComponent getRealComp(Siteinfo siteinfo, String mode) {
		return siteinfo.getCommonComponent().get(mode);
	}

	public Date timeProcess(String time) {
		Date d = null;
		try {
			d = sdf1.parse(time);
		} catch (ParseException e) {
			try {
				d = sdf2.parse(time);
			} catch (ParseException e1) {
				try {
					d = sdf3.parse(time);
				} catch (ParseException e2) {
					d = null;
				}
			}
		}
		return d;
	}

}
