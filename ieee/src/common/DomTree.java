package common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.http.client.ClientProtocolException;
import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DomTree {
	public static void printNode(String mark, Node node) {
		String rNodeName = node.getNodeName();// 当前遍历元素名称
		 if(node.getNodeType()==Node.ELEMENT_NODE){ //为节点类型，输出节点名称
		 System.out.print("<"+rNodeName+">");
		 }
		if (node.getNodeType() == Node.TEXT_NODE) { // 文本类型，输出文朿
			String s = ((Text) node).getWholeText();
			//s = s.replace("\\s*", "");
			System.out.println(s);
		}

		NodeList allNodes = node.getChildNodes();// 获取承要遍历节点的子节炿
		int size = allNodes.getLength();
		if (size > 0) {
			for (int j = 0; j < size; j++) {
				Node childNode = allNodes.item(j);
				printNode(mark, childNode);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					// 每遍历完丿个标签，输出结束标签
					String s = childNode.getTextContent();
					//s = s.replace("\\s*", "");
					System.out.println("</" + childNode.getNodeName() + ">" + s);
				}
			}
		}
	}

	public static String getHtmlContent(String url) throws Exception {

		GetMethod getMethod = new GetMethod(url);
		try {
			HttpClient client = new HttpClient();
			int statusCode = client.executeMethod(getMethod);
			System.out.println("statusCode:" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + getMethod.getStatusLine());
			}

			InputStream is = getMethod.getResponseBodyAsStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				sb.append(line).append("\r\n");
			}
			reader.close();
			return sb.toString();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DocumentFragment getNode(String content, String charset) throws SAXException, IOException {
		if (content == null)
			return null;
		charset = charset == null ? "utf-8" : charset;
		byte[] byt = null;
		try {
			byt = content.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "").getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			System.out.println("获得字节失败，检查编码是否存在");
			byt = null;
			return null;
		}
		InputSource source = new InputSource(new ByteArrayInputStream(byt));
		source.setEncoding(charset);
		DOMFragmentParser parser = new DOMFragmentParser();
		DocumentFragment domtree = new HTMLDocumentImpl().createDocumentFragment();
		// try {
		// 是否允许增补缺失的标签?如果要以XML方式操作HTML文件，此值必须为眿
		parser.setFeature("http://cyberneko.org/html/features/balance-tags", true);
		// 是否剥掉<script>元素中的<!-- -->等注释符
		parser.setFeature("http://cyberneko.org/html/features/scanner/script/strip-comment-delims", true);
		parser.parse(source, domtree);
		return domtree;

	}
	
	public static NodeList commonList(String xpath, Node domtree) {
		if (xpath == null || xpath.equals("") || xpath.startsWith("${"))
			return null;
		NodeList list = null;
		try {
			list = XPathAPI.selectNodeList(domtree, xpath);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 去除前后空格
	public static String trimInnerSpaceStr(String str) {
		str = str.trim();
		while (str.startsWith(" ")) {
			str = str.substring(1, str.length()).trim();
		}
		while (str.endsWith(" ")) {
			str = str.substring(0, str.length() - 1).trim();
		}
		return str;
	}

	public static String replaceSpecChar(String content){
		content = content.replaceAll("\\\\\r\\\\\n", "\n");
		content = content.replaceAll("\\\\r\\\\n", "\n");
		content = content.replaceAll("\\\r\\\n", "\n");
		content = content.replaceAll("\\r\\n", "\n");
		content = content.replaceAll("\r\n", "\n");
		content = content.replaceAll("\\\\\n", "\n");
		content = content.replaceAll("\\\\n", "\n");
		content = content.replaceAll("\\\n", "\n");
		content = content.replaceAll("\\n", "\n");
		content = content.replaceAll("\\\\\t", "\t");
		content = content.replaceAll("\\\\t", "\t");
		content = content.replaceAll("\\\t", "\t");
		content = content.replaceAll("\\t", "\t");
		content = content.replaceAll("\\\\\"", "\"").replace("\\\\/", "/");
		content = content.replaceAll("\\\"", "\"").replace("\\/", "/");
		content = content.replaceAll("\\\"", "\"").replace("\\/", "/");
		return content;
	}

	// 微博js html内容去除js影响 Monitor
	public static String weiboMonitorJsHtml(String content) {
		
		content=replaceSpecChar(content);
		StringBuilder consb = new StringBuilder();
		
		String[] cons = content.split("\n");
		for (String s : cons) {
			if (s.indexOf("FM.view") < 0) 
			{
				//System.out.println(s);
				consb.append(s+"\n");
			}
		}
		content = consb.toString();
		
        return content; 
	}

	// 微博js html内容去除js影响 Search
	public static String weiboSearchJsHtml(String content) {
//		content = content.replaceAll("\\\\r\\\\n", "\n");
//		content = content.replaceAll("\\\\n", "\n");
//		content = content.replaceAll("\\\\t", "\t");
		content=replaceSpecChar(content);
		StringBuilder consb = new StringBuilder();
		//content = content.replace("\\\"", "\"").replace("\\/", "/");
		String[] cons = content.split("\n");
		for (String s : cons) {
			if (s.indexOf("STK") < 0||s.indexOf("SCRIPT") < 0) {
				consb.append(s);
			}
		}
		return consb.toString();
	}
}