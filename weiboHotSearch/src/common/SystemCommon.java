package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SystemCommon {
	public static String User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";
	public static String currentCookie;
	public static void sleeps(int s){
		Random random = new Random();
		try {
			Thread.sleep((s+random.nextInt(5))*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void printLog(String s){
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.print(dateFormat.format(date)+" : ");
		System.out.println(s);
		
	}
	public static void printBlog(Bloggers blog){
		printLog("author    \t:"+blog.getAuthor());
		printLog("address   \t:"+blog.getAddress());
		printLog("birth     \t:"+blog.getBirth());
		printLog("fansnum   \t:"+blog.getFansNum());
		printLog("fansUrl   \t:"+blog.getFansUrl());
		printLog("weibonum  \t:"+blog.getWeiboNum());
		printLog("weiboUrl  \t:"+blog.getWeiboUrl());
		printLog("follownum \t:"+blog.getFollowNum());
		printLog("followUrl \t:"+blog.getFollowUrl());
		printLog("info      \t:"+blog.getInfo());
		printLog("infourl   \t:"+blog.getInfoUrl());
		printLog("nick      \t:"+blog.getNick());
		printLog("provice   \t:"+blog.getProvince());
		printLog("weibourl  \t:"+blog.getWeiboUrl());
		printLog("sex       \t:"+blog.getSex());
		printLog("registtime\t:"+blog.getRegistTime());
		printLog("md5       \t:"+blog.getMd5());
		printLog("tag       \t:"+blog.getTag());
		
	}
	public static void printHotSearch(HotSearch hotSearch) {
		printLog("keyword       \t:"+hotSearch.getKeyWord());
		printLog("searchIndex   \t:"+hotSearch.getSearchIndex());
	}
}
