package common.down;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import common.bean.HtmlInfo;
import common.bean.WebData;
import common.extractor.XpathExtractor;
import common.http.DownloadData;
import common.http.SimpleHttpClient;
import common.siteinfo.Siteinfo;
import common.system.Systemconfig;

/**
 * 数据下载父类
 * @author grs
 *
 */

public abstract class CommonDown {

	protected static final Map<String, DownloadData> downMap = new HashMap<String, DownloadData>();
	protected static final Map<String, XpathExtractor> xpathMap = new HashMap<String, XpathExtractor>();
	protected WebData data;
	protected CountDownLatch count;
	protected final String siteFlag;
	protected final String key;
	protected final String gloaburl;
	protected SimpleHttpClient http;
	
	protected DownloadData downloadData;
	protected XpathExtractor xpath;
	
	public CommonDown(String siteFlag, WebData vd, CountDownLatch endCount) {
		this.siteFlag = siteFlag;
		this.data = vd;
		this.count = endCount;
		gloaburl = null;
		key = null;
		
		if(downMap.containsKey(siteFlag)) {
			this.downloadData = downMap.get(siteFlag);
		} else {
			createHttpClient(siteFlag);
			this.downloadData = new DownloadData(this.http);
			downMap.put(siteFlag, this.downloadData);
		}
		if(xpathMap.containsKey(siteFlag)) {
			this.xpath = xpathMap.get(siteFlag);
		} else {
			this.xpath = getXpath();
			xpathMap.put(siteFlag, this.xpath);
		}
	}
	public CommonDown(String siteFlag, String key, String url, CountDownLatch count) {
		this.siteFlag = siteFlag;
		this.key = key;
		this.gloaburl = url;
		this.count = count;
		
		createHttpClient(siteFlag);
		
		if(downMap.containsKey(siteFlag)) {
			this.downloadData = downMap.get(siteFlag);
		} else {
			createHttpClient(siteFlag);
			this.downloadData = new DownloadData(this.http);
			downMap.put(siteFlag, this.downloadData);
		}
		if(xpathMap.containsKey(siteFlag)) {
			this.xpath = xpathMap.get(siteFlag);
		} else {
			this.xpath = getXpath();
			xpathMap.put(siteFlag, this.xpath);
		}
	}
	
	public CommonDown(String siteFlag, String url, CountDownLatch count) {
		this.siteFlag = siteFlag;
		this.gloaburl = url;
		this.count = count;
		this.key = null;
		
		if(downMap.containsKey(siteFlag)) {
			this.downloadData = downMap.get(siteFlag);
		} else {
			createHttpClient(siteFlag);
			this.downloadData = new DownloadData(this.http);
			downMap.put(siteFlag, this.downloadData);
		}
		if(xpathMap.containsKey(siteFlag)) {
			this.xpath = xpathMap.get(siteFlag);
		} else {
			this.xpath = getXpath();
			xpathMap.put(siteFlag, this.xpath);
		}
	}
	/**
	 * 对入口URL做处理
	 * @return
	 */
	protected String getRealUrl(Siteinfo siteinfo, String url) {
		if(url==null)
			return siteinfo.getUrl();
		else 
			return url;
	}
	
	/**
	 * 对入口URL做处理
	 * @return
	 */
	protected String getRealUrl(WebData vd) {
		return vd.getUrl();
	}
	
	protected int getRealPage(Siteinfo siteinfo) {
		return siteinfo.getPage();
	}
	
	protected HtmlInfo htmlInfo(String type) {
		Siteinfo siteinfo = Systemconfig.allSiteinfos.get(siteFlag);
		HtmlInfo html = new HtmlInfo();
		html.setEncode(siteinfo.getCharset());
		html.setSite(siteFlag);
		html.setAgent(siteinfo.getAgent());
		html.setType(type+File.separator+siteFlag);
		return html;
	}
	
	protected XpathExtractor getXpath() {
		int in = siteFlag.indexOf("_");
		if(in==-1) in = siteFlag.length();
		String site = siteFlag.substring(0, in);
		XpathExtractor be = null;
		try {
			be = (XpathExtractor) Class.forName(Systemconfig.siteExtractClass.get(site)).newInstance();
		} catch (ClassNotFoundException e) {
			//"未找到注册类，更新后处理"
		} catch (InstantiationException e) {
			//注册类实例化异常
		} catch (IllegalAccessException e) {
			//非法访问注册类
		} catch (Exception e) {
		}
		if(be==null)
			be = new XpathExtractor();
		return be;
	}
	
	private void createHttpClient(String siteFlag) {
		try {
			http = (SimpleHttpClient) Class.forName(Systemconfig.siteHttpClass.get(siteFlag)).newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		} catch (Exception e) {
		}
		if(http==null) {
			http = new SimpleHttpClient();
		}
	}
	
}
