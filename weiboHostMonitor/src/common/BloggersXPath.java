package common;

public class BloggersXPath {
	public static String author="//H1[@class='username']";//��������
	public static String authorImg="//P[@class='photo_wrap']/IMG/@src";
	////DIV[@class='pf_photo']//P[@class='photo_wrap']/IMG/@src
							////P[@class='photo_wrap']/IMG/@src
	
	
	/*
	�ǳƣ�������
	���ڵأ����� ������
	�Ա���
	����������http://weibo.com/huangxiaoming
	��飺��˵���������£��Ҳ�����õģ�����Ҫ����Ŭ����......����ع���������ϵ�����˹�ͤ�ã�hxmstudiowb2016@126.com��
	ע��ʱ�䣺2010-04-14
	��ǩ��������Ա
	*/
	public static  String baseInfo="//LI[@class='li_1 clearfix']";
	
	
	/*
	577��ע
	49358613��˿
	2870΢��
	*/
	public static  String followSoOn = "//TD[@class='S_line1']";
								    // "//TD[@class='S_line1']";
	
	/*
	0:http://weibo.com/p/1006051730077315/follow?from=page_100605&wvr=6&mod=headfollow#place
	1:http://weibo.com/p/1006051730077315/follow?relate=fans&from=100605&wvr=6&mod=headfans&current=fans#place
	2:http://weibo.com/p/1006051730077315/home?from=page_100605_profile&wvr=6&mod=data#place 
	 */
	public static  String followSoOnUrl = "//TD[@class='S_line1']/A[1]/@href";
								       // "//TD[@class='S_line1']";
	
	/*
	http://weibo.com/u/5144924711?refer_flag=1001030103_
	http://weibo.com/u/1949942544?refer_flag=1001030103_
	http://weibo.com/u/5586271100?refer_flag=1001030103_
	 */
	public static  String  searchHostUrl = "//DIV[@class='face']/A[1]/@href";
	
	public static  String url="";
	public static  String img="//DIV[@class='pf_photo']//P[@class='photo_wrap']/IMG/@src";
	public static  String fansNum="";
	public static  String weiboNum="";
	//��    0
	//W_icon icon_pf_approve  ������֤  1
	//W_icon_co2 icon_pf_approve_co    ��ҵ��֤
	public static  String certify="//DIV[@class='pf_photo'][@node-type='photo']/A/EM/@class";
	public static  String address="";
	public static  String sex="";
	public static  String info="";
	public static  String tag="//SPAN[@class='W_arrow_bor W_arrow_bor_l']";
	public static  String company="";
	public static  String nick="";
	public static  String registTime="";
	public static  String fansUrl="";
	public static  String followUrl="";
	public static  String weiboUrl="";
	public static  String infoUrl="";
	public static  String concact="";
	public static  String authorUid="";
	//��    0
	//W_icon icon_pf_approve  ������֤  1
	//W_icon_co2 icon_pf_approve_co    ��ҵ��֤
	public static  String categoryCode="//DIV[@class='pf_photo'][@node-type='photo']/A/EM/@class";
	
	
	public static  String siteId="";
	public static  String birth="";
	public static  String authorFlag="";
	public static  String weiboId="";
	public static  String province="";
}
