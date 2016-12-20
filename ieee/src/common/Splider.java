package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class Splider {

	public String decodeUnicode(String str) {
		Charset set = Charset.forName("UTF-16");
		Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
		Matcher m = p.matcher(str);
		int start = 0;
		int start2 = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(start)) {
			start2 = m.start();
			if (start2 > start) {
				String seg = str.substring(start, start2);
				sb.append(seg);
			}
			String code = m.group(1);
			int i = Integer.valueOf(code, 16);
			byte[] bb = new byte[4];
			bb[0] = (byte) ((i >> 8) & 0xFF);
			bb[1] = (byte) (i & 0xFF);
			ByteBuffer b = ByteBuffer.wrap(bb);
			sb.append(String.valueOf(set.decode(b)).trim());
			start = m.end();
		}
		start2 = str.length();
		if (start2 > start) {
			String seg = str.substring(start, start2);
			sb.append(seg);
		}
		return sb.toString();
	}

	public String getHtmlContent(String url, String charset)  {
		final String searchExcept01 = "你的行为有些异常";
		final String searchExcept02 = "看不清，换一张";
		HttpClient client = new HttpClient();
		
		GetMethod getMethod = new GetMethod(url);
		//String cookie = "SINAGLOBAL=9283362622372.807.1472706398371; un=13582688545; wvr=6; TC-Page-G0=4c4b51307dd4a2e262171871fe64f295; SCF=AhJy3b4rgQhnHF-HCAdDQmxuNLGdGPXe6Fh89BnNIXCDaGPdR4ZUuEyANPhXW1VYF4rGmvEPfPO_57mUswY-KmU.; SUB=_2A2564Pr5DeTxGeNH7FEZ9yvOyT2IHXVZlGsxrDV8PUNbmtANLXGhkW9tE7cBcTs7y7WvkCg1Ketku3gNMg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWeGpHGUYqImqyd9yXpEySQ5JpX5KMhUgL.Fo-4S0eRS0-Eeo22dJLoI0qLxKqL1h-L1K-LxK-LB--LBo.LxK-L1K2L1hqLxK-L1KqL1hBLxK-L1-zLBKzLxKML1-zLB--t; SUHB=0jB4qJL3yLgqum; ALF=1506131497; SSOLoginState=1474595497; YF-V5-G0=a5a264208a5b5a42590274f52e6c7304; YF-Ugrow-G0=ea90f703b7694b74b62d38420b5273df; _s_tentry=login.sina.com.cn; Apache=9566982616670.43.1474595501713; ULV=1474595501775:7:7:4:9566982616670.43.1474595501713:1474335714920; UOR=www.vegnet.com.cn,widget.weibo.com,login.sina.com.cn; YF-Page-G0=27b9c6f0942dad1bd65a7d61efdfa013";
		
		String User_Agent = SystemCommon.User_Agent;
				//"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";
		//getMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		getMethod.setRequestHeader("Cookie", SystemCommon.currentCookie);
		getMethod.setRequestHeader("Accept", "*/*");
		//Content-Type:text/html; charset=GBK
		getMethod.setRequestHeader("Content-Type", "text/html; charset=GBK");
		getMethod.setRequestHeader("Connection", "keep-alive");
		getMethod.setRequestHeader("User-Agent", User_Agent);
		//NameValuePair[] data = { new NameValuePair("keyword", searchExcept) };
		//getMethod.setQueryString(data);
		StringBuffer sb = new StringBuffer();
		try {
			int statusCode = client.executeMethod(getMethod);
			//System.out.println("statusCode:" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + getMethod.getStatusLine());
				return null;
			}

			//Header[] responseHeader = getMethod.getResponseHeaders();

			//for (Header header : responseHeader) {
			//		System.out.println(header.getName()+":"+header.getValue());
			//}
			if(charset==null){
				charset = "utf-8";
			}
			BufferedReader br = new BufferedReader(// utf-8
					new InputStreamReader(getMethod.getResponseBodyAsStream(), 
							charset));//utf-8   gb2312
			
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				line = decodeUnicode(line);
				sb.append(line+"\n");
										
				if (line.indexOf(searchExcept01) != -1) {
					System.out.println(line+"\t"+"\t"+line.indexOf(searchExcept01)+
				              "\t"+line.indexOf(searchExcept02));
					return null;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(sb.toString());
		}
		return sb.toString();
	}

}