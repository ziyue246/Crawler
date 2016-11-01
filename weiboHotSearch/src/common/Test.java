package common;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test {
	public static void search() throws Exception {
		SystemCommon.User_Agent = "";
		SystemCommon.currentCookie = "";
		
		
		Splider splider = new Splider();
		String url = "http://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
		String content = splider.getHtmlContent(url, url);

		content = DomTree.weiboSearchJsHtml(content);
		Node node = DomTree.getNode(content, null);

		//DomTree.printNode("node", node);
		//NodeList nl = DomTree.commonList("//P[@node-type='feed_list_content']", node);
		String urlpath = "//P[@class='star_name']/A[1]/@href";
		String contentpath = "//P[@class='star_name']/A[1]";
		//<td class="td_03"><p class="star_num"><span>
		String indexPath = "//TD[@class='td_03']/P[@class='star_num']/SPAN";
		NodeList nl = DomTree.commonList(urlpath, node);
		NodeList nl2 = DomTree.commonList(contentpath, node);
		NodeList nl3 = DomTree.commonList(indexPath, node);
		for (int i = 0; i < nl2.getLength(); i++) {
			//String s = nl.item(i).getTextContent();
			String s2 = nl2.item(i).getTextContent();
			String s3 = nl3.item(i).getTextContent();
			//s = "http://s.weibo.com"+DomTree.trimInnerSpaceStr(s);
			System.out.println(s2+"\t:\t"+Integer.parseInt(s3));
		}
		System.out.println("nl.getLength()"+nl.getLength());
	}
	
	
	
	public static void main(String[] args) throws Exception {
		search();
	}//4
	public static void monitor() throws Exception {
		Splider sp = new Splider();

		String[] mkeyvalues = { 
				"http://weibo.com/p/1005055615924665/info?mod=pedit_more", 
				"http://weibo.com/p/1005055232671688/info?mod=pedit_more", 
				"http://weibo.com/p/1006051730077315/info?mod=pedit_more",
				"http://weibo.com/p/1005051807810307/info?mod=pedit_more",
				"http://weibo.com/p/1005053042214087/info?mod=pedit_more"
				};
		
		String url = mkeyvalues[4];
		String content = sp.getHtmlContent(url, "");
		content = DomTree.weiboMonitorJsHtml(content);
		Node node = DomTree.getNode(content, null);

		//DomTree.printNode("node", node);
		
		//String xpath = BloggersXPath.baseInfo;
					 //"//LI[@class='li_1 clearfix']";
		String xpath = "//TD[@class='S_line1']/A[1]/@href";
		
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			//System.out.println(DomTree.trimInnerSpaceStr(nl.item(i).g));
			System.out.println(i+":"+(nl.item(i).getTextContent()));
		}
		System.out.println("monitor nl.getLength()"+nl.getLength());
	}
}
