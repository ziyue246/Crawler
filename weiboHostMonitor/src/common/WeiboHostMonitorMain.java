package common;

import org.apache.commons.configuration.SystemConfiguration;

import common.extractor.Extractor;
import common.sql.ConnectSql;
import http.SinaHttpProcess;

public class WeiboHostMonitorMain {
	public static void main(String[] args) throws Exception {
		//testLogin();
		weiboHostMonitor();
	}
	public static void testLogin() throws Exception {

		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		sinaHttp.login();
		System.out.println(sinaHttp.getLoginCookie());
		// while(sinaHttp.verify()){
		// System.out.println("登陆成功！！！");
		// SystemCommon.sleeps(10);
		// }
	}
	public static void weiboHostMonitor() {
		
		while (true) {
			try {
				//世界之窗
				//SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7";
				
				
				//SystemCommon.currentCookie = "SINAGLOBAL=6337581237312.406.1473662416800; wvr=6; YF-Ugrow-G0=3a02f95fa8b3c9dc73c74bc9f2ca4fc6; SCF=AhA1J9b7L4jXjj0oxS793lYQMxAoSvaXU4XP_OAW9h7SbOWPvW7qhjgoPtLtelQBbSrB0meu6owqjo8nchq9XR0.; SUB=_2A256-plFDeTxGeNH4lIW8irNzTqIHXVWcY2NrDV8PUNbmtANLXPMkW-I8B5AFEZxoakV4DegS8Fc66LQKQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFsHB.-W9R_dWd-5d491v8C5JpX5KMhUgL.Fo-41K5NeoBpSoq2dJLoIX7LxKqL1h5L1K-LxKnL1h5L1h2LxK-LBK.L1hqLxKML1-2L1hBLxKnLBo2L1hqLxKnL122LB.BLxK-LBozL1h2LxKqL1-eL1hnp1K.4; SUHB=0RyqF0vZ8V3t1p; ALF=1507859604; SSOLoginState=1476323605; YF-V5-G0=b1e3c8e8ad37eca95b65a6759b3fc219; _s_tentry=login.sina.com.cn; Apache=7621232685633.004.1476323615101; ULV=1476323615384:4:2:2:7621232685633.004.1476323615101:1476235126970; YF-Page-G0=dc8d8d4964cd93a7c3bfa7640c1bd10c; UOR=,,login.sina.com.cn";
				//
				
				SystemCommon.getCookie();
				
				ConnectSql.excuteOracle(null, "selectHostUrl");
				System.out.println("获取的博主数量："+SystemCommon.WeiboDataProcessedSet.size());
				if(SystemCommon.WeiboDataProcessedSet.size()==0){
					SystemCommon.sleeps(2*60*60);
				}
				for (WeiboDataProcessed weibodata : SystemCommon.WeiboDataProcessedSet) {
					String url = Extractor.getHostUrl(weibodata);
					SystemCommon.sleeps(10);
					try {
						Bloggers blog = Extractor.getBloggerinfo(url, weibodata);
						ConnectSql.insertBloggerMessage(blog);
						SystemCommon.printBlog(blog);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					SystemCommon.sleeps(20);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}