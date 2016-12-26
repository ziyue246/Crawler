package http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.util.log.Log;

import common.StringUtil;
import common.SystemCommon;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rzy 2016/12/19
 *
 */
public class Httpieee{

    private String sid;

    private String qid;
    private String parentQid; // IEEE Transactions on Visualization and Computer
    // Graphics
    // SID="Y1GphMPgZ6ndeFuoKi2"; CUSTOMER="CAS Institute of Automation"; E_GROUP_NAME="CAS Institute of Automation"; JSESSIONID=E0A744C25285742170E94269D27D02FF
    //private final String modelCookie = "SID=\"<sid>\";CUSTOMER=\"CAS Institute of Automation\";E_GROUP_NAME=\"CAS Institute of Automation\";";
    String Content_Type = "application/x-www-form-urlencoded;text/html;charset=UTF-8";
    Map<String,String> recordKeysMap;
    private List<Integer> selectedIdList;
    private int printCount;
    public Map<String, String> getRecordKeysMap() {
        return recordKeysMap;
    }

    public void setRecordKeysMap(Map<String, String> recordKeysMap) {
        this.recordKeysMap = recordKeysMap;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public int getPrintCount() {
        return printCount;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    

    public String getParentQid() {
        return parentQid;
    }

    public void setParentQid(String parentQid) {
        this.parentQid = parentQid;
    }

    public List<Integer> getSelectedIdList() {
        return selectedIdList;
    }

    public void setSelectedIdList(List<Integer> selectedIdList) {
        this.selectedIdList = selectedIdList;
    }


    public void getCookie(HtmlInfo html) {
    	HttpClient client = HttpClients.createDefault();
    	String loginUrl = "http://ieeexplore.ieee.org/search/advsearch.jsp"; 
    	html.setRealUrl(loginUrl);
        HttpGet get = new HttpGet(html.getRealUrl());
        get.setHeader("Host", "ieeexplore.ieee.org");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("User-Agent", html.getUa());
        get.setHeader("Accept","text/html, application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        get.setHeader("Upgrade-Insecure-Requests", "1");
       
        HttpResponse response = null;

        try {
			response = client.execute(get);
			SystemCommon.printLog("获取Cookie get status:"+response.getStatusLine());
	        SystemCommon.printHeaders(response.getAllHeaders());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        if (response.containsHeader("Set-Cookie")) {
            String cookie = "";
            for (Header co : response.getHeaders("Set-Cookie")) {
                cookie += co.getValue().split(";")[0] + ";";
            }
            html.setCookie(cookie);
        }
        if(response.containsHeader("Location")){
	        try {
	            String locationUrl = response.getFirstHeader("Location").getValue();
	            html.setRealUrl(locationUrl);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }
    }

    public String simplePost(HtmlInfo html, Map<String, String> params, String mark) {
        HttpClient client = HttpClients.createDefault();

        if (html.getCookie() == null || sid == null)
            getCookie(html);

        HttpPost post = new HttpPost(html.getRealUrl());
        if (mark.equals("01")) {
            params.clear();
            String Referer = "http://apps.webofknowledge.com/WOS_CitedReferenceSearch_input.do?" +
                    "locale=en_US&errorKey=&viewType=input&" +
                    "SID="+sid+"&product=WOS&search_mode=CitedReferenceSearch";
            post.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            post.setHeader("Cache-Control", "max-age=0");
            post.setHeader("Connection", "keep-alive");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("Host", "apps.webofknowledge.com");
            post.setHeader("Origin","http://apps.webofknowledge.com");
            post.setHeader("Upgrade-Insecure-Requests","1");
            post.setHeader("Connection", "keep");
            post.setHeader("Referer",html.getReferUrl());
            getParamsMap01(params);
            SystemCommon.printLog(" 01 post");
            SystemCommon.printLog(html.getCookie());
        } else if (mark.equals("02")) {
            params.clear();
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            getParamsMap02(params);
            SystemCommon.printLog("  02  post:");
            SystemCommon.printLog(html.getCookie());
        } else if (mark.equals("03")) {//
            params.clear();
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            getParamsMap03(params);
            SystemCommon.printLog("  03  post");
            SystemCommon.printLog(html.getCookie());
        } 

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            list.add(new BasicNameValuePair(key, params.get(key)));
        }

        post.setHeader("User-Agent", html.getUa());
        post.setHeader("Cookie", html.getCookie());
        //post.setHeader("Referer", html.getReferUrl());
        try {
            post.setEntity(new UrlEncodedFormEntity(list));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        HttpResponse response = null;

        try {
            response = client.execute(post);
            SystemCommon.printLog("Post 鐘舵?佺爜锛?"+response.getStatusLine());

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            post.abort();
        }
        String geturl = null;
        try {
            geturl = response.getFirstHeader("Location").getValue();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        if (mark.equals("04"))
        {
            try {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    String content = EntityUtils.toString(httpEntity, "utf-8");
                    html.setContent(content);
                    //System.out.println(mark+"\t:\t"+EntityUtils.toString(httpEntity,"utf-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (response.containsHeader("Set-Cookie")) {
            String cookie = "";
            for (Header co : response.getHeaders("Set-Cookie")) {
                cookie += co.getValue().split(";")[0] + ";";
            }
            
            html.setCookie(cookie);
        }
        if (geturl == null)
            return null;
        html.setRealUrl(geturl);

        return geturl;
    }
    /** 
     * 发送HttpPost请求 
     *  
     * @param strURL 
     *            服务地址 
     * @param params 
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/> 
     * @return 成功:返回json字符串<br/> 
     */ 
    public void postJson(HtmlInfo html, String params) {
 
        try {  
            URL url = new URL(html.getRealUrl());// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json, text/plain, */*"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 设置发送数据的格式 
            connection.setRequestProperty("Origin",	"http://ieeexplore.ieee.org"); 
            connection.setRequestProperty("User-Agent", html.getUa()); 
            //String cookie = "ipCheck=2001:cc0:2016:f177:e8a4:9675:ebcd:fa44; ERIGHTS=uAqrn8HZUQJxxF6c1hmx2BjarCx2B7i2NMkyg*Xx2B0g9x2BMZ2yphGl4zKIRqP7eL12blJaBi5Fx2B3Bx2FzEOFJuorqx2BQ1x2BkeQx3Dx3D-18x2dqVwxxgC4WsmNTPTpUU0DGKQx3Dx3DunpZKbSK4EdLwaD3MhnbFAx3Dx3D-EaKd2XR2eRsi32aYaJQIvwx3Dx3D-VAwWmHx2BMhF6cTr1AHXl8VQx3Dx3D; JSESSIONID=yLzQYYSL6hPxGp3q9QT6hpvQGfrdF6vjpgnYV36V5YSv1yTFypLj!1761083077; seqId=25481; xploreCookies=eyJjb250YWN0RW1haWwzIjoiTkEiLCJpc0RlbGVnYXRlZEFkbWluIjoiZmFsc2UiLCJjb250YWN0UGhvbmUiOiJOQSIsImlzQ29udGFjdFdlYkFkZHJFbmFibGVkIjoiZmFsc2UiLCJzdGFuZGFyZHNMaWNlbnNlSWQiOiIwIiwib3BlblVybEltZ0xvYyI6Ik5BIiwiY29udGFjdEVtYWlsMiI6Ik5BIiwiaXNEZXNrdG9wVXNlciI6ImZhbHNlIiwiaXNDb250YWN0TmFtZUVuYWJsZWQiOiJmYWxzZSIsImluc3RUeXBlIjoiRyIsImlzQ29udGFjdEZheEVuYWJsZWQiOiJmYWxzZSIsImlzUHJvdmlzaW9uZWQiOiJmYWxzZSIsIm9wZW5VcmwiOiJOQSIsImN1c3RvbWVyU3VydmV5IjoiTkEiLCJ1c2VySWRzIjoiMjU0ODEiLCJpbnN0SW1hZ2UiOiIiLCJpc0luc3QiOiJ0cnVlIiwib3BlblVybFR4dCI6Ik5BIiwiaXNSb2FtaW5nRW5hYmxlZCI6InRydWUiLCJjb250YWN0V2ViQWRkciI6Ik5BIiwiY29udGFjdEZheCI6Ik5BIiwic21hbGxCdXNpbmVzc0xpY2Vuc2VJZCI6IjAiLCJpc0NoYXJnZWJhY2tVc2VyIjoiZmFsc2UiLCJwcm9kdWN0cyI6IklCTXxJRUx8VkRFfEFMQ0FURUwtTFVDRU5UfCIsImNvbnRhY3ROYW1lIjoibmluZyBsdSIsImlzQ29udGFjdFBob25lRW5hYmxlZCI6ImZhbHNlIiwiaXNNZW1iZXIiOiJmYWxzZSIsImlzQ29udGFjdEVtYWlsRW5hYmxlZCI6ImZhbHNlIiwib2xkU2Vzc2lvbktleSI6InVBcXJuOEhaVVFKeHhGNmMxaG14MkJqYXJDeDJCN2kyTk1reWcqWHgyQjBnOXgyQk1aMnlwaEdsNHpLSVJxUDdlTDEyYmxKYUJpNUZ4MkIzQngyRnpFT0ZKdW9ycXgyQlExeDJCa2VReDNEeDNELTE4eDJkcVZ3eHhnQzRXc21OVFBUcFVVMERHS1F4M0R4M0R1bnBaS2JTSzRFZEx3YUQzTWhuYkZBeDNEeDNELUVhS2QyWFIyZVJzaTMyYVlhSlFJdnd4M0R4M0QtVkF3V21IeDJCTWhGNmNUcjFBSFhsOFZReDNEeDNEIiwiY29udGFjdEVtYWlsIjoibmluZy5sdUBpYS5hYy5jbiIsImlzQ2l0ZWRCeUVuYWJsZWQiOiJ0cnVlIiwiZW50ZXJwcmlzZUxpY2Vuc2VJZCI6IjAiLCJpc0lwIjoidHJ1ZSIsImluc3ROYW1lIjoiSU5TVElUVVRFIE9GIEFVVE9NQVRJT04gQ0FTIn0=; cookieCheck=true; unicaID=Dfak3VfN4VJ-aF4HYfM; cp_anonymousID=2a96d3db13f6d3b83b481037f40142a22001:cc0:2016:f177:e8a4:9675:ebcd:fa44; cmTPSet=Y; CoreID6=14120528956514822160319&ci=52820000; utag_main=v_id:01591af5b4ae003253e50a77c98e0506c003b06400918$_sn:1$_ss:0$_st:1482218029052$ses_id:1482216027319%3Bexp-session$_pn:1%3Bexp-session; cp_user_institution_id_unique=e32ed669a79fea8e23516b9386994395; 52820000_clogin=v=1&l=1482216031&e=1482218030676; WLSESSION=vi26200104c00080340000000000000053.20480; visitstart=14:43";
            connection.setRequestProperty("Cookie", html.getCookie()); 
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), html.getEncode()); 

            out.append(params);
            out.flush();
            out.close();
            
            SystemCommon.printLog("PostJson　:　"+connection.getResponseCode());
            
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			
			StringBuffer sbf = new StringBuffer();

			for(String lines = reader.readLine();lines!=null;lines = reader.readLine()){
			    sbf.append(new String(lines.getBytes(), html.getEncode()));
			}
			html.setContent(sbf.toString());
			reader.close();
			connection.disconnect();
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
   
    public String simplePost(HtmlInfo html, String params) {
        HttpClient client = HttpClients.createDefault();

        if (html.getCookie() == null || sid == null)
            getCookie(html);

        HttpPost post = new HttpPost(html.getRealUrl());
       
        post.setHeader("User-Agent", html.getUa());
        post.setHeader("Cookie", html.getCookie());
        
        //post.setHeader("Referer", html.getReferUrl());

        HttpResponse response = null;

        try {
            response = client.execute(post);
            SystemCommon.printLog("Post 鐘舵?佺爜锛?"+response.getStatusLine());

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            post.abort();
        }
        String geturl = null;
        try {
            geturl = response.getFirstHeader("Location").getValue();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        if (response.containsHeader("Set-Cookie")) {
            String cookie = "";
            for (Header co : response.getHeaders("Set-Cookie")) {
                cookie += co.getValue().split(";")[0] + ";";
            }
            
            html.setCookie(cookie);
        }
        if (geturl == null)
            return null;
        html.setRealUrl(geturl);

        return geturl;
    }
    public String simpleGet(HtmlInfo html) {
        HttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(html.getRealUrl());
        html.setContent(null);
        get.setHeader("User-Agent", html.getUa());
        get.setHeader("Cookie", html.getCookie());
        //get.setHeader("Host", "apps.webofknowledge.com");
        //get.setHeader("Connection", "keep-alive");
        // get.setHeader("Content-Type", Content_Type);
        // get.setHeader("Referer", html.getReferUrl());
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
        return response.toString();
    }


    
    public void getParamsMap03(Map<String, String> params) {

        params.put("product", "WOS");
        params.put("SID", sid);
        params.put("selectedIds", "");
        params.put("TitleFormat", "");
        params.put("parentQid", parentQid);
        params.put("input_invalid_notice", "Search Error: Please enter a search term.");
        params.put("input_invalid_notice_limits", " <br/>Note: Fields displayed in scrolling boxes must be combined with at least one other search field.");
        params.put("update_back2search_link_param", "yes");
        params.put("selectedRecs", "0");// 涓婃閫夋嫨鐨勪釜鏁?
        params.put("page", "1");
        params.put("search_mode", "CitedRefIndex");
        params.put("formValue(summary_mode)", "CitedReferenceSearch");
        params.put("citedRefStep2MaxChecks", "You are attempting to select more than the  records allowed. Only the first  records have been selected.");
        params.put("citedRefStep2FinishLoadingCheck", "Please select the checkbox after the page finished the loading.");
        params.put("postFormSubmitUrl",
                //http://apps.webofknowledge.com/summary.do?product=WOS&parentQid=24&SID=Y1GphMPgZ6ndeFuoKi2&search_mode=CitedReferenceSearch&formValue(summary_mode)=CitedReferenceSearch&page=1
                "http://apps.webofknowledge.com/summary.do?product=WOS&parentQid=" + parentQid + "&SID=" + sid
                        + "&search_mode=CitedReferenceSearch&formValue(summary_mode)=CitedReferenceSearch&page=1");
        params.put("max_selected_cited_recs.top", "500");
        params.put("selected_cited_recs.top", "0");// 涓婃閫夋嫨鐨勪釜鏁?
        params.put("clearall_url.top", "http://apps.webofknowledge.com/summary.do?ClearCheckboxes=all&cacheurl=no");
        params.put("fastLaneUpdateSelection_url.top",
                "http://apps.webofknowledge.com/fastLaneUpdateSelection.do?cacheurl=no");
        params.put("Finish Search.x", "443");
        params.put("Finish Search.y", "446");
        params.put("all_summary_IDs", "");
//		params.put("isickref", "1");// 瀵瑰簲閫夋嫨鐨勬爣鍙?
//		params.put("isickref", "2");
//		params.put("isickref", "3");
        for (Integer i : selectedIdList) {
            if ((i-1)%50 < 10) {
                params.put("isickref", (i) + "");
            }
        }
        params.put("all_summary_IDs", "");

        params.put("viewAbstractUrl", "/RefViewAbstract.do?product={0}&colName={0}&SID=" + sid
                + "&search_mode=CitedFullRecord&viewType=ViewAbstract&");
        params.put("LinksAreAllowedRightClick", "/CitedFullRecord.do");
        params.put("LinksAreAllowedRightClick", "/CitedRefList.do");
        params.put("LinksAreAllowedRightClick", "/InterService.do");
//		params.put("isickref", "11");// 瀵瑰簲閫夋嫨鐨勬爣鍙?
//		params.put("isickref", "12");
//		params.put("isickref", "13");

        for (Integer i : selectedIdList) {
            if ((i-1)%50 >= 10) {
                params.put("isickref", (i) + "");
            }
        }
        params.put("all_summary_IDs", "");
        params.put("viewAbstractUrl", "/RefViewAbstract.do?product={0}&colName={0}&SID=" + sid
                + "&search_mode=CitedFullRecord&viewType=ViewAbstract&");
        params.put("LinksAreAllowedRightClick", "/CitedFullRecord.do");
        params.put("LinksAreAllowedRightClick", "/CitedRefList.do");
        params.put("LinksAreAllowedRightClick", "/InterService.do");
        params.put("selectedrefpg", "1");
        params.put("max_selected_cited_recs.bottom", "500");
        params.put("selected_cited_recs.bottom", selectedIdList.size()+"");// 鎬绘暟
        params.put("clearall_url.bottom", "http://apps.webofknowledge.com/summary.do?ClearCheckboxes=all&cacheurl=no");
        params.put("fastLaneUpdateSelection_url.bottom",
                "http://apps.webofknowledge.com/fastLaneUpdateSelection.do?cacheurl=no");
        params.put("fieldCount", "13");
        params.put("value(bool_1_2)", "AND");
        params.put("value(bool_2_3)", "AND");
        params.put("value(bool_3_4)", "AND");
        params.put("value(bool_4_5)", "AND");
        params.put("value(bool_5_6)", "AND");
        params.put("value(bool_6_7)", "AND");
        params.put("value(bool_7_8)", "AND");
        params.put("value(bool_8_9)", "AND");
        params.put("value(bool_9_10)", "AND");
        params.put("value(bool_10_11)", "AND");
        params.put("value(bool_11_12)", "AND");
        params.put("value(bool_12_13)", "AND");
        params.put("value(bool_13_14)", "AND");
        params.put("value(select2)", "LA");
        params.put("value(input2)", "");
        params.put("value(select3)", "DT");
        params.put("value(input3)", "");
        params.put("value(limitCount)", "15");
        params.put("docNumForVA", "");
    }


    public void getParamsMap01(Map<String, String> params) {
      
    }

    public void getParamsMap02(Map<String, String> params) {
      
    }

    public void extractRecordKeys2Map(String content) {
                            //window.recordKeysMapping['3'] = '485337821';
        String pattern = "window\\.recordKeysMapping\\[\\'\\d{1,3}\\'\\]\\s+=\\s+\\'\\d{7,11}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        if(recordKeysMap==null)recordKeysMap = new HashMap<String,String>();
        while (m.find()) {
            String s=m.group();
            String key = s.split("\\[\\'")[1].split("\\'\\]")[0];
            String value = s.split("= \\'")[1].split("\\'")[0];
            recordKeysMap.put(key,value);
        }
    }
    
    private String inputStreamToString(InputStream is) {

        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        // Return full string
        return total.toString();
    }
}
