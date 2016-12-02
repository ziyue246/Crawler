package common.down;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import common.bean.WebData;
import common.system.Systemconfig;
import common.util.TimeUtil;

/**
 * 详细数据控制线程
 * @author grs
 *
 */
public class DataThreadControl {
	protected final String siteFlag;
	protected final String unique;
	public DataThreadControl(String siteFlag, String unique) {
		this.siteFlag = siteFlag;
		this.unique = unique;
	}
	/**
	 * 处理的第一种方式，列表数据采集一部分就开始采集内容页
	 * @param list
	 * @param interval
	 */
	public void process(List<WebData> list, int interval) {
		CountDownLatch endCount = new CountDownLatch(list.size());
		Iterator<WebData> iter = list.iterator();
		while(iter.hasNext()) {
			WebData vd = iter.next();
			synchronized (list) {
				iter.remove();
			}
			Systemconfig.dataexec.get(siteFlag).execute(DownFactory.dataControl(siteFlag, vd, endCount));
			TimeUtil.rest(interval);
		}
		try {
			endCount.await(2, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		Systemconfig.sysLog.log(siteFlag+"关键词:"+unique+"内容数据采集完成！");
		Systemconfig.finish.put(siteFlag+unique, true);
	}
	
	private static final HashMap<String, List<WebData>> map = new HashMap<String, List<WebData>>();
	/**
	 * 处理的第二种方式，站点所有列表数据都采集完成后再采集内容页
	 * @param list
	 * @param interval
	 * @param count
	 */
	public void process(List<WebData> list, int interval, long count) {
		if(count > 1) {
			synchronized (map) {
				if(map.containsKey(siteFlag)) {
					map.get(siteFlag).addAll(list);
					map.put(siteFlag, map.get(siteFlag));
				} else
					map.put(siteFlag, list);
			}
			return;
		}
		List<WebData> temp = null;
		synchronized (map) {
			if(map.containsKey(siteFlag)) {
				map.get(siteFlag).addAll(list);
				map.put(siteFlag, map.get(siteFlag));
			} else
				map.put(siteFlag, list);
			temp = map.get(siteFlag);
			
			map.remove(siteFlag);
		}
		CountDownLatch endCount = new CountDownLatch(temp.size());
		Iterator<WebData> iter = list.iterator();
		while(iter.hasNext()) {
			WebData vd = iter.next();
			synchronized (list) {
				iter.remove();
			}
			Systemconfig.dataexec.get(siteFlag).execute(DownFactory.dataControl(siteFlag, vd, endCount));
			TimeUtil.rest(interval);
		}
		try {
			endCount.await(2, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		Systemconfig.sysLog.log(siteFlag+"关键词:"+unique+"内容数据采集完成！");
		Systemconfig.finish.put(siteFlag+unique, true);
	}

}
