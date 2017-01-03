package common.util;

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
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class DomTree {
	public static void printNode(String mark, Node node) {
		String rNodeName = node.getNodeName();// ��ǰ����Ԫ������
		 if(node.getNodeType()==Node.ELEMENT_NODE){ //Ϊ�ڵ����ͣ�����ڵ�����
		 System.out.print("<"+rNodeName+">");
		 }
		if (node.getNodeType() == Node.TEXT_NODE) { // �ı����ͣ�����Ėc
			String s = ((Text) node).getWholeText();
			//s = s.replace("\\s*", "");
			System.out.println(s);
		}

		NodeList allNodes = node.getChildNodes();// ��ȡ��Ҫ�����ڵ���ӽڞ�
		int size = allNodes.getLength();
		if (size > 0) {
			for (int j = 0; j < size; j++) {
				Node childNode = allNodes.item(j);
				printNode(mark, childNode);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					// ÿ������د����ǩ�����������ǩ
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

	public static DocumentFragment getNode(String content, String charset) {
		if (content == null)
			return null;
		charset = charset == null ? "utf-8" : charset;
		byte[] byt = null;
		try {
			byt = content.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "").getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			System.out.println("����ֽ�ʧ�ܣ��������Ƿ����");
			byt = null;
			return null;
		}
		InputSource source = new InputSource(new ByteArrayInputStream(byt));
		source.setEncoding(charset);
		DOMFragmentParser parser = new DOMFragmentParser();
		DocumentFragment domtree = new HTMLDocumentImpl().createDocumentFragment();
		// try {
		// �Ƿ���������ȱʧ�ı�ǩ?���Ҫ��XML��ʽ����HTML�ļ�����ֵ����Ϊ��
		try {
			parser.setFeature("http://cyberneko.org/html/features/balance-tags", true);
		} catch (SAXNotRecognizedException | SAXNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �Ƿ����<script>Ԫ���е�<!-- -->��ע�ͷ�
		try {
			parser.setFeature("http://cyberneko.org/html/features/scanner/script/strip-comment-delims", true);
		} catch (SAXNotRecognizedException | SAXNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			parser.parse(source, domtree);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	// ȥ��ǰ��ո�
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

	// ΢��js html����ȥ��jsӰ�� Monitor
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

	// ΢��js html����ȥ��jsӰ�� Search
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