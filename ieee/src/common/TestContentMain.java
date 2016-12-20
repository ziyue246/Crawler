package common;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestContentMain {
	public static void search() throws Exception {
		Splider splider = new Splider();
		String url = "http://baijia.baidu.com/?tn=search&word=%E5%A7%9A%E6%98%8E";
		url = "http://zdwang.baijia.baidu.com/article/555030";
		url = "http://www.sogou.com/web?query=%E5%8D%97%E6%B5%B7%E9%97%AE%E9%A2%98%E5%8F%91%E5%B8%83%E4%BC%9A&sut=2061&lkt=0%2C0%2C0&interation=196647&sst0=1479190215444&cid=&page=1&ie=utf8&p=40040100&dp=1&w=01029901&dr=1";
		url = "http://www.sogou.com/web?user_ip=103.254.67.40&sourceid=hint&bh=1&hintidx=1&query=%E5%B0%8F%E6%9C%88%E6%9C%88%E6%BC%AB%E7%94%BB&cid=&w=01020600&interation=196647&interV=&htdbg=idc%3Ebjdjt%7CdbgID%3E01%7Cabt%3E7%7Cmth%3E1&ie=utf8";
		
		SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		//SystemCommon.currentCookie = "SINAGLOBAL=8574716518633.067.1475226495557; wvr=6; TC-Ugrow-G0=5e22903358df63c5e3fd2c757419b456; SCF=Au34eOHW9vCEpOlPBAVdrn3J3RS_DvJLdrWJUX2TS6nLqI57xSVYdV_-EquKjHeMwDrWbHwJmPuyLXb35GoJCvA.; SUB=_2A256-CUNDeTxGeRK61UZ8SbIzz2IHXVZjBHFrDV8PUNbmtANLRPXkW8NoQqbdYsxRIy_6hOp1f_nIQSqvQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFLaUa9E2iGSFaS52z1uGc35JpX5KMhUgL.FozXehMReKnXSh22dJLoI7yAIPLf9KzXSntt; SUHB=0jBZNvQmCLnVmB; ALF=1507690716; SSOLoginState=1476154717; YF-V5-G0=69afb7c26160eb8b724e8855d7b705c6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; _s_tentry=login.sina.com.cn; Apache=6840462826658.04.1476154738002; ULV=1476154738313:3:2:2:6840462826658.04.1476154738002:1475993331330; UOR=,,login.sina.com.cn; YF-Page-G0=e3ff5d70990110a1418af5c145dfe402; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; TC-Page-G0=6fdca7ba258605061f331acb73120318";
		
		String content = splider.getHtmlContent(url, "utf-8");
		//System.out.println(content);
		//content = DomTree.weiboSearchJsHtml(content);
		Node node = DomTree.getNode(content, null);

		//DomTree.printNode("node", node);
		// class="r-sech ext_query"
		//<li class="str-text-info">
		//<li class="str-text-info"><div class="str_info_div"> <div class="vrwrap">
		String hxpath = "//DIV[@class='vrwrap']";
		///ul class="str-list-v4"   li class="str-text-info"
		String xpath  = hxpath+"//H3[@class='vrTitle']/A/@href";//"//LI[@class='str-text-info']/SPAN";
		//div id="rb_0"
		/*
			<!-- 列表页 -->
			<!-- head = //DIV[@class='vrwrap'][num] -->
			<prop name="title" value="//H3[@class='vrTitle']/A"/>
			<prop name="url"   value="//H3[@class='vrTitle']/A/@href"/>
			<prop name="brief" value="//UL[@class='str-list-v4']/LI[@class='str-text-info']/SPAN"/>
			
			<prop name="pubtime" value="//UL[@class='str-list-v4']/LI[1]/SPAN"/>
			<prop name="next" value="//A[@class='np']/@href"/>
			<prop name="next_prefix" value="http://www.sogou.com/web"/>
			<prop name="author" value="//UL[@class='str-list-v4']/LI[1]/A[1]"/>
			<!-- 需要去掉{.} -->
			<!-- 内容页 -->
			<prop name="imgs_url" value="//P[@class='image']/IMG/@src"/>
			<prop name="content" value="//DIV[@class='article-detail']"/>
			<prop name="openId" value=""/>
		 */
		
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent().trim();
			
			System.out.println(s);
		}
		System.out.println("nl.getLength()"+nl.getLength());
	}
	
	public static void main(String[] args) throws Exception {
		search();
		//PreparedStatement ps = con.prepareStatement(jasql, new String[] { "id" });
	}
}
