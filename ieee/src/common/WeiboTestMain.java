package common;

import http.SinaHttpProcess;

public class WeiboTestMain {
	public static void main(String[] args) throws Exception {
		System.out.println(args[0]+":"+args[1]);
		testLogin(args[0],args[1]);
		//weiboHostMonitor();
	}
	public static void testLogin(String account,String password) throws Exception {

		SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		
		
		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		sinaHttp.setUserName(account);
		sinaHttp.setPassWord(password);
		//13582688545----pp9999   //微博博主信息爬取
		//13513182624----pp9999   //微博评论爬取
		
		//微博博主信息爬取		//17056545489----qw111222       登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败        隔了一天登陆成功   
		//微博评论爬取		//13569438457----qw111222       登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败        隔了一天登陆成功 
		
		//18818395967----qw111222       登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败        隔了一天登陆成功 
		//15986824314----qw444555       登陆失败	扫二维码后登陆失败        通过转发了8条微博，登陆成功   
		//18240451867----qw444555       登陆失败	扫二维码后登陆失败        通过转发了2条微博，登陆成功
		//18906632549----qw444555       登陆失败	扫二维码后登陆失败        通过转发了4条微博，登陆成功
		//13113732458----qw444555       登陆失败	扫二维码后登陆失败       通过转发了8条微博，登陆成功
		//15130156410----qw444555       登陆失败	扫二维码后登陆成功    
		//18819162901----qw444555       登陆失败	扫二维码后登陆失败        通过转发了8条微博，登陆成功
		//13128024284----qw444555       登陆失败	扫二维码后登陆失败        通过转发了8条微博，登陆成功
		//18819472126----qw444555       登陆失败          充会员过后登陆成功

		//zoo_5@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_6@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_7@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_8@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_9@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_10@sina.com		 登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_11@sina.cn		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_12@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_13@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败
		//zoo_14@sina.com		登陆失败	扫二维码后登陆失败        通过转发微博，依然登陆失败

		System.out.println(sinaHttp.getUserAgent());
		sinaHttp.login();
		System.out.println(sinaHttp.getLoginCookie());
		// while(sinaHttp.verify()){
		// System.out.println("登陆成功！！！");
		// SystemCommon.sleeps(10);
		// }
	}
	public static void weiboHostMonitor() {
		
		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		while (true) {
			try {
				while(!sinaHttp.verify()){
					System.out.println("进行登陆，获取cookie");
					sinaHttp.login();
					SystemCommon.currentCookie = sinaHttp.getLoginCookie();
					System.out.println(SystemCommon.currentCookie);
					
					SystemCommon.sleeps(10);
				}ConnectSql.excuteOracle(null, "selectHostUrl");
				System.out.println("获取的博主数量："+ConnectSql.urlSet.size());
				for (String bloggerUrl : ConnectSql.urlSet) {
					String url = Extractor.getHostUrl(bloggerUrl);
					SystemCommon.sleeps(10);
					try {
						Bloggers blog = Extractor.getBloggerinfo(url, bloggerUrl);
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