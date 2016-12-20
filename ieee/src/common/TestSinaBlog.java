package common;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestSinaBlog {
	
	public static void test() throws Exception {
		Splider splider = new Splider();
		String url = "http://v.youku.com/v_show/id_XNjExMTE5Nzcy.html?from=s1.8-1-1.2&spm=a2h0k.8191407.0.0";
		
		url = "http://search.sina.com.cn/?c=blog&q=%BB%C6%CF%FE%C3%F7%D7%CA%D6%FA%B0%D7%D1%AA%B2%A1%BB%BC%D5%DF%C8%A5%CA%C0&range=article&by=&sort=time";
		url="http://blog.sina.com.cn/s/blog_14ece22940102zu6m.html";
		url="http://search.sina.com.cn/?c=blog&q=%E5%BE%AE%E5%8D%9A%E6%90%9E%E7%AC%91%E6%8E%92%E8%A1%8C%E6%A6%9C&range=article&by=&sort=time&ie=utf-8";
		
		SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		//SystemCommon.currentCookie = "SINAGLOBAL=8574716518633.067.1475226495557; wvr=6; TC-Ugrow-G0=5e22903358df63c5e3fd2c757419b456; SCF=Au34eOHW9vCEpOlPBAVdrn3J3RS_DvJLdrWJUX2TS6nLqI57xSVYdV_-EquKjHeMwDrWbHwJmPuyLXb35GoJCvA.; SUB=_2A256-CUNDeTxGeRK61UZ8SbIzz2IHXVZjBHFrDV8PUNbmtANLRPXkW8NoQqbdYsxRIy_6hOp1f_nIQSqvQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFLaUa9E2iGSFaS52z1uGc35JpX5KMhUgL.FozXehMReKnXSh22dJLoI7yAIPLf9KzXSntt; SUHB=0jBZNvQmCLnVmB; ALF=1507690716; SSOLoginState=1476154717; YF-V5-G0=69afb7c26160eb8b724e8855d7b705c6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; _s_tentry=login.sina.com.cn; Apache=6840462826658.04.1476154738002; ULV=1476154738313:3:2:2:6840462826658.04.1476154738002:1475993331330; UOR=,,login.sina.com.cn; YF-Page-G0=e3ff5d70990110a1418af5c145dfe402; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; TC-Page-G0=6fdca7ba258605061f331acb73120318";
		
		
		String content = splider.getHtmlContent(url, url);
//		/content.replaceAll("script", "\n\n");
		System.out.println(content);
		//content = DomTree.weiboSearchJsHtml(content);
		
//		TITLE   //DIV/H2/A
//		AUTHOR   //A[@class='rib-author']
//		SITE_ID   SITE_TEMPLATE 表里面的id
//		PUBTIME   //SPAN[@class='fgray_time']
//		URL			//H2[@class='r-info-blog-tit']/A/@href
//		SEARCH_KEYWORD    表
//		SOURCE         null
//		CATEGORY_CODE   表
//		INSERTTIME   
//		MD5           SEARCH_KEYWORD+url
//		CONTENT         //DIV[@id='sina_keyword_ad_area2']
//		BRIEF         //P[@class='content']
//		IMG_URL			//@data-origin-src
//		next = 
		
		
		Node node = DomTree.getNode(content, "gb2312");//"gb2312"
		
		//DomTree.printNode("node", node);
		String xpath = "//A[@class='rib-author']";

		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent();
			//s = "http://s.weibo.com"+DomTree.trimInnerSpaceStr(s);
			System.out.println(s);
		}
		String commentUrl = "http://comments.youku.com/comments/~ajax/vpcommentContent.html?"
				+ "__callback=vpcommentContent_html&__ap={"
				+ "\"videoid\":\"<videoid>\","
				+ "\"showid\":\"<showid>\",\"isAjax\":1,\"sid\":\"\",\"page\":1,\"chkpgc\":0,\"last_modify\":\"\"}";
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
