package common.down;

import java.util.concurrent.CountDownLatch;

import common.bean.WebData;


/**
 * 下载线程的工厂方法
 * 可处理特殊的站点下载
 * @author grs
 *
 */
public class DownFactory {
	/**
	 * 元数据
	 * @param siteFlag
	 * @param type
	 * @return
	 */
	public static MetaCommonDownload metaControl(String siteFlag, String key, String url, CountDownLatch count) {
		return new MetaCommonDownload(siteFlag, key, url, count);
	}
	/**
	 * 详细数据
	 * @param siteFlag
	 * @param type
	 * @return
	 */
	public static DataCommonDownload dataControl(String siteFlag, WebData snd, CountDownLatch endCount) {
		return new DataCommonDownload(siteFlag, snd, endCount);
	}
	
}
