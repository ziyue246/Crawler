package common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import http.HtmlInfo;
import http.Httpieee;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IeeeMain {

	
	public static void getIeeeList() {
	
		HtmlInfo html = new HtmlInfo();	
		Httpieee http = new Httpieee();
		
		html.setEncode("UTF-8");
		html.setUa(SystemCommon.User_Agent);
		http.getCookie(html);
// 
		String keyword = "Publications Access Articles";// Access Articles
		String searchUrl = "http://ieeexplore.ieee.org/search/searchresult.jsp?"
				+ "action=search&sortType=&rowsPerPage=&searchField=Search_All&matchBoolean=true&"
				+ "queryText=(%22Document%20Title%22:"+SystemCommon.urlEncode(keyword, "utf-8")+")&refinements=4291944822";
		//searchUrl = searchUrl.replace(" ","%20");
		//searchUrl = URLEncoder.encode("searchUrl", "utf-8");
		html.setRealUrl(searchUrl);
		http.simpleGet(html);
		System.out.println("2 get cookie:"+html.getCookie());		
		
		String postJsonUrl = "http://ieeexplore.ieee.org/rest/search";

		html.setRealUrl(postJsonUrl);
		JSONObject obj = new JSONObject();
		obj.element("queryText", "(\"Document Title\":"+keyword+")");
        obj.element("refinements", "[\"4291944822\"]");
        obj.element("matchBoolean", "true");
        obj.element("searchField", "Search_All");
		http.postJson(html,obj.toString());
		System.out.println(html.getContent());
	}
	public static void JsonParser2(String content){
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
		//System.out.println(Calendar.getInstance().get(Calendar.YEAR));
		getIeeeList();
	}
}
