package common.down;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import common.WOSStart;
import common.siteinfo.Siteinfo;
import common.system.Systemconfig;
import common.util.TimeUtil;


/**
 * 数据下载的控制线程
 * @author grs
 * 
 */
public class MetaThreadControl implements Runnable {
	
	protected final String siteFlag;
	protected final String keyword;
	
	public MetaThreadControl(String site, String keyword) {
		this.siteFlag = site;
		this.keyword = keyword;
	}
	@Override
	public void run() {
		Siteinfo si = Systemconfig.allSiteinfos.get(siteFlag);
		Map<String, String> urls = new HashMap<String, String>();
		Systemconfig.finish.put(siteFlag+keyword, false);
		//格式：  auto:2011-2013
		urls.put(keyword, keyword);
		
		CountDownLatch endCount = new CountDownLatch(urls.size());
		
		process(urls, endCount, si.getDownInterval());
		
		try {
			endCount.await(5, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			clear();
		}
		Systemconfig.sysLog.log(si.getSiteName()+"所有数据采集完成！！");
		urls.clear();
		Systemconfig.finish.put(siteFlag+keyword, true);
	}
	
	private void clear() {
		Systemconfig.metaexec.get(siteFlag).shutdownNow();
		Systemconfig.dataexec.get(siteFlag).shutdownNow();
		Systemconfig.metaexec.clear();
		Systemconfig.dataexec.clear();
		WOSStart.create(siteFlag);
		Systemconfig.finish.put(siteFlag, true);
	}
	
	private void process(Map<String, String> urls, CountDownLatch endCount, int downInterval) {
		for(Map.Entry<String, String> m : urls.entrySet()) {
			Systemconfig.metaexec.get(siteFlag).execute(DownFactory.metaControl(siteFlag, m.getKey(), m.getValue(), endCount));
			TimeUtil.rest(downInterval);
		}
	}
	
}
