package common.download;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.smy.dama2.Dame2Code;
import common.http.HtmlInfo;
import common.http.sub.HttpPatent;
import common.system.FileOperation;
import common.system.SystemCommon;
import common.system.VerifyCodeRecognition;
import common.util.DomTree;

public class PatentDownload {

	
	public static void main2(String[] args) {
		String url = "http://www11.drugfuture.com/cnpat/package/发明专利申请说明书CN99802893.2.pdf";
		HtmlInfo html = new HtmlInfo();
		HttpPatent http = new HttpPatent();

		String ua = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36";
		           //Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36
		html.setUa(ua);
		html.setEncode("utf-8");
		html.setOrignUrl(url);
		System.out.println(http.downGet(html, "test.pdf"));
	}
	public void firstPage(HttpPatent http,HtmlInfo html){
		// 进入查询首页
		String url = "http://www.drugfuture.com/cnpat/cn_patent.asp";
		html.setOrignUrl(url);
		SystemCommon.sleeps(2);
		http.simpleGet(html);
	}
	public int verify(HttpPatent http,HtmlInfo html,String patentCode){
		
		ArrayList<String> params = new ArrayList<String>();
		
		String pattern = "http:(\\W+)www(\\d+).drugfuture.com(\\W+)cnpat(\\W+)verify.aspx";
		String url = SystemCommon.regex(html.getContent(), pattern);
		if (url.contains("\n"))
			url = url.split("\n")[0];
		html.setReferUrl(html.getOrignUrl());
		html.setOrignUrl(url);

		http.setPatentCode(patentCode);
		SystemCommon.sleeps(2);
		http.httpUrlConnection(html, params, "verify");
		
		// 下载验证码
		String imgurl = url.replace("verify", "VerifyCode");
		html.setReferUrl(html.getOrignUrl());
		html.setOrignUrl(imgurl);
		String imgfilename = "img/tmp.jpg";
		SystemCommon.sleeps(2);
		http.downGet(html, imgfilename);
		String[] result = { "" };
		//int reCode = Dame2Code.d2FileActionPerformed(imgfilename, result);
		
		VerifyCodeRecognition.trainPath = "img";
		result[0]=VerifyCodeRecognition.getAllOcr(imgfilename);
		if(result[0]==null){
			SystemCommon.printLog("result[0] 验证码返回为NULL");
			return 0;
		}
		
		http.setValidCode(result[0]);
		//html.setReferUrl(url);
		url = html.getReferUrl().replace("verify", "search");
		html.setOrignUrl(url);
		SystemCommon.sleeps(2);
		http.httpUrlConnection(html, params, "search");
		if (html.getContent().contains("验证码输入错误，请返回重新输入。")) {
			//Dame2Code.errorReport(reCode);
			SystemCommon.printLog("验证码输入错误，请返回重新输入。  result:"+result[0]);
			File tfile = new File("img/tmp.jpg");
			if(tfile.exists())
				tfile.delete();
			return -1;
		}
		FileOperation.renameFile("img", "tmp.jpg", result[0] + ".jpg");
		params.clear();
		
		return 1;
	}
	public void getParams(HttpPatent http,HtmlInfo html,ArrayList<String> params){
		Node formNode = DomTree.getNode(html.getContent(), html.getEncode());
		String formXpath = "//FORM[@name='Download']/INPUT";

		NodeList formNl = DomTree.commonList(formXpath, formNode);
		
		String pattern = "FulltextType.value=\"....\"";//
		//http:(\\W+)www(\\d+).drugfuture.com(\\W+)cnpat(\\W+)verify.aspx";
		String fulltextType = SystemCommon.regex(html.getContent(), pattern);
		if(fulltextType.contains("\n")){
			fulltextType = fulltextType.split("\n")[0];
		}
		fulltextType = fulltextType.
				replace("FulltextType.value=\"", "").replace("\"", "");
		
		for (int j = 0; j < formNl.getLength(); j++) {
			NamedNodeMap namedNodeMap = formNl.item(j).getAttributes();
			String key = namedNodeMap.getNamedItem("name").getTextContent();
			String value = namedNodeMap.getNamedItem("value").getTextContent();
			if (key.equals("FulltextType")){
				params.add(key + "##" + fulltextType);
			}
			else
				params.add(key + "##" + value);
		}
		for (String s : params) {
			//System.out.println(s);
		}

		// 下载链接
		pattern = "http:(\\W+)www(\\d+).drugfuture.com(\\W+)cnpat(\\W+)SecurePdf.aspx";
		String url = SystemCommon.regex(html.getContent(), pattern);
		if (url.contains("\n"))
			url = url.split("\n")[0];

		
		html.setReferUrl(html.getOrignUrl());
		html.setOrignUrl(url);
		// http.simplePost(html, params);
		SystemCommon.sleeps(2);
		http.httpUrlConnection(html, params, "");

		//大于30页的情况 ，，，继续下一部份
		while(html.getContent().contains("继续下一部份")){
			
			formNode = DomTree.getNode(html.getContent(), html.getEncode());
			formXpath = "//FORM[@name='lastpart']/INPUT";
			//	<form name="lastpart" method="post" action="SecurePdf.aspx">
			formNl = DomTree.commonList(formXpath, formNode);
			params.clear();
			
			for (int j = 0; j < formNl.getLength(); j++) {
				NamedNodeMap namedNodeMap = formNl.item(j).getAttributes();
				String key = namedNodeMap.getNamedItem("name").getTextContent();
				String value = namedNodeMap.getNamedItem("value").getTextContent();
				params.add(key + "##" + value);
			}
			html.setReferUrl(html.getOrignUrl());
			SystemCommon.sleeps(2);
			http.httpUrlConnection(html, params, "");
		}
	}
	public int getPdf(HttpPatent http,HtmlInfo html,ArrayList<String> params,String line){
		
		
		String id = line.split("##")[0];//申请号
		String title = line.split("##")[1];//专利名
		String patentCode = line.split("##")[2];//申请号
		String category = line.split("##")[3];//专利名
		
		
		for (int j = 0; j < 5; j++) {
			if(html.getContent().contains("出错了!请稍后再试!")){
				System.out.println("出错了!请稍后再试!");
				SystemCommon.sleeps(10);
				http.httpUrlConnection(html, params, "");
			}else{
				break;
			}
		}
		if(html.getContent().contains("出错了!请稍后再试!")){
			FileOperation.appendWrite("file/notGet", line+"##出错了!请稍后再试!\n");
			System.out.println("出错了!请稍后再试!");
			SystemCommon.sleeps(10);
			return 0;
		}
		
		Node pdfNode = DomTree.getNode(html.getContent(), html.getEncode());
		String pdfXpath = "//A/@href";

		NodeList pdfNl = DomTree.commonList(pdfXpath, pdfNode);
		SystemCommon.sleeps(2);
		for (int j = 0; j < pdfNl.getLength(); j++) {
			String pdfUrl = pdfNl.item(j).getTextContent();
			if (pdfUrl.contains(".pdf")) {
				//http://www6.drugfuture.com/cnpat/SecurePdf.aspx
				String url = html.getOrignUrl();
				String preUrl = url.split("drugfuture")[0];
				pdfUrl = preUrl+"drugfuture.com/cnpat/package" + pdfUrl.split("package")[1];
				
				//pdfUrl = pdfUrl.replace("发明专利申请说明书", 
				//		"%E5%8F%91%E6%98%8E%E4%B8%93%E5%88%A9%E7%94%B3%E8%AF%B7%E8%AF%B4%E6%98%8E%E4%B9%A6");
				String hanzi = pdfUrl.split("package/")[1].split("CN")[0];
				
				
				try {
					pdfUrl = pdfUrl.replace(hanzi, URLEncoder.encode(hanzi, html.getEncode()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(pdfUrl);
				html.setReferUrl(html.getOrignUrl());
				html.setOrignUrl(pdfUrl);
				
				String fileName = "pdfDownload/"+category;
				
				
				File folder = new File(fileName);
				if (!(folder.exists() && folder.isDirectory())) {
					folder.mkdirs();
				}//fileName +id+"_"+title+".pdf"
				int status = http.downGet(html, fileName+ "/" +id+"_"+title+".pdf");
				
				//int status = DownloadBinary.download(html, fileName+ "/" +id+"_"+title+".pdf");
				if(status==0) SystemCommon.printLog(title+"  下载成功！！！");
				else {
					
					FileOperation.appendWrite("file/notGet", line+"##下载失败！\n");
					SystemCommon.printLog(title+"  下载失败！！！");
					//failCount++;
					//System.out.println("当前失败次数为："+failCount);
					//if(failCount>20)
					{
						return 11;
					}
				}
				SystemCommon.sleeps(5);
				break;
			}
		}
		SystemCommon.sleeps(5);
		return 0;
	}
	public void download(List<String> message) {
		HtmlInfo html = new HtmlInfo();
		HttpPatent http = new HttpPatent();

		String ua = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36";
		           //Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36
		html.setUa(ua);
		html.setEncode("utf-8");

		for (int i = 0; i < message.size(); i++) {
			
			try{
				String line = message.get(i);
				String id = line.split("##")[0];//申请号
				String title = line.split("##")[1];//专利名
				String patentCode = line.split("##")[2];//申请号
				String category = line.split("##")[3];//专利名
				SystemCommon.printLog(i+"\t:  "+line+"  开始下载！！！");
				ArrayList<String> params = new ArrayList<String>();
				String url = "";
				String pattern = "";
	
				firstPage(http,html);
				// 获取验证码
				int status = verify(http,html,patentCode);
				if(status!=1){
					if(status==-1)	--i;
					continue;
				}
				
				
				//System.out.println(html.getContent());
				params.clear();
				
				String turl = url;
				String content = html.getContent();
				String cookie = html.getCookie();
				String referUrl = html.getReferUrl();
				status = 11;
				while(status==11){
					url=turl;
					html.setContent(content);
					html.setCookie(cookie);
					html.setReferUrl(referUrl);
					
					getParams(http, html, params);
					status = getPdf(http, html, params, line);
					if(status==0)continue;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}


