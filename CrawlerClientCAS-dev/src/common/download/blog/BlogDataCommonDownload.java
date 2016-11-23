package common.download.blog;

import java.util.concurrent.CountDownLatch;

import common.bean.BlogData;
import common.bean.HtmlInfo;
import common.download.GenericDataCommonDownload;
import common.rmi.packet.SearchKey;
import common.system.Systemconfig;

/**
 * 下载详细页面
 * 
 * @author grs
 */
public class BlogDataCommonDownload extends GenericDataCommonDownload<BlogData> {

	public BlogDataCommonDownload(String siteFlag, BlogData vd, CountDownLatch endCount, SearchKey key) {
		super(siteFlag, vd, endCount, key);
	}

	public void process() {
		String url = getRealUrl(data);
		if (url == null)
			return;
		HtmlInfo html = htmlInfo("DATA");
		try {
			if (url != null && !url.equals("")) {
				html.setOrignUrl(url);
				html.setAgent(false);
				http.getContent(html);
				// html.setContent();
				if (html.getContent() == null) {
					return;
				}
				// 解析数据
				xpath.templateContentPage(data, html);
				
				BlogData blogData =data;
				System.out.println("\n\n一条博客信息：");	
				System.out.println("SearchKey   :"+blogData.getSearchKey());
				System.out.println("Title       :"+blogData.getTitle());
				System.out.println("Brief       :"+blogData.getBrief());
				System.out.println("Pubtime     :"+blogData.getPubtime());
				System.out.println("Pubdate     :"+blogData.getPubdate().toLocaleString());
				System.out.println("Author      :"+blogData.getBlogAuthor());
				System.out.println("Md5         :"+blogData.getMd5());
				System.out.println("ImgUrl      :"+blogData.getImgUrl());
				System.out.println("Url         :"+blogData.getUrl());
				System.out.println("Content     :"+blogData.getContent());
				System.out.println("\n\n");
				
				
				Systemconfig.sysLog.log(data.getTitle() + "解析完成。。。");
				//Systemconfig.dbService.saveData(data);
				synchronized (key) {
					key.savedCountIncrease();
				}
				Systemconfig.sysLog.log(data.getTitle() + "保存完成。。。");
			}
		} catch (Exception e) {
			Systemconfig.sysLog.log("采集出现异常" + url, e);
		} finally {
			if (count != null)
				count.countDown();
		}
	}

}
