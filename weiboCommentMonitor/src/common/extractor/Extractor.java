package common.extractor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.Comment;
import common.CommentXPath;
import common.ConnectSql;
import common.DomTree;
import common.MD5Util;
import common.Splider;
import common.SystemCommon;
import common.WeiboData;
import common.WeiboDataProcessed;

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
	public static String getBloggerinfo(WeiboDataProcessed weibo){
		String commentUrl = weibo.getCommentUrl();
		SystemCommon.printLog("getBloggerinfo url:"+weibo.getUrl());
		Splider splider = new Splider();
		String content = splider.getHtmlContent(commentUrl, null);
		
		content = DomTree.weiboMonitorJsHtml(content);
		Node node = null;
		try {
			node = DomTree.getNode(content, null);
			getCommentSet(node,weibo);
			return getNextPage(node, weibo);
			//id=3888502346881634&page=2
			//http://weibo.com/aj/comment/big?id=3888502346881634
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static String getNextPage(Node node,WeiboDataProcessed weibo){
		String url = weibo.getCommentUrl();
		//id=3888502346881634&page=2
		//http://weibo.com/aj/comment/big?id=3888502346881634
		NodeList nextPageln = DomTree.commonList(CommentXPath.nextPage, node);
		if(nextPageln.getLength()>0){
			String nextPage = nextPageln.item(0).getTextContent();
			if(nextPage.indexOf("page")>-1){
				nextPage = url.split("id")[0]+nextPage;
				SystemCommon.printLog(nextPage);
				weibo.setCommentUrl(nextPage);
				return nextPage;
			}
		}
		return null;
	}
	public static void getCommentSet(Node node,WeiboDataProcessed weibo){
			
		NodeList authornl = DomTree.commonList(CommentXPath.author, node);
		NodeList authorImgnl = DomTree.commonList(CommentXPath.authorImg, node);
		NodeList authorUrlnl = DomTree.commonList(CommentXPath.authorUrl, node);
		NodeList contentnl = DomTree.commonList(CommentXPath.content, node);
		NodeList pubtimenl = DomTree.commonList(CommentXPath.pubtime, node);
		NodeList likeCountnl = DomTree.commonList(CommentXPath.likeCount, node);
		SystemCommon.printLog("comment authornl length:"+authornl.getLength());
		for (int i = 0; i < authornl.getLength(); i++) {
			Comment comment = new Comment();
			System.out.println("weibo.getUrl()"+weibo.getUrl());
			comment.setUrl(weibo.getUrl());
			comment.setCommentUrl(weibo.getCommentUrl());	
			comment.setGps(weibo.getGPSinfo());
			System.out.println(pubtimenl.item(i).getTextContent());
			String pubtime = pubtimenl.item(i).getTextContent().replace("(", "").replace(")", "");
			
			comment.setPubtime(getPubtime(pubtime));	
			comment.setCategoryCode(Integer.parseInt(weibo.getCategoryCode()));
			comment.setAuthor(authornl.item(i).getTextContent());
			comment.setAuthorImg(authorImgnl.item(i).getTextContent());
			String authorurl = authorUrlnl.item(i).getTextContent().replace("/", "");
			if(authorurl.matches("[0-9]+")){
				authorurl = "http://weibo.com/u/"+authorurl;
			}else{
				authorurl = "http://weibo.com/"+authorurl;
			}
			comment.setAuthorUrl(authorurl);
			
			String content = contentnl.item(i).getTextContent().replaceAll("\\s+", "");
		
			comment.setContent(content.split("：")[1].split("[(]")[0]);
			String likeCount = likeCountnl.item(i).getTextContent().replace("(", "").replace(")", "");
			if(likeCount.length()>0)
				comment.setLikeCount(Integer.parseInt(likeCount));
			comment.setMd5(MD5Util.MD5(comment.getAuthor()+comment.getPubtime()+comment.getUrl()+comment.getContent()));
			SystemCommon.printComment(comment);
			ConnectSql.insertMessage(comment);
		}
	}
	private static Date getPubtime(String pubtime){
		SimpleDateFormat ymdfmt = new  SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf  =   new  SimpleDateFormat("yyyy-MM-dd HH:mm" );
		
		
		if(pubtime.contains("分钟前")){
			int am = Integer.parseInt(pubtime.split("分钟前")[0]);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -am);
			return calendar.getTime();
			
		}else if(pubtime.contains("今天")){
			pubtime = pubtime.replaceAll("今天", ymdfmt.format(new Date()));
		}
		if(valiDateTimeWithLongFormat(pubtime)){
			try {
				
				return sdf.parse(pubtime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return new Date();
	}
	
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}
}