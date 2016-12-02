package common.http.sub;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.message.BasicNameValuePair;

import common.bean.HtmlInfo;
import common.http.HttpInfo;
import common.http.SimpleHttpClient;
import common.util.StringUtil;

public class WosHttpClient extends SimpleHttpClient {
	private static String cookie;
	private static String sid;
	
	
	public static void main(String[] args) {
		HtmlInfo html = new HtmlInfo();
		html.setSite("");
		new WosHttpClient().getCookie(html);
	}
	
	public String getCookie(HtmlInfo html) {
		if(cookie==null || cookie.equals("")) {
			cookie = "";
			HttpGet g = new HttpGet("http://www.webofknowledge.com");
			g.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
			HttpClient client = httpClient(html);
			HttpResponse response = null;
			try {
				response = client.execute(g);
				for(Header co : response.getHeaders("Set-Cookie")) {
					if(co.getValue().startsWith("SID")) {
						sid = StringUtil.regMatcher(co.getValue(), "\"", "\"");
					}
					cookie += co.getValue().split(";")[0]+";";
				}
				g.abort();
				if(sid==null) {
					HttpGet get = null;
					try {
						String loc = response.getFirstHeader("Location").getValue();
						get = new HttpGet(loc);
						response = client.execute(get);
						
						for(Header co : response.getHeaders("Set-Cookie")) {
							if(co.getValue().startsWith("SID")) {
								sid = StringUtil.regMatcher(co.getValue(), "\"", "\"");
							}
							cookie += co.getValue().split(";")[0]+";";
						}
					} finally {
						get.abort();
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return cookie;
	}
	@Override
	public synchronized HttpInfo simpleGet(HtmlInfo html) {
		HttpClient client = httpClient(html);
		if(!html.getOrignUrl().startsWith("http://") && html.getOrignUrl().indexOf(":")>-1) {
			String[] arr = html.getOrignUrl().split(":");
			if(arr.length<3) throw new RuntimeException("采集配置错误！");
			String startYear = arr[1];
			String endYear = arr[1];
			if(arr.length>2) {
				endYear = arr[2];
			}
			
			if(cookie==null || sid==null) getCookie(html);
			
			HttpPost p = new HttpPost("http://apps.webofknowledge.com/UA_GeneralSearch.do");
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("fieldCount", "1"));
			list.add(new BasicNameValuePair("action", "search"));
			list.add(new BasicNameValuePair("product", "UA"));
			list.add(new BasicNameValuePair("search_mode", "GeneralSearch"));
			list.add(new BasicNameValuePair("SID", sid));//参数
			list.add(new BasicNameValuePair("max_field_count", "25"));
			list.add(new BasicNameValuePair("max_field_notice", "注意: 无法添加另一字段。"));
			list.add(new BasicNameValuePair("input_invalid_notice", "检索错误: 请输入检索词。"));
			list.add(new BasicNameValuePair("exp_notice", "检索错误: 专利检索词可在多个家族中找到 ("));
			list.add(new BasicNameValuePair("input_invalid_notice_limits", "<br/>注: 滚动框中显示的字段必须至少与一个其他检索字段相组配。"));
			list.add(new BasicNameValuePair("sa_params", "UA||"+sid+"|http://apps.webofknowledge.com|'"));
			list.add(new BasicNameValuePair("value(input1)", arr[0]));
			list.add(new BasicNameValuePair("value(select1)", arr[3]));
			list.add(new BasicNameValuePair("x", "31"));
			list.add(new BasicNameValuePair("y", "6"));
			list.add(new BasicNameValuePair("value(hidInput1)", ""));
			list.add(new BasicNameValuePair("limitStatus", "expanded"));
			list.add(new BasicNameValuePair("ss_lemmatization", "On"));
			list.add(new BasicNameValuePair("ss_spellchecking", "Suggest"));
			list.add(new BasicNameValuePair("SinceLastVisit_UTC", ""));
			list.add(new BasicNameValuePair("SinceLastVisit_DATE", ""));
			list.add(new BasicNameValuePair("range", "ALL"));
			list.add(new BasicNameValuePair("period", "Year Range"));
			list.add(new BasicNameValuePair("startYear", startYear));
			list.add(new BasicNameValuePair("endYear", endYear));
			
			list.add(new BasicNameValuePair("ssStatus", "display:none"));
			list.add(new BasicNameValuePair("ss_showsuggestions", "ON"));
			list.add(new BasicNameValuePair("ss_query_language", "auto"));
			
			p.setHeader("Content-Type", "application/x-www-form-urlencoded;text/html;charset=UTF-8");
			
			try {
				p.setEntity(new UrlEncodedFormEntity(list));
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			HttpResponse response = null;
			try {
				response = client.execute(p);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				p.abort();
			}
			
			String url = response.getFirstHeader("Location").getValue();
			if(url==null) return null;
			html.setOrignUrl(url);
		}
		
		HttpGet get = new HttpGet(html.getOrignUrl());
		HttpResponse response = null;
		try {
			response = client.execute(get);
			return new HttpInfo(get, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpInfo();
		}
	}
}
