package common;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Extractor {
	
	public static Set<String> getBloggerUrls(String url){
		Splider splider = new Splider();
		String content = splider.getHtmlContent(url, url);
		content = DomTree.weiboSearchJsHtml(content);
		Set<String> set = new HashSet<String>();
		Node node = null;
		try {
			node = DomTree.getNode(content, null);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xpath = "//DIV[@class='face']/A[1]/@href";
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent();
			s = DomTree.trimInnerSpaceStr(s);
			set.add(s);
		}
		System.out.println("getBloggerUrls nl.getLength()"+nl.getLength());
		return set;
	}
	public static String getHostUrl(String url){
		Splider splider = new Splider();
		final String keyurl = "http://weibo.com/p/<keyword>/info?mod=pedit_more";
		String content = splider.getHtmlContent(url, url);
		String []contents = content.split("\n");
		for (String s : contents) {
			if(s.contains("$CONFIG['page_id']='")){
				String pageId = s.split("='")[1].split("'")[0];
				return keyurl.replace("<keyword>", pageId);
			}
		}
		return null;
	}
	public static Set<String> getHotSearchUrls(){
		Splider splider = new Splider();
		Set<String> set = new HashSet<String>();
		String url = "http://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
		String content = splider.getHtmlContent(url, url);

		content = DomTree.weiboSearchJsHtml(content);
		Node node = null;
		try {
			node = DomTree.getNode(content, null);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//DomTree.printNode("node", node);
		//NodeList nl = DomTree.commonList("//P[@node-type='feed_list_content']", node);
		String xpath = "//P[@class='star_name']/A[1]/@href";
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent();
			s = "http://s.weibo.com"+DomTree.trimInnerSpaceStr(s);
			set.add(s);
		}
		System.out.println("getHotSearchUrls nl.getLength()"+nl.getLength());
		return set;
	}
	public static Bloggers getBloggerinfo(String url,String baseUrl){
		System.out.println("hostUrl:"+url);
		Splider splider = new Splider();
		String content = splider.getHtmlContent(url, null);

		content = DomTree.weiboMonitorJsHtml(content);
		Node node = null;
		try {
			node = DomTree.getNode(content, null);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bloggers blog = new Bloggers();
		blog.setAuthorUrl(baseUrl);
		getblogAuthor(blog, node);
		getblogBaseInfo(blog, node);
		getblogTag(blog, node);
		getblogFollowSoOn(blog, node);
		getblogFollowSoOnUrl(blog, node);
		getCategoryCode(blog,node);
		blog.setMd5(MD5Util.MD5(blog.getAuthor()+blog.getAuthorUrl()+blog.getCategoryCode()));
		
		return blog;
	}
	private static void getblogAuthor(Bloggers blog,Node node){
		String xpath = BloggersXPath.author;
		NodeList nl = DomTree.commonList(xpath, node);
		String s = nl.item(0).getTextContent();
		blog.setAuthor(s);
		
		
		String imgxpath = BloggersXPath.authorImg;
		NodeList imgnl = DomTree.commonList(xpath, node);
		String imgs = imgnl.item(0).getTextContent();
		blog.setAuthor(imgs);
	}
	private static void getblogTag(Bloggers blog,Node node){
		String xpath = BloggersXPath.tag;
		NodeList nl = DomTree.commonList(xpath, node);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getNextSibling().getTextContent();
			sb.append(s+";");
		}
		blog.setTag(sb.toString());
	}
	private static void getCategoryCode(Bloggers blog,Node node){
		String xpath = BloggersXPath.categoryCode;
		NodeList nl = DomTree.commonList(xpath, node);
		String s = nl.item(0).getTextContent();
		if(s.contains("W_icon icon_pf_approve")){
			blog.setCategoryCode(1);
		}else if(s.contains("W_icon_co2 icon_pf_approve_co")){
			blog.setCategoryCode(2);
		}else{
			blog.setCategoryCode(0);
		}
	}
	private static void getblogBaseInfo(Bloggers blog,Node node){
		String xpath = BloggersXPath.baseInfo;
		NodeList nl = DomTree.commonList(xpath, node);
		
		/*
		昵称：黄晓明
		所在地：北京 东城区
		性别：男
		个性域名：http://weibo.com/huangxiaoming
		简介：少说话，多做事！我不是最好的
		注册时间：2010-04-14
		标签：歌手演员
		*/
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent();
			if(s.contains("昵称")){
				blog.setNick(s.split("：")[1]);
			}else if(s.contains("所在地")){
				blog.setAddress(s.split("：")[1]);
			}
			else if(s.contains("性别")){
				blog.setSex(s.split("：")[1]);
			}
			else if(s.contains("个性域名")){
				blog.setInfoUrl(s.split("：")[1]);
			}
			else if(s.contains("简介")){
				blog.setInfo(s.split("：")[1]);
			}
			else if(s.contains("生日")){
				blog.setBirth(s.split("：")[1]);
			}
			else if(s.contains("注册时间：")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					blog.setRegistTime( sdf.parse(s.split("：")[1]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}		
	}
	/*
	577关注
	49358613粉丝
	2870微博
	*/
	private static void getblogFollowSoOn(Bloggers blog,Node node){
		String xpath = BloggersXPath.followSoOn;
		NodeList nl = DomTree.commonList(xpath, node);
		
		String s = nl.item(0).getTextContent();
		blog.setFollowNum(Integer.parseInt(s.replaceAll("[^(0-9)]","")));
		
		s = nl.item(1).getTextContent();
		blog.setFansNum(Integer.parseInt(s.replaceAll("[^(0-9)]","")));
		
		s = nl.item(2).getTextContent();
		blog.setWeiboNum(Integer.parseInt(s.replaceAll("[^(0-9)]","")));
		
	}
	/*
	0:http://weibo.com/p/1006051730077315/follow?from=page_100605&wvr=6&mod=headfollow#place
	1:http://weibo.com/p/1006051730077315/follow?relate=fans&from=100605&wvr=6&mod=headfans&current=fans#place
	2:http://weibo.com/p/1006051730077315/home?from=page_100605_profile&wvr=6&mod=data#place 
	 */
	private static void getblogFollowSoOnUrl(Bloggers blog,Node node){
		String xpath = BloggersXPath.followSoOnUrl;
		NodeList nl = DomTree.commonList(xpath, node);
		blog.setFollowUrl(nl.item(0).getTextContent());
		blog.setFansUrl(nl.item(1).getTextContent());
		blog.setWeiboUrl(nl.item(2).getTextContent());
	}
}
