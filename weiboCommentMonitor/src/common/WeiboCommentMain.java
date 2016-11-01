package common;

import common.extractor.Extractor;
import common.extractor.ExtractorFromWeiboDate;
import http.SinaHttpProcess;

public class WeiboCommentMain {
	public static void main(String[] args) throws Exception {
		weiboHostMonitor();
	}

	public static void testLogin() throws Exception {

		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		sinaHttp.login();
		System.out.println(sinaHttp.getLoginCookie());
//		 while(sinaHttp.verify()){
//		 System.out.println("登陆成功！！！");
//		 SystemCommon.sleeps(10);
//		 }
	}

	public static void weiboHostMonitor() {

		//while(true)
		{
			try {
				//SystemCommon.currentCookie = "SINAGLOBAL=159.226.20.135_1473662016.969445; SCF=AkJ0ix_eVveWvQRJbl2yzeXhjNJyqjIjUiPkms-ky2PtvTye0fVJMpvaTMw7YcrEA-9g9UQ0ha-dhYcM8bn-jFg.; sso_info=v02m6alo5qztKWRk5ClkKSIpZCkiKWRk5iljoOApZCTnKWRk5yljpSEpY6DkKWRk5SljpOApY6TgKWRk5SljoSYpZCjoLKMg4C4jIOgpp2WpaSPk5S5jpOItIyDjLCOg6DA; Apache=159.226.20.135_1476236354.548015; SUB=_2A256-oV0DeTxGeNH4lAV8C3MwzSIHXVWcfG8rDV_PUNbm9ANLRHjkW97o5QGVFfcJvkgosa9tZaU0kwllw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWhhi6Rel-5yT1MXaepK20k5NHD95Qf1K.ESh50ehnRWs4Dqcj0i--Xi-iFi-iFi--ciKn7i-2Ni--NiKLWiKnXi--fiK.7iK.7i--fiKysi-zReo571h5R; ALF=1507862692";
				//SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 OPR/39.0.2256.71";
				SinaHttpProcess sinaHttp = new SinaHttpProcess();
				while (!sinaHttp.verify()) {
					System.out.println("进行登陆，获取cookie");
					sinaHttp.login();
					SystemCommon.currentCookie = sinaHttp.getLoginCookie();
					//sinaHttp.setLoginCookie(SystemCommon.currentCookie);
					System.out.println(SystemCommon.currentCookie);
					
					SystemCommon.sleeps(30);
				}
				ConnectSql.excuteOracle(null, "selectUrl");
				System.out.println("获取的博主数量：" + SystemCommon.WeiboDataProcessedSet.size());
				for (WeiboDataProcessed weidbop : SystemCommon.WeiboDataProcessedSet) {
	
					try {
						String nextPage = null;
						do {
							nextPage = Extractor.getBloggerinfo(weidbop);
							SystemCommon.sleeps(20);
						} while (nextPage != null);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}