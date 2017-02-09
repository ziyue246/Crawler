package common.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import common.system.SystemCommon;

/**
 *
 * @author rzy 2016/12/26
 *
 */
public class HttpInfo{
	
    public void post(HtmlInfo html, String params) {
        HttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost(html.getRealUrl());
        
        if(html.getUa()!=null)
        	post.setHeader("User-Agent", html.getUa());
        if(html.getCookie()!=null)
        	post.setHeader("Cookie", html.getCookie());
        
        HttpResponse response = null;

        try {
            response = client.execute(post);
            SystemCommon.printLog("Post status"+response.getStatusLine());

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            post.abort();
        }
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            String result = null;
            try {
                result = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            html.setContent(result);
        }
    }
    public void get(HtmlInfo html) {
        HttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(html.getRealUrl());
        html.setContent(null);
        if(html.getUa()!=null){
        	get.setHeader("User-Agent", html.getUa());
        }
        HttpResponse response = null;

        try {
            response = client.execute(get);
            if (response.containsHeader("Set-Cookie")) {
                String cookie = "";
                for (Header co : response.getHeaders("Set-Cookie")) {
                    cookie += co.getValue().split(";")[0] + ";";
                }
                html.setCookie(cookie);
            }
            html.setReferUrl(html.getRealUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            String result = null;
            try {
                result = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            html.setContent(result);
        }
    }
}
