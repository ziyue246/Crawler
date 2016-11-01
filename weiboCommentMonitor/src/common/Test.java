package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.spi.CalendarDataProvider;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import http.SinaHttpProcess;

public class Test {
	public static void search() throws Exception {
		Splider splider = new Splider();
		String url = "http://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
		String content = splider.getHtmlContent(url, url);

		content = DomTree.weiboSearchJsHtml(content);
		Node node = DomTree.getNode(content, null);

		//DomTree.printNode("node", node);
		//NodeList nl = DomTree.commonList("//P[@node-type='feed_list_content']", node);
		String xpath = "//P[@class='star_name']/A[1]/@href";
		NodeList nl = DomTree.commonList(xpath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			String s = nl.item(i).getTextContent();
			s = "http://s.weibo.com"+DomTree.trimInnerSpaceStr(s);
			System.out.println(s);
		}
		System.out.println("nl.getLength()"+nl.getLength());
	}
	public static void monitor() throws Exception {
		Splider p = new Splider();
		
		
		String[] mkeyvalues = { 
				"黄晓明", "http://weibo.com/huangxiaoming?profile_ftype=1&is_all=1", 
				"王宝强", "http://weibo.com/wbq?profile_ftype=1&is_all=1#_0", 
				"赵薇", "http://weibo.com/zhaowei?profile_ftype=1&is_all=1#_0", 
				"郭德纲","http://weibo.com/guodegang?profile_ftype=1&is_all=1#_0", 
				"文章", "http://weibo.com/wenzhang626?profile_ftype=1&is_all=1#_0", 
				"小沈阳", "http://weibo.com/xiaoshenyang2008?profile_ftype=1&is_all=1#_0", 
				"罗玉凤", "http://weibo.com/fengjieluoyufeng?profile_ftype=1&is_all=1#_0", 
				"王思聪", "http://weibo.com/sephirex?profile_ftype=1&is_all=1#_0", 
				"邓超",   "http://weibo.com/dengchao?topnav=1&wvr=6&topsug=1&is_hot=1", 
				"陈赫",    "http://weibo.com/chenhe09?topnav=1&wvr=6&topsug=1&is_hot=1", 
				};
		
		//http://weibo.com/1649159940/CB8lIsSe6
		String turl = "http://weibo.com/1649159940/CB8lIsSe6"; 
		String turl1 = "http://weibo.com/aj/comment/big?id=3888502346881634"; 
		String url = turl1;//mkeyvalues[1];							//4022840136723741   //4024349414962034
		String content = p.getHtmlContent(url, url);				//4023368669562307   id=4023368669562307&page=2
		
		content = DomTree.weiboMonitorJsHtml(content);//weiboMonitorJsHtml
		//System.out.println(content);
		Node node = DomTree.getNode(content, null);
		
		// TABLE/TBODY/TR/TD[@width='200'][@class='td_on'][1]
		//DomTree.printNode("node", node);
		////<div class="WB_from S_txt2">
					//		/a action-type="ignore_list"
		//span class="S_txt2"
		String contestPath = "//DD/SPAN[@class='S_txt2']";//WB_text W_f14
		String pubtimePath = "//DD/SPAN[@class='S_txt2']";//WB_text W_f14
		String contestMidPath = "//DIV[@class='WB_from S_txt2']/A/@name";
		String commentPath = "//DIV[@class='WB_text']";
		
		String imgUrlPath="//DT/A/IMG/@src";
		String userName = "//DT/A/@title";
		String userUrl = "//DT/A/@href";
		String userid = "//DT/A//IMG/@usercard";
		//String countForwardPath = "//EM[@class='W_ficon ficon_forward S_ficon']";
		String countForwardPath = "//SPAN[@class='line S_line1']/SPAN[1]/EM[@class='W_ficon ficon_forward S_ficon']";
		String countRepeatPath = "//SPAN[@class='line S_line1']/SPAN[1]/EM[@class='W_ficon ficon_repeat S_ficon']";
		String countPraisedPath = "//SPAN[@class='line S_line1']/SPAN[@node-type='like_status']/EM[@class='W_ficon ficon_praised S_txt2']";
		String RTTCount = "//SPAN[@class='line S_line1'][@node-type='forward_btn_text']";	
		String commentCount = "//SPAN[@class='line S_line1'][@node-type='comment_btn_text']";
		String nextPage = "//DIV[@class='W_pages_minibtn']/A[@class='W_btn_c btn_page_next']/SPAN/@action-data";
		                      //<span node-type="like_status" class=""><em class="W_ficon ficon_praised S_txt2">
		String praisedCount = "//A[@action-type='fl_like']";
		String uid = "//DIV[@class='opt_box clearfix']/DIV[@class='btn_bed W_fl']/A/@suda-data";
		//div class="info"
		String allPath = "//DD";
		String namePath = "//DT/A/@title";
		NodeList nl = DomTree.commonList(contestPath, node);
		NodeList nl2 = DomTree.commonList(namePath, node);
		NodeList nl3 = DomTree.commonList(pubtimePath, node);
		NodeList nl4 = DomTree.commonList(allPath, node);
		for (int i = 0; i < nl.getLength(); i++) {
			//String s = nl.item(i).getNextSibling().getTextContent();
			//s = DomTree.trimInnerSpaceStr(s);
//			
//			String name = (nl2.item(i).getTextContent());
////			System.out.println(nl.item(i).getPreviousSibling().getTextContent());
//			String contentt = nl4.item(i).getTextContent().replaceAll("\\s+", "");
//			System.out.println(contentt);
//			System.out.println(contentt.split("：")[1].split("[(]")[0]);
			String  pubtime = nl3.item(i).getTextContent().replace("(", "").replace(")", "");
			System.out.println("1:"+nl3.item(i).getTextContent());
			System.out.println("2:"+pubtime);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			System.out.println("3:"+format.parse(pubtime)+"\n\n");
		}
		System.out.println("monitor nl.getLength()"+nl.getLength());
	}
	
