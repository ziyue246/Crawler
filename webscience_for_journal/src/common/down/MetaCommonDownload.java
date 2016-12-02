package common.down;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import common.bean.HtmlInfo;
import common.bean.WebData;
import common.siteinfo.CollectDataType;
import common.siteinfo.Siteinfo;
import common.system.Systemconfig;
import common.util.TimeUtil;

/**
 * 下载元数据
 * 
 * @author grs
 */
public class MetaCommonDownload extends CommonDown implements Runnable {

	protected Map<String, Integer> map = Collections
			.synchronizedMap(new HashMap<String, Integer>());

	public MetaCommonDownload(String siteFlag, String key, String url,
			CountDownLatch count) {
		super(siteFlag, key, url, count);
	}

	@Override
	public void run() {
		List<WebData> alllist = new ArrayList<WebData>();
		List<WebData> list = new ArrayList<WebData>();
		Siteinfo siteinfo = Systemconfig.allSiteinfos.get(siteFlag);
		
		
		
		
		
		String url = getRealUrl(siteinfo, gloaburl);
		
		
		
		
		
		int page = getRealPage(siteinfo);
		map.put(key, 1);
		String nexturl = url;
		DataThreadControl dtc = new DataThreadControl(siteFlag, key);
		HtmlInfo html = htmlInfo("META");
		try {
			while (nexturl != null && !nexturl.equals("")) {
				list.clear();

				html.setOrignUrl(nexturl);

				System.out.println("########################");
				
				System.out.println("1:\t" + html.getOrignUrl());
				System.out.println("2:\t" + html.getRealUrl());
				System.out.println("3:\t" + html.getSite());

				System.out.println("########################");
				
				try {
					downloadData.getContent(html);
					// html.setContent(common.util.StringUtil.getContent("filedown/META/wos/4a31918dadee7ff7dcc5bf0d52b2052d.htm"));

					
					
					
					
					nexturl = xpath.templateList(list, html, map.get(key),
							siteFlag, CollectDataType.META.name(), key,
							nexturl, siteFlag);

					if (list.size() == 0) {
						Systemconfig.sysLog.log(url + "元数据页面解析为空！！");
						break;
					}
					Systemconfig.sysLog.log(url + "元数据页面解析完成。");

					Systemconfig.mysqlService.getNorepeatData(list, "wos_data");

					dtc.process(list, siteinfo.getDownInterval());
					// Systemconfig.swinghtml.append(url).append("列表页面解析完成，有效数据量:").append(list.size());
					alllist.addAll(list);

					map.put(key, map.get(key) + 1);
					if (map.get(key) > page)
						break;
					url = nexturl;
					if (nexturl != null)
						TimeUtil.rest(siteinfo.getDownInterval());

				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			 Systemconfig.sysLog.log("{"+key + "} 全部采集完成.");
			// dtc.process(alllist, siteinfo.getDownInterval());
		} finally {
			list.clear();
			list = null;
			html = null;
			nexturl = null;

			map.clear();
			map = null;

			xpath = null;
			downloadData = null;
			http = null;
			if (count != null)
				count.countDown();
		}
	}

}
