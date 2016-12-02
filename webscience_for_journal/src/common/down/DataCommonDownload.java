package common.down;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import common.bean.HtmlInfo;
import common.bean.WebData;
import common.siteinfo.CollectDataType;
import common.system.Systemconfig;

/**
 * 下载详细页面
 * @author grs
 */
public class DataCommonDownload extends CommonDown implements Runnable {

	public DataCommonDownload(String siteFlag, WebData vd,
			CountDownLatch endCount) {
		super(siteFlag, vd, endCount);
	}
	
	@Override
	public void run() {
		String url = getRealUrl(data);
		if(url==null) return;
		HtmlInfo html = htmlInfo("DATA");
		try {
			if (url != null && !url.equals("")) {
				html.setOrignUrl(url);
				html.setRealUrl(data.getMd5());
				
				downloadData.getContent(html);
//				html.setContent(common.util.StringUtil.getContent("filedown/DATA/wos/a44e528ffe54aebb04ab77b1bf05c0a2.htm"));
				if(html.getContent()==null) {
					return;
				}
				//解析数据
				
				xpath.templateSingle(html, siteFlag, data);
				
				System.out.println("\n\n一条信息：");	
				System.out.println("Title       :"+data.getTitle());
				System.out.println("Author      :"+data.getAuthor());
				System.out.println("Brief       :"+data.getBrief());
				System.out.println("Cite_num    :"+data.getCite_num());
				System.out.println("Source      :"+data.getSource());
				System.out.println("Pubyear     :"+data.getPubyear());
				System.out.println("Md5         :"+data.getMd5());
				System.out.println("Url         :"+data.getUrl());
				System.out.println("\n\n");
				
				Systemconfig.sysLog.log(data.getTitle() + "解析完成。。。");
//				Systemconfig.swinghtml.append(url).append("\r\n内容页面下载解析完成！\r\n标题：").append(data.getTitle());
				Systemconfig.mysqlService.saveData(data);
				Systemconfig.sysLog.log(data.getTitle() + "保存完成。。。");
//				Systemconfig.swinginfo.append(data.toString());
			}
		} catch (Exception e) {
			Systemconfig.sysLog.log("采集出现异常"+url, e);
		} finally {
			//回收
			data = null;
			html = null;
			
			xpath = null;
			downloadData = null;
			http = null;
			
			if(count!=null)
				count.countDown();
		}
		
//		some();
	}
	
//	public void some() {
//		java.io.File[] fs = new java.io.File("filedown/DATA/wos").listFiles(new java.io.FileFilter() {
//			@Override
//			public boolean accept(java.io.File name) {
//				return true;
//			}
//		});
//		HtmlInfo html = htmlInfo("");
//		html.setEncode("utf-8");
//		html.setSite("wos");
//		for(java.io.File f : fs) {
//			System.out.println(f.getName());
//			String con = common.util.StringUtil.getContent(f.getAbsolutePath());
//			html.setContent(con);
//			xpath.templateSingle(html, siteFlag, data);
//			Systemconfig.mysqlService.saveData(data);
//			data.setTitle(null);
//			data.setMd5(null);
//		}
//		System.out.println("finish");
//	}
}
