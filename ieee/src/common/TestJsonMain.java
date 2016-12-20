package common;

import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class TestJsonMain {
	

	public static String extractOne(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			sb.append(m.group().trim());
			break;
		}
		return sb.toString();
	}
	public static void json() throws Exception {
		Splider splider = new Splider();
		String url = "http://baijia.baidu.com/?tn=search&word=%E5%A7%9A%E6%98%8E";
		url = "http://zdwang.baijia.baidu.com/article/555030";
		url = "http://baijia.baidu.com/ajax/searcharticle?page=2&pagesize=20&word=2015%E5%B9%B4";
		url = "http://baijia.baidu.com/ajax/searcharticle?page=2&pagesize=20&word=%E5%A5%BD%E5%A5%87%E6%A0%91%E6%87%92%E8%BF%9B%E5%8F%91%E7%94%B5%E5%8E%82";
		//SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		//SystemCommon.currentCookie = "SINAGLOBAL=8574716518633.067.1475226495557; wvr=6; TC-Ugrow-G0=5e22903358df63c5e3fd2c757419b456; SCF=Au34eOHW9vCEpOlPBAVdrn3J3RS_DvJLdrWJUX2TS6nLqI57xSVYdV_-EquKjHeMwDrWbHwJmPuyLXb35GoJCvA.; SUB=_2A256-CUNDeTxGeRK61UZ8SbIzz2IHXVZjBHFrDV8PUNbmtANLRPXkW8NoQqbdYsxRIy_6hOp1f_nIQSqvQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFLaUa9E2iGSFaS52z1uGc35JpX5KMhUgL.FozXehMReKnXSh22dJLoI7yAIPLf9KzXSntt; SUHB=0jBZNvQmCLnVmB; ALF=1507690716; SSOLoginState=1476154717; YF-V5-G0=69afb7c26160eb8b724e8855d7b705c6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; _s_tentry=login.sina.com.cn; Apache=6840462826658.04.1476154738002; ULV=1476154738313:3:2:2:6840462826658.04.1476154738002:1475993331330; UOR=,,login.sina.com.cn; YF-Page-G0=e3ff5d70990110a1418af5c145dfe402; TC-V5-G0=52dad2141fc02c292fc30606953e43ef; TC-Page-G0=6fdca7ba258605061f331acb73120318";
		url = "http://www.yidianzixun.com/home?page=article&id=0F3kXn3w&up=44";
		url = "http://www.toutiao.com/search_content/?offset=0&format=json&keyword=%E9%BB%84%E6%99%93%E6%98%8E&autoload=true&count=20&_=1480400339679";
		url = "http://www.toutiao.com/a6358176393390653698/";
		
		String commentUrl = "http://www.yidianzixun.com/api/q/?path=contents/comments&version=999999&docid=<id>&count=1000 ";

		//commentUrl = commentUrl.replace("<id>", url.split("&id=")[1].split("&up")[0]);
		
		String content = splider.getHtmlContent(url, "utf-8");
		
		String group_id = extractOne(url,"\\d+");
		String  item = content.split("item_id:")[1].split("\n")[0];
		String item_id = extractOne(item,"\\d+");
		System.out.println(group_id);
		System.out.println(item_id);
		
		commentUrl = "http://www.toutiao.com/api/comment/list/?group_id=<group_id>&item_id=<item_id>&offset=0&count=5"; 
		
		commentUrl = commentUrl.replace("<group_id>", group_id).replace("<item_id>", item_id);
		
		content = splider.getHtmlContent(commentUrl, "utf-8");
		JSONArray jarray = JSONObject.fromObject(content).getJSONObject("data")
				.getJSONArray("comments");
		
		for (Object obj : jarray) {
			JSONObject  jobj 	= (JSONObject)obj;
			String 		name 	= jobj.getJSONObject("user").getString("name");
			String 		uimgUrl = jobj.getJSONObject("user").getString("avatar_url");
			String time = jobj.getString("create_time");
			String comment = jobj.getString("text");
			
			String formats = "yyyy-MM-dd HH:mm:ss";
			Long timestamp = Long.parseLong(time)*1000;    
			String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
			 
			 
			System.out.println("name 	: "+name);
			System.out.println("uimgUrl : "+uimgUrl);
			System.out.println("time 	: "+date);
			System.out.println("comment : "+comment);

			System.out.println();
		}	
	}
	
	
	public static void main(String[] args) throws Exception {
		json();
		//PreparedStatement ps = con.prepareStatement(jasql, new String[] { "id" });
	}
}
