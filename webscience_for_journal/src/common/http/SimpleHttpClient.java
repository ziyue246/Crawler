package common.http;

import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpParams;
import common.bean.HtmlInfo;
import common.system.Systemconfig;
import common.util.UserAgent;

/**
 * 普通http请求处理
 * @author grs
 * @since 2014年1月
 */
public class SimpleHttpClient {

	protected int readTime = 600000;
	protected int connectNum = 10;
	protected int requestTime = 60000;
	protected String userAgent = UserAgent.getUserAgent();
	/**
	 * 创建连接
	 * 
	 * @param module
	 * @return
	 */
	private static final ConcurrentHashMap<String, HttpClient> clientMap = new ConcurrentHashMap<String, HttpClient>();
	
	public HttpClient httpClient(HtmlInfo html) {
		String key = html.getSite();
		if(clientMap.contains(key)) {
			return clientMap.get(key);
		} else {
			HttpParams params = httpParams(html.getAgent());
			
			HttpClient client = new DefaultHttpClient(params);
			
			clientMap.putIfAbsent(key, client);
			return client;
		}
	}

	/**
	 * 普通的get请求
	 * 
	 * @param url
	 * @return
	 */
	public synchronized HttpInfo simpleGet(HtmlInfo html) {
		
		HttpClient hc = httpClient(html);
		HttpGet get = new HttpGet(html.getOrignUrl());
		
		try {
			HttpResponse response = hc.execute(get);
			return new HttpInfo(get, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpInfo();
		}
	}
	
	protected HttpParams httpParams(boolean agent) {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
		params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);		
		params.setParameter(ClientPNames.MAX_REDIRECTS, connectNum);	
		params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, requestTime);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, readTime);
		if(agent) {
			HttpHost proxy = new HttpHost(Systemconfig.agentIp, Systemconfig.agentPort);
			params.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		return params;
	}

}