	private static Date getPubtime(String pubtime){
		SimpleDateFormat ymdfmt = new  SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf  =   new  SimpleDateFormat("yyyy-MM-dd HH:mm" );
		
		
		if(pubtime.contains("分钟前")){
			int am = Integer.parseInt(pubtime.split("分钟前")[0]);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -am);
			return calendar.getTime();
			
		}else if(pubtime.contains("今天")){
			pubtime = pubtime.replaceAll("今天", ymdfmt.format(new Date()));
		}
		if(valiDateTimeWithLongFormat(pubtime)){
			try {
				
				return sdf.parse(pubtime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println(pubtime.length()+"\t:\t"+"2015-9-18 19:14".length());
		return new Date();
	}
	
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}
	public static void main(String[] args) throws Exception {	
		
		System.out.println(getPubtime("56分钟前"));
		System.out.println(getPubtime("今天 13:22"));
		System.out.println(getPubtime("2015-9-18 19:14"));
//		SystemCommon.currentCookie = "SCF=AtnRECrgxmFJcBzFWSWY2aRtn4B-0tc43fr1XidsXmlACsSeORQcdQqWisnZ-LInANkyiEn93-uglHd-5ctQUl8.; SUB=_2A2566aXvDeTxGeNH7FET9ifLwzqIHXVZnpAnrDV8PUNbktANLU2mkW8oKM5s90BnjvENOVb5ku9W26R6vQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WW0zHLmNmTZd4VUXhAfrgVc5JpX5K2hUgL.Fo-4S0eESo.N1hq2dJLoI0qLxKnL1--L12BLxK.L1h2LBK-LxKnL1K2LBo2LxK-L12qLBonLxKqL12eL1h.LxK-L12-LB.zt; SUHB=079TMn7MUxI14s; YF-Ugrow-G0=ad06784f6deda07eea88e095402e4243; SCF=AtnRECrgxmFJcBzFWSWY2aRtn4B-0tc43fr1XidsXmlACsSeORQcdQqWisnZ-LInANkyiEn93-uglHd-5ctQUl8.; SUB=_2A2566aXvDeTxGeNH7FET9ifLwzqIHXVZnpAnrDV8PUNbktANLU2mkW8oKM5s90BnjvENOVb5ku9W26R6vQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WW0zHLmNmTZd4VUXhAfrgVc5JpX5K2hUgL.Fo-4S0eESo.N1hq2dJLoI0qLxKnL1--L12BLxK.L1h2LBK-LxKnL1K2LBo2LxK-L12qLBonLxKqL12eL1h.LxK-L12-LB.zt; SUHB=079TMn7MUxI14s; YF-Ugrow-G0=ad06784f6deda07eea88e095402e4243;";
//			monitor();
	}
}
