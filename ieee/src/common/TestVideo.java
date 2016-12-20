package common;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestVideo {
	
	public static void test() throws Exception {
		Splider splider = new Splider();
		String url = "http://v.youku.com/v_show/id_XNjExMTE5Nzcy.html?from=s1.8-1-1.2&spm=a2h0k.8191407.0.0";
		
		url = "http://v.youku.com/v_show/id_XNDAyNDM4NTQw.html?from=s1.8-1-1.2";
		//SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		//SystemCommon.currentCookie = "SINAGLOBAL=8574716518633.067.1475226495557; wvr=6; TC-Ugrow-G0=5e22903358df63c5e3fd2c757419b456; SCF=Au34eOHW9vCEpOlPBAVdrn3J3RS_DvJLdrWJUX2TS6nLqI57xSVYdV_-EquKjHeMwDrWbHwJmPuyLXb35GoJCvA.; SUB=_2A256-CUNDeTxGeRK61UZ8SbIzz2IHXVZjBHFrDV8PUNbmtANLRPXkW8NoQqbdYsxRIy_6hOp1f_nIQSqvQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFLaUa9E2iGSFaS52z1uGc35JpX5KMhUgL.FozXehMReKnXSh22dJLoI7yAIPLf9KzXSntt; SUHB=0jBZNvQmCLnVmB; ALF=1507690716; SSOLoginState=1476154717; YF-V5-G0=69afb7c26160eb8b724e8855d7b705c6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; _s_tentry=login.sina.com.cn; Apache=6840462826658.04.1476154738002; ULV=1476154738313:3:2:2:6840462826658.04.1476154738002:1475993331330; UOR=,,login.sina.com.cn; YF-Page-G0=e3ff5d70990110a1418af5c145dfe402; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; TC-Page-G0=6fdca7ba258605061f331acb73120318";
		
		
		String content = splider.getHtmlContent(url, url);
//		/content.replaceAll("script", "\n\n");
		//System.out.println(content);
		//content = DomTree.weiboSearchJsHtml(content);
		
		Node node = DomTree.getNode(content, null);
		
		//DomTree.printNode("node", node);
		String xpath = "//H1[@class='title']/A";
//		<span class="num" id="upVideoTimes" data-stat-role="ck">70</span>
		
		
//		protected String url;
		//   //DIV[@class='v-meta va']/DIV[@class='v-meta-title']/A/@href
//		protected String title;
		//   //DIV[@class='v-meta va']/DIV[@class='v-meta-title']/A/@title
//		private String authorUrl;//作者url 
		//   //DIV[@class='v-meta-data']/SPAN/A[@target='_blank']/@href
//		private int playCount;//播放次数
		//   //DIV[@class='v-meta-entry']/DIV/SPAN[@class='pub']
//		//http://comments.youku.com/comments/~ajax/vpcommentContent.html?__callback=vpcommentContent_html&__ap={"videoid":"400986325","showid":"0","isAjax":1,"sid":"","page":1,"chkpgc":0,"last_modify":""}
//		private String commentUrl;//评论url
		
		
//		private String pubtime;//发布时间
		//  "//DIV[@class='v-meta-entry']/DIV/SPAN[@class='r']"
//		private String tags;//标签
//		private String author;//作者
		//   //DIV[@class='v-meta-data']/SPAN/A[@target='_blank']
//		private String playtime;//播放时常
		//   //DIV[@class='v-thumb-tagrb']/SPAN[@class='v-time']
//		private String channel;//
		//  2page //H1[@class='title']/A	
		
		//http://v.youku.com/action/getVideoPlayInfo?beta&timestamp=<timestamp>&vid=152779943&showid=280707&param%5B%5D=share&param%5B%5D=favo&param%5B%5D=download&param%5B%5D=phonewatch&param%5B%5D=updown&callback=tuijsonp2
		//videoId:"152779943",  showid:"280707", 
//		private int likeCount;
		// 3page
//		private int dislikeCount;
		// 3page
//		String vid = content.split("videoId:\"")[1].split("\",")[0];
//		String showid = content.split("showid:\"")[1].split("\",")[0];
//		long timestamp = System.currentTimeMillis();
//		String likedislike = "http://v.youku.com/action/getVideoPlayInfo?beta&"
//				+ "timestamp=<timestamp>&"
//				+ "vid=<vid>&"
//				+ "showid=<showid>&param%5B%5D=share&param%5B%5D=favo&param%5B%5D=download&param%5B%5D=phonewatch&param%5B%5D=updown&callback=tuijsonp2";
//		String ss = likedislike.replace("<timestamp>", timestamp+"").
//				replace("<vid>", vid).replace("<showid>", showid);
//		System.out.println(ss);
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
