package common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestZhihuMain {

	public static void search() throws Exception {
		Splider splider = new Splider();

		final String murl = "http://zhihu.sogou.com/zhihu?query=<keyword>&ie=utf8&dp=1&w=&oq=fangji&ri=0&sourceid=sugg&stj=0%3B4%3B0%3B0&stj2=0&stj0=0&stj1=4&hp=40&hp1=&sut=9109&sst0=1480905543914&lkt=7%2C1480905535933%2C1480905536713";

		String[] keywords = { "房价", "欢乐颂", "朴槿惠辞职", "巴西飞机坠毁", "万年一遇东方美人", "中国经济", "经济危机", "黄晓明", "周杰伦" };

		String charset = "utf-8";//

		// <h4 class="about-list-title">
		String titleXpath = "//H4[@class='about-list-title']/A";
		String titleUrlXpath = "//H4[@class='about-list-title']/A/@href";
		// <div id="zh-question-detail"
		String cQuestionXpath = "//DIV[@id='zh-question-detail']/DIV[@class='zm-editable-content']";

		// <div class="zm-editable-content clearfix">
		String cAnswerXpath = "//DIV[@class='zm-editable-content clearfix']";
		String cXpath = "//H4[@class='about-list-title']/A/@href";
		// String xpath ="DIV[contains(@class,'content')]//P|//ARTICLE//P";
		String xpath = cAnswerXpath;
		HashMap<String, String> map;

		HashMap<String, String> xpathMap = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		xpathMap.put("titleXpath", titleXpath);
		xpathMap.put("titleUrlXpath", titleUrlXpath);
		xpathMap.put("cQuestionXpath", cQuestionXpath);
		xpathMap.put("cAnswerXpath", cAnswerXpath);
		while (true) {
			for (String keyword : keywords) {
				if (keyword.length() < 1)
					continue;
				String url = murl.replace("<keyword>", URLEncoder.encode(keywords[0], "UTF-8"));
				map = getListPage(url, xpathMap);
				if (map == null)
					continue;
				Set<String> keySet = map.keySet();
				for (String curl : keySet) {
					String ctitle = (String) map.get(curl);
					System.out.println("title  :" + ctitle);
					System.out.println("url    :" + curl);
					getContentPage(curl, xpathMap);
					Thread.sleep(5000);
				}
			}
		}
	}

	public static void getContentPage(String url, HashMap<String,String> xpathMap) throws Exception {
		Splider splider = new Splider();
		String charset = "utf-8";
		String content = splider.getHtmlContent(url, charset);
		Node node = DomTree.getNode(content, charset);

		String cQuestionXpath = (String) xpathMap.get("cQuestionXpath");
		String cAnswerXpath = (String) xpathMap.get("cAnswerXpath");
		NodeList questionNL = DomTree.commonList(cQuestionXpath, node);
		NodeList cAnswerNL = DomTree.commonList(cAnswerXpath, node);

		if (questionNL.item(0) != null) {
			String question = questionNL.item(0).getTextContent().trim();
			System.out.println("question   :" + question);
			System.out.println("answerLen  :" + cAnswerNL.getLength());
			System.out.println();
		}
		for (int i = 0; i < cAnswerNL.getLength(); i++) {
			String cAnswer = cAnswerNL.item(i).getTextContent().trim();
			//System.out.println("answer##" + (i + 1) + "##\t" + cAnswer);
			//System.out.println();
		}
	}

	public static HashMap<String, String> getListPage(String url, HashMap<String,String> xpathMap) throws Exception {
		Splider splider = new Splider();
		String charset = "utf-8";
		String content = splider.getHtmlContent(url, charset);
		Node node = DomTree.getNode(content, charset);
		HashMap<String, String> map = new HashMap<String, String>();
		String tilteXpath = (String) xpathMap.get("titleXpath");
		String titleUrlXpath = (String) xpathMap.get("titleUrlXpath");
		NodeList tilteNL = DomTree.commonList(tilteXpath, node);
		NodeList titleUrlNL = DomTree.commonList(titleUrlXpath, node);
		if (tilteNL.getLength() != titleUrlNL.getLength())
			return null;
		for (int i = 0; i < tilteNL.getLength(); i++) {
			String title = tilteNL.item(i).getTextContent().trim();
			String titleUrl = titleUrlNL.item(i).getTextContent().trim();
			map.put(titleUrl, title);
		}
		return map;
	}

	public static String utf82gbk(String s) {
		String gbk = null;
		try {
			// String utf8 = new String(s.getBytes("UTF-8"));
			// System.out.println(utf8);
			// String unicode = new String(s.getBytes(),"GBK");
			// System.out.println(unicode); String(str.getBytes("ISO-8859-1"),
			// "UTF-8")
			// gbk = new String(s.getBytes("GBK"));
			gbk = new String(s.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gbk;
	}

	public static void main(String[] args) throws Exception {

		// System.out.println(Calendar.getInstance().get(Calendar.YEAR));
		search();
	}

	public static void zhongwen(String content) {
		Pattern p = Pattern
				.compile("[\u4e00-\u9fa5]|[\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b]");
		Matcher m = p.matcher(content);
		while (m.find()) {
			System.out.print(m.group().trim());
		}
		System.out.println();
	}
}
