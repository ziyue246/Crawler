package common;

import java.util.Set;

import http.SinaHttpProcess;

public class WeiboHotSearchMain {
	public static void main(String[] args) throws Exception {
		//testLogin();
		weiboHotSearch();
	}
	public static void testLogin() throws Exception {

		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		sinaHttp.login();
		System.out.println(sinaHttp.getLoginCookie());
		// while(sinaHttp.verify()){
		// System.out.println("��½�ɹ�������");
		// SystemCommon.sleeps(10);
		// }
	}
	public static void weiboHotSearch() {
		final String url = "http://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
		while (true) {
			try {
				
				try {
					Set<HotSearch> hotSearchSet  = Extractor.getHotSearchInfo(url);
					for (HotSearch hotSearch : hotSearchSet) {
						SystemCommon.printHotSearch(hotSearch);
						ConnectSql.insertHotSearchMessage(hotSearch);
					}
					System.out.println("���β����������� :"+hotSearchSet.size());
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			SystemCommon.sleeps(60*30);
		}
	}
}