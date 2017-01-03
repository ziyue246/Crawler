package common.http.sub;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import common.http.HtmlInfo;
import common.system.SystemCommon;

/**
 *
 * @author rzy 2016/12/19
 *
 */
public class HttpCnki {

	HashMap<String, String> cookieMap = new HashMap<String, String>();
	
	public String updateCookieMap(Header[] heads) {
		for (Header head : heads) {
			String lineCookie = head.getValue().split(";")[0];
			String cookieKey = lineCookie.split("=")[0].trim();
			String cookieValue = lineCookie.replace(cookieKey, "").trim();	
			if(!cookieValue.equals("=")){
				cookieMap.put(cookieKey, cookieValue);
			}
		}
		String cookie = "";
		for (String key : cookieMap.keySet()) {
			if(cookie==""){
				cookie = key + cookieMap.get(key);
			}else{
				cookie += "; "+key + cookieMap.get(key);
			}
		}
		return cookie+"; kc_cnki_net_uid=c8bf8056-57a5-248b-35a1-946320f8a00e";
	}

	public void getCookie(HtmlInfo html) {
		HttpClient client = HttpClients.createDefault();
		//html.setOrignUrl(loginUrl);
		
		//html.setOrignUrl("http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB");
		HttpGet get = new HttpGet(html.getOrignUrl());
		get.setHeader("User-Agent", html.getUa());
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Host", "acad.cnki.net");
		get.setHeader("Proxy-Connection", "keep-alive");
		get.setHeader("Upgrade-Insecure-Requests", "1");
	
		HttpResponse response = null;
		try {
			response = client.execute(get);
			SystemCommon.printLog("获取Cookie get status:" + response.getStatusLine());
			//SystemCommon.printHeaders(response.getAllHeaders());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (response.containsHeader("Set-Cookie")) {
			html.setCookie(updateCookieMap(response.getHeaders("Set-Cookie")));
		}
		if (response.containsHeader("Location")) {
			try {
				String locationUrl = response.getFirstHeader("Location").getValue();
				html.setOrignUrl(locationUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public String simpleGet(HtmlInfo html) {
		
		HttpClient client = HttpClients.createDefault();

		HttpGet get = new HttpGet(html.getOrignUrl());
		get.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
		html.setContent(null);
		get.setHeader("User-Agent", html.getUa());
		get.setHeader("Connection", "keep-alive");
		if(html.getCookie()!=null){
			get.setHeader("Cookie", html.getCookie());
		}
		if(html.getReferUrl()!=null){
			get.setHeader("Referer",html.getReferUrl());
		}
		html.setOrignUrl(html.getOrignUrl());
		HttpResponse response = null;
		try {
			response = client.execute(get);
			
			//SystemCommon.printLog("获取Cookie get status:" + response.getStatusLine());
			if (response.containsHeader("Set-Cookie")) {
				html.setCookie(updateCookieMap(response.getHeaders("Set-Cookie")));
			}
			if (response.containsHeader("Location")){
	            String realUrl = response.getFirstHeader("Location").getValue();
	            html.setRealUrl(realUrl);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			HttpEntity httpEntity = response.getEntity();
			String result = null;
			try {
				result = EntityUtils.toString(httpEntity,html.getEncode());
			} catch (IOException e) {
				e.printStackTrace();
			}
			html.setContent(result);
		}
		return response.toString();
	}

}
