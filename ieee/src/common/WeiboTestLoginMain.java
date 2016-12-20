package common;

import http.SinaHttpProcess;

public class WeiboTestLoginMain {
	public static void main(String[] args) throws Exception {
		if(args.length<2){
			System.out.println("Ã»ÓÐÊäÈëÕËºÅÃÜÂë");
			return;
		}
		System.out.println(args[0]+":"+args[1]);
		testLogin(args[0],args[1]);
		//weiboHostMonitor();
	}
	public static void testLogin(String account,String password) throws Exception {

		SystemCommon.User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";
		
		
		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		sinaHttp.setUserName(account);
		sinaHttp.setPassWord(password);
		//13582688545----pp9999   //Î¢²©²©Ö÷ÐÅÏ¢ÅÀÈ¡
		//13513182624----pp9999   //Î¢²©ÆÀÂÛÅÀÈ¡
		
		//Î¢²©²©Ö÷ÐÅÏ¢ÅÀÈ¡		//17056545489----qw111222       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü        ¸ôÁËÒ»ÌìµÇÂ½³É¹¦   
		//Î¢²©ÆÀÂÛÅÀÈ¡		//13569438457----qw111222       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü        ¸ôÁËÒ»ÌìµÇÂ½³É¹¦ 
		
		//18818395967----qw111222       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü        ¸ôÁËÒ»ÌìµÇÂ½³É¹¦ 
		//15986824314----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢ÁË8ÌõÎ¢²©£¬µÇÂ½³É¹¦   
		//18240451867----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢ÁË2ÌõÎ¢²©£¬µÇÂ½³É¹¦
		//18906632549----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢ÁË4ÌõÎ¢²©£¬µÇÂ½³É¹¦
		//13113732458----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü       Í¨¹ý×ª·¢ÁË8ÌõÎ¢²©£¬µÇÂ½³É¹¦
		//15130156410----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½³É¹¦    
		//18819162901----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢ÁË8ÌõÎ¢²©£¬µÇÂ½³É¹¦
		//13128024284----qw444555       µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢ÁË8ÌõÎ¢²©£¬µÇÂ½³É¹¦
		//18819472126----qw444555       µÇÂ½Ê§°Ü          ³ä»áÔ±¹ýºóµÇÂ½³É¹¦

		//zoo_5@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_6@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_7@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_8@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_9@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_10@sina.com		 µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_11@sina.cn		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_12@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_13@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü
		//zoo_14@sina.com		µÇÂ½Ê§°Ü	É¨¶þÎ¬ÂëºóµÇÂ½Ê§°Ü        Í¨¹ý×ª·¢Î¢²©£¬ÒÀÈ»µÇÂ½Ê§°Ü

		System.out.println(sinaHttp.getUserAgent());
		sinaHttp.login();
		System.out.println(sinaHttp.getLoginCookie());
		// while(sinaHttp.verify()){
		// System.out.println("µÇÂ½³É¹¦£¡£¡£¡");
		// SystemCommon.sleeps(10);
		// }
	}
	public static void weiboHostMonitor() {
		
		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		while (true) {
			try {
				while(!sinaHttp.verify()){
					System.out.println("½øÐÐµÇÂ½£¬»ñÈ¡cookie");
					sinaHttp.login();
					SystemCommon.currentCookie = sinaHttp.getLoginCookie();
					System.out.println(SystemCommon.currentCookie);
					
					SystemCommon.sleeps(10);
				}ConnectSql.excuteOracle(null, "selectHostUrl");
				System.out.println("»ñÈ¡µÄ²©Ö÷ÊýÁ¿£º"+ConnectSql.urlSet.size());
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