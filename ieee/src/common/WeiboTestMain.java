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
		//13582688545----pp9999   //΢��������Ϣ��ȡ
		//13513182624----pp9999   //΢��������ȡ
		
		//΢��������Ϣ��ȡ		//17056545489----qw111222       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��        ����һ���½�ɹ�   
		//΢��������ȡ		//13569438457----qw111222       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��        ����һ���½�ɹ� 
		
		//18818395967----qw111222       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��        ����һ���½�ɹ� 
		//15986824314----qw444555       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת����8��΢������½�ɹ�   
		//18240451867----qw444555       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת����2��΢������½�ɹ�
		//18906632549----qw444555       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת����4��΢������½�ɹ�
		//13113732458----qw444555       ��½ʧ��	ɨ��ά����½ʧ��       ͨ��ת����8��΢������½�ɹ�
		//15130156410----qw444555       ��½ʧ��	ɨ��ά����½�ɹ�    
		//18819162901----qw444555       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת����8��΢������½�ɹ�
		//13128024284----qw444555       ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת����8��΢������½�ɹ�
		//18819472126----qw444555       ��½ʧ��          ���Ա�����½�ɹ�

		//zoo_5@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_6@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_7@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_8@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_9@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_10@sina.com		 ��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_11@sina.cn		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_12@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_13@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��
		//zoo_14@sina.com		��½ʧ��	ɨ��ά����½ʧ��        ͨ��ת��΢������Ȼ��½ʧ��

		System.out.println(sinaHttp.getUserAgent());
		sinaHttp.login();
		System.out.println(sinaHttp.getLoginCookie());
		// while(sinaHttp.verify()){
		// System.out.println("��½�ɹ�������");
		// SystemCommon.sleeps(10);
		// }
	}
	public static void weiboHostMonitor() {
		
		SinaHttpProcess sinaHttp = new SinaHttpProcess();
		while (true) {
			try {
				while(!sinaHttp.verify()){
					System.out.println("���е�½����ȡcookie");
					sinaHttp.login();
					SystemCommon.currentCookie = sinaHttp.getLoginCookie();
					System.out.println(SystemCommon.currentCookie);
					
					SystemCommon.sleeps(10);
				}ConnectSql.excuteOracle(null, "selectHostUrl");
				System.out.println("��ȡ�Ĳ���������"+ConnectSql.urlSet.size());
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