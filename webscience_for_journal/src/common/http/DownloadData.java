package common.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.zip.GZIPInputStream;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import common.bean.HtmlInfo;
import common.down.FileAttr;
import common.http.SimpleHttpClient;
import common.system.Systemconfig;
import common.util.MD5Util;
import common.util.StringUtil;

/**
 * 数据下载类
 * @author grs
 *
 */
public class DownloadData {
	private static Logger log = Logger.getLogger(DownloadData.class);
	private SimpleHttpClient ahc;
	private String[] charsets = new String[]{"utf-8","gbk","gb2312"};
	
	public DownloadData(SimpleHttpClient ahc) {
		this.ahc = ahc;
	}
	
	public void getContent(HtmlInfo html) {
		try {
			byte[] fromURL = byteDataFromURL(html);
			
			
			if (fromURL != null) {
				String con = new String(fromURL, html.getEncode());
				String charset = StringUtil.regMatcher(con, "charset=\"?", "\"");
				if(charset!=null && charset.length()<11 && charset.length()>2) {
					charset = charset.trim().toLowerCase();
					if(!html.getEncode().equals(charset)) {
						for(String s : charsets ) {
							if(charset.contains(s)) {
								html.setEncode(s);
								break;
							}
						}
						con = new String(fromURL, html.getEncode());
						
					}
				}

				html.setContent(con);
				if(Systemconfig.createFile) {
					String md5 = html.getRealUrl()==null?MD5Util.MD5(html.getOrignUrl()):html.getRealUrl();
					FileAttr fa = new FileAttr(html.getType()+File.separator+md5, con, html.getEncode(), ".htm");//默认生成htm文件
					Systemconfig.fcManager.createFile(fa);
				}
				fromURL = null;
			} else 
				log.warn("没有抓取到内容，建议暂停采集！请检查网络链接或URL：" + html.getOrignUrl() + "是否正确。");
		} catch (UnsupportedEncodingException e) {
			log.error("请检查并输入正确字符集！", e);
		}
	}

	/**
	 * 得到请求的url的页面内容
	 */
	public byte[] byteDataFromURL(HtmlInfo html) {
		try {
			byte[] buffer = new byte[1024];
			byte[] byteArray = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int count = -1;
			InputStream responseBodyAsStream;

			HttpInfo info = ahc.simpleGet(html);

			HttpResponse  response = info.getResponse();
			if (response != null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseBodyAsStream = entity.getContent();
				Header contentEncodingHeader = entity.getContentEncoding();  
		        if (contentEncodingHeader != null) {  
		            String contentEncoding = contentEncodingHeader.getValue();  
		            if (contentEncoding.toLowerCase(Locale.US).indexOf("gzip") != -1) {  
		            	responseBodyAsStream = new GZIPInputStream(responseBodyAsStream);  
		            }  
		        }  
		        Header contentType = entity.getContentType();  
		        if (contentType != null) {
		            String type = contentType.getValue();  
		            if (type.contains("charset")) {  
		            	String[] temp = type.split("charset=");
		            	if(type.length()>1) {
		            		type = temp[1].trim();
		            		html.setEncode(type);
		            	}
		            }
		        }  
				while ((count = responseBodyAsStream.read(buffer, 0, buffer.length)) > -1) {
					baos.write(buffer, 0, count);
				}
				buffer = null;
				baos.close();
				responseBodyAsStream.close();
				info.getRequest().abort();
				byteArray = baos.toByteArray();
			}
			return byteArray;
		} catch (IOException e) {
			log.error("下载页面读取出现问题", e);
		}
		return null;
	}

}
