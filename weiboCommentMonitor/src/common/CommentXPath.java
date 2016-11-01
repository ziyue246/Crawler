package common;

import java.util.Date;

public class CommentXPath {

	public static int id;
	public static String url;//原数据库有
	public static String pubtime="//DD/SPAN[@class='S_txt2']";//原数据库有
	public static Date insertTime;
	public static String md5;
	public static String userId="//DT/A//IMG/@usercard";
	public static String commentCount;//="//SPAN[@class='line S_line1'][@node-type='comment_btn_text']";
	public static String rttCount;//="//SPAN[@class='line S_line1'][@node-type='forward_btn_text']";
	//key=tblog_attention_click&value=1730077315
	public static String uid = "//DIV[@class='opt_box clearfix']/DIV[@class='btn_bed W_fl']/A/@suda-data";
	//4023368669562307
	public static String mid="//DIV[@class='WB_from S_txt2']/A/@name";
	public static String commentUrl;
	public static String rttUrl;
	public static String author="//DT/A/@title";
	// weibo/com    /lasialeehay;
	public static String authorUrl= "//DT/A/@href";
	public static String searchKeyword;//原数据库有
	public static int categoryCode;//原数据库有
	public static String authorImg="//DT/A/IMG/@src";
	public static String content = "//DD";//微博内容
	public static String source;
	public static int siteId;
	public static String imgUrl;
	public static int reliability;
	public static String gps;
	//(6)
	public static String likeCount="//A[@action-type='fl_like']";
	public static int po_score;
	public static String words;
	//id=3888502346881634&page=2
	//http://weibo.com/aj/comment/big?id=3888502346881634
	public static String nextPage = "//DIV[@class='W_pages_minibtn']/A[@class='W_btn_c btn_page_next']/SPAN/@action-data";
	
}
