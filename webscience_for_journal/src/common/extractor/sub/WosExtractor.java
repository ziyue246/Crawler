package common.extractor.sub;

import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.bean.HtmlInfo;
import common.bean.WebData;
import common.extractor.XpathExtractor;
import common.siteinfo.CollectDataType;
import common.siteinfo.CommonComponent;
import common.siteinfo.Component;
import common.siteinfo.Siteinfo;
import common.system.Systemconfig;
import common.util.StringUtil;

/**
 * 抽取实现类
 * 
 * @author socialmedia
 */
public class WosExtractor extends XpathExtractor {

	@Override
	public void parseAuthor(WebData videoData, Node dom, Component component, String... args) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		String author = "";
		for (int i = 0; i < nl.getLength(); i++) {
			author += nl.item(i).getTextContent();
			if (i < nl.getLength() - 1)
				author += "#";
		}
		videoData.setAuthor(StringUtil.format(author.replace("By:", "")));
	};

	/**
	 * 解析评论
	 * 
	 * @param list
	 * @param html
	 * @param page
	 * @param siteFlag
	 * @return
	 */
	public String referList(List<WebData> list, HtmlInfo html, int page, String siteFlag, String... keyword) {
		list.clear();
		Siteinfo siteinfo = Systemconfig.allSiteinfos.get(siteFlag);
		// create(content);
		html.setContent(html
				.getContent()
				.replace(
						"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">",
						""));
		Node domtree = getRealDOM(html);
		if (domtree == null) {
			Systemconfig.sysLog.log("DOM解析为NULL！！");
			return null;
		}
		CommonComponent comp = getRealComp(siteinfo, CollectDataType.REFER.name());// 得到元数据的配置组件

		parsePubyear(list, domtree, comp.getComponents().get("pubyear"));
		if (list.size() == 0)
			return null;
		parseWebAuthor(list, domtree, comp.getComponents().get("web_author"));
		parseWebTitle(list, domtree, comp.getComponents().get("web_title"));

		// extractor.parseReferCount(list, domtree,
		// comp.getComponents().get("refer_count"));
		// extractor.parseReferUrl(list, domtree,
		// comp.getComponents().get("refer_url"));

		return parseNext(domtree, comp.getComponents().get("next"), new String[] { keyword[1], page + "" });
	}

	public void parseReferUrl(List<WebData> list, Node dom, Component component) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		judge(list.size(), nl.getLength());
		for (int i = 0; i < nl.getLength(); i++) {
			String url = nl.item(i).getTextContent();
			if (url.equals(""))
				continue;

			list.get(i).setRefer_url(url);
		}
	}

	public void parseReferCount(List<WebData> list, Node dom, Component component) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		judge(list.size(), nl.getLength());
		for (int i = 0; i < nl.getLength(); i++) {
			String url = nl.item(i).getTextContent();
			list.get(i).setRefer_num(Integer.parseInt(url));
		}
	}

	public void parsePubyear(List<WebData> list, Node dom, Component component) {
		NodeList nl = commonList(component.getXpath(), dom);
		if (nl == null)
			return;
		judge(list.size(), nl.getLength(), true);
		for (int i = 0; i < nl.getLength(); i++) {
			WebData vd = new WebData();
			String[] time = StringUtil.format(nl.item(i).getTextContent()).split("Published:");
			if (time.length > 1) {
				vd.setPubyear(time[1]);
				vd.setSource(time[0]);
			} else {
				vd.setSource(time[0]);
			}

			list.add(vd);
		}
	}

	public void parseWebAuthor(List<WebData> list, Node dom, Component component) {
		for (int i = 0; i < list.size(); i++) {
			NodeList nl = commonList("//TR[contains(@id,'RECORD')][" + (i + 1) + component.getXpath(), dom);
			if (nl.item(0) != null)
				list.get(i).setAuthor(StringUtil.format(nl.item(0).getTextContent()));
		}
	}

	public void parseWebTitle(List<WebData> list, Node dom, Component component) {
		for (int i = 0; i < list.size(); i++) {
			NodeList nl = commonList("//TR[contains(@id,'RECORD')][" + (i + 1) + component.getXpath(), dom);
			if (nl.item(0) != null)
				list.get(i).setTitle(StringUtil.format(nl.item(0).getTextContent()));
		}
	}

	public String citeList(List<WebData> list, HtmlInfo html, int page, String... keyword) {
		list.clear();
		Siteinfo siteinfo = Systemconfig.allSiteinfos.get(keyword[0]);
		// create(content);
		Node domtree = getRealDOM(html);
		if (domtree == null) {
			Systemconfig.sysLog.log("DOM解析为NULL！！");
			return null;
		}
		CommonComponent comp = getRealComp(siteinfo, CollectDataType.META.name());// 得到元数据的配置组件
		parsePubyear(list, domtree, comp.getComponents().get("pubyear"));
		if (list.size() == 0)
			return null;
		parseWebAuthor(list, domtree, comp.getComponents().get("web_author"));
		parseWebTitle(list, domtree, comp.getComponents().get("web_title"));
		String nextPage = parseNext(domtree, comp.getComponents().get("next"), new String[] { keyword[1], page + "" });
		return nextPage;
	}

}
