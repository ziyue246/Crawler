package common;

public class BloggersXPath {
	public static String author="//H1[@class='username']";//博主名字
	
	
	/*
	昵称：黄晓明
	所在地：北京 东城区
	性别：男
	个性域名：http://weibo.com/huangxiaoming
	简介：少说话，多做事！我不是最好的，但我要做最努力的......（相关工作事宜联系经纪人郭亭婷：hxmstudiowb2016@126.com）
	注册时间：2010-04-14
	标签：歌手演员
	*/
	public static  String baseInfo="//LI[@class='li_1 clearfix']";
	
	
	/*
	577关注
	49358613粉丝
	2870微博
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
	public static  String img="";
	public static  String fansNum="";
	public static  String weiboNum="";
	public static  String certify="";
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
	public static  String categoryCode="";
	public static  String siteId="";
	public static  String birth="";
	public static  String authorFlag="";
	public static  String weiboId="";
	public static  String province="";
}
