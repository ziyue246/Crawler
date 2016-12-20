package common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.http.Header;

public class SystemCommon {
	public static String User_Agent = "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36";
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
	public static String urlEncode(String str,String charset){
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void printLog(String s){
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.print(dateFormat.format(date)+" : ");
		System.out.println(s);
		
	}
	public static void printHeaders(Header []heads){
        for (Header header : heads) {
        	printLog(header.getName()+"\t:\t"+header.getValue());
		}
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
}
