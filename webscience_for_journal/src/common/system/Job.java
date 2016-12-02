package common.system;

import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.bean.WebData;
import common.down.DataCommonDownload;
import common.down.MetaThreadControl;

public class Job {
	private static final ExecutorService es = Executors.newFixedThreadPool(Systemconfig.allSiteinfos.size());
	
	public void list(String site, String key) {
		es.execute(new MetaThreadControl(site, key));
	}
	public void page(Entry<String,String> e, String site) {
		WebData vd = new WebData();
		vd.setUrl(e.getKey());
		vd.setTitle(e.getValue());
		es.execute(new DataCommonDownload(site, vd, new CountDownLatch(1)));
	}
	
	
	public void pageSingle(String url, String site) {
		WebData vd = new WebData();
		vd.setUrl(url);
		new DataCommonDownload(site, vd, new CountDownLatch(1)).run();
	}
	
}
