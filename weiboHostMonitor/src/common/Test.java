package common;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test {
	public static void search() throws Exception {
		Splider splider = new Splider();
		String url = "http://weibo.com/p/1006051730077315/info?mod=pedit_more";
		SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		SystemCommon.currentCookie = "SINAGLOBAL=8574716518633.067.1475226495557; wvr=6; TC-Ugrow-G0=5e22903358df63c5e3fd2c757419b456; SCF=Au34eOHW9vCEpOlPBAVdrn3J3RS_DvJLdrWJUX2TS6nLqI57xSVYdV_-EquKjHeMwDrWbHwJmPuyLXb35GoJCvA.; SUB=_2A256-CUNDeTxGeRK61UZ8SbIzz2IHXVZjBHFrDV8PUNbmtANLRPXkW8NoQqbdYsxRIy_6hOp1f_nIQSqvQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFLaUa9E2iGSFaS52z1uGc35JpX5KMhUgL.FozXehMReKnXSh22dJLoI7yAIPLf9KzXSntt; SUHB=0jBZNvQmCLnVmB; ALF=1507690716; SSOLoginState=1476154717; YF-V5-G0=69afb7c26160eb8b724e8855d7b705c6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; _s_tentry=login.sina.com.cn; Apache=6840462826658.04.1476154738002; ULV=1476154738313:3:2:2:6840462826658.04.1476154738002:1475993331330; UOR=,,login.sina.com.cn; YF-Page-G0=e3ff5d70990110a1418af5c145dfe402; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; TC-Page-G0=6fdca7ba258605061f331acb73120318";
		String content = splider.getHtmlContent(url, url);

		content = DomTree.weiboSearchJsHtml(content);
		Node node = DomTree.getNode(content, null);

		DomTree.printNode("node", node);
		//NodeList nl = DomTree.commonList("//P[@node-type='feed_list_content']", node);
		String xpath = "//P[@class='star_name']/A[1]/@href";
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent();
			s = "http://s.weibo.com"+DomTree.trimInnerSpaceStr(s);
			System.out.println(s);
		}
		System.out.println("nl.getLength()"+nl.getLength());
	}
	
	
	
	public static void main(String[] args) throws Exception {
		monitor();
	}
	public static void monitor() throws Exception {
		Splider sp = new Splider();

		String[] mkeyvalues = { 
				"http://weibo.com/p/1005055615924665/info?mod=pedit_more", //0
				"http://weibo.com/p/1005055232671688/info?mod=pedit_more", //1
				"http://weibo.com/p/1006051730077315/info?mod=pedit_more", //2
				"http://weibo.com/p/1005051807810307/info?mod=pedit_more", //3
				"http://weibo.com/p/1005053042214087/info?mod=pedit_more", //4
				"http://weibo.com/3230122083/about"						   //5
				};
		SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		SystemCommon.currentCookie = "SINAGLOBAL=8574716518633.067.1475226495557; wvr=6; TC-Ugrow-G0=5e22903358df63c5e3fd2c757419b456; SCF=Au34eOHW9vCEpOlPBAVdrn3J3RS_DvJLdrWJUX2TS6nLqI57xSVYdV_-EquKjHeMwDrWbHwJmPuyLXb35GoJCvA.; SUB=_2A256-CUNDeTxGeRK61UZ8SbIzz2IHXVZjBHFrDV8PUNbmtANLRPXkW8NoQqbdYsxRIy_6hOp1f_nIQSqvQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFLaUa9E2iGSFaS52z1uGc35JpX5KMhUgL.FozXehMReKnXSh22dJLoI7yAIPLf9KzXSntt; SUHB=0jBZNvQmCLnVmB; ALF=1507690716; SSOLoginState=1476154717; YF-V5-G0=69afb7c26160eb8b724e8855d7b705c6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; _s_tentry=login.sina.com.cn; Apache=6840462826658.04.1476154738002; ULV=1476154738313:3:2:2:6840462826658.04.1476154738002:1475993331330; UOR=,,login.sina.com.cn; YF-Page-G0=e3ff5d70990110a1418af5c145dfe402; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; TC-Page-G0=6fdca7ba258605061f331acb73120318";
	
		String url = mkeyvalues[3];
		String content = sp.getHtmlContent(url, "");
		//System.out.println(content);
		content = DomTree.weiboMonitorJsHtml(content);
		
		//System.out.println(content);
		
		Node node = DomTree.getNode(content, null);

		//DomTree.printNode("node", node);
		
		//String xpath = BloggersXPath.baseInfo;
					 //"//LI[@class='li_1 clearfix']";
		String authorImageXPath = "//P[@class='photo_wrap']/IMG/@src";
		String xpath = "//DIV[@class='pf_photo'][@node-type='photo']/A/EM/@class";
		
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			//System.out.println(DomTree.trimInnerSpaceStr(nl.item(i).g));
			String s = nl.item(i).getTextContent();
			if(s.contains("W_icon icon_pf_approve")){
				System.out.println(i+":"+"个人认证");
			}else if(s.contains("W_icon_co2 icon_pf_approve_co")){
				System.out.println(i+":"+"企业认证");
			}else{
				
			}
			System.out.println(s);
			
		}
		System.out.println("monitor nl.getLength()"+nl.getLength());
	}
}
