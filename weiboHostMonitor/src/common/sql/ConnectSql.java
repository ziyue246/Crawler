package common.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import common.Bloggers;
import common.SystemCommon;
import common.WeiboDataProcessed;

public class ConnectSql {// implements DBTest{
	
	public static Set<String>  urlSet = new HashSet<String>();
	public static int countMd5 =0;
	public static int maxId = 0;
 	public static void excuteOracle(String sql,String commend) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序

			String url = "jdbc:oracle:thin:@172.18.79.3:1521:ORCL";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = "JINRONG";// 用户名,系统默认的账户名
			String password = "jinrong";// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			Statement smt = con.createStatement();
	
			/*
			 * 
			 * select distinct author_url from WEIBO_DATA 
where author_url not in(select author_url from Weibo_Person )
and to_char(sysdate-1,'yyyy-mm-dd') = to_char(insert_time,'yyyy-mm-dd')
			 * 
			 */
			
			if(commend.equals("insert")){
				
				result = smt.executeQuery(sql);
			}else if(commend.equals("selectHostUrl")){
				
				if(sql==null){
					sql = "select * from weibo_data_processed "
							+ "where to_char(sysdate-300,'yyyy-mm-dd') < to_char(insert_time,'yyyy-mm-dd') "
							+ "and author_url not in (select author_url from weibo_person ) "
							+ "and to_char(sysdate-1,'yyyy-mm-dd') = to_char(insert_time,'yyyy-mm-dd')";
							//+ "and rownum <1000";
//					sql = "select author_url from WEIBO_DATA "
//							+"where author_url not in(select author_url from Weibo_Person ) "
//							+ "and to_char(sysdate-1,'yyyy-mm-dd') = to_char(insert_time,'yyyy-mm-dd')";
				}
				SystemCommon.printLog("select : "+sql);
				result = smt.executeQuery(sql);
				sqlreslut2WeiboDateProcessed(result);
//				//urlSet.clear();
//				while (result.next()) {
//					System.out.println("url:" + result.getString("author_url"));
//					urlSet.add(result.getString("author_url"));
//					
//				}
			}else if(commend.equals("md5")){
				result = smt.executeQuery(sql);
				countMd5=0;
				while (result.next()) {
					String md5 = result.getString("md5");
					if(md5.length()==32){
						System.out.println("该词条已经插入 "+ md5);
						++countMd5;
					}
				}
			}else if(commend.equals("id")){
				result = smt.executeQuery(sql);
				maxId=0;
				while (result.next()) {
					maxId = result.getInt("id");
				}
				++maxId;
			}else if(commend.equals("delete")){
				result = smt.executeQuery(sql);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 	public static void sqlreslut2WeiboDateProcessed(ResultSet result){
 		try {
 			if(!SystemCommon.WeiboDataProcessedSet.isEmpty())
 				SystemCommon.WeiboDataProcessedSet.clear();
 			
			while (result.next()) {
				WeiboDataProcessed weiboDatap = new WeiboDataProcessed();
				weiboDatap.setAuthor(result.getString("AUTHOR").replaceAll(" ", ""));
				//				AUTHOR	VARCHAR2
				weiboDatap.setAuthorImg(result.getString("AUTHOR_IMG"));
				//			 	AUTHOR_IMG	VARCHAR2
				weiboDatap.setAuthorUrl(result.getString("AUTHOR_URL"));
				//			 	AUTHOR_URL	VARCHAR2
				weiboDatap.setCategory1(result.getInt("CATEGORY1"));
				weiboDatap.setCategory2(result.getInt("CATEGORY2"));
				weiboDatap.setCategory3(result.getInt("CATEGORY3"));
				//			 	CATEGORY1	NUMBER
				//			 	CATEGORY2	NUMBER
				//			 	CATEGORY3	NUMBER
				weiboDatap.setCategoryCode(result.getString("CATEGORY_CODE"));
				//			 	CATEGORY_CODE	VARCHAR2
				weiboDatap.setCollectedNum(result.getInt("COLLECTED_NUM"));
				//			 	COLLECTED_NUM	NUMBER
				weiboDatap.setCommentNum(result.getInt("COMMENT_NUM"));
				//			 	COMMENT_NUM	NUMBER
				weiboDatap.setCommentUrl(result.getString("COMMENT_URL"));
				//			 	COMMENT_URL	VARCHAR2
				weiboDatap.setContent(result.getString("CONTENT"));
				//			 	CONTENT	CLOB
				weiboDatap.setEmotionScore(result.getInt("EMOTION_SCORE"));
				//			 	EMOTION_SCORE	NUMBER
				weiboDatap.setEntities(result.getString("ENTITIES"));
				//			 	ENTITIES	VARCHAR2
				weiboDatap.setGPSinfo(result.getString("GPS_INFO"));
				//			 	GPS_INFO	VARCHAR2
				weiboDatap.setHotIndex(result.getInt("HOT_INDEX"));
				//			 	HOT_INDEX	NUMBER
				//			 	ID	NUMBER
				weiboDatap.setImgUrl(result.getString("IMG_URL"));
				//			 	IMG_URL	VARCHAR2
				//			 	INSERT_TIME	TIMESTAMP(6)
				weiboDatap.setIsJunk(result.getInt("IS_JUNK"));
				//			 	IS_JUNK	NUMBER
				weiboDatap.setKeywords(result.getString("KEYWORDS"));
				//			 	KEYWORDS	VARCHAR2
				weiboDatap.setLikeCount(result.getInt("LIKE_COUNT"));
				//			 	LIKE_COUNT	NUMBER
				//weiboDatap.setKeywords(result.getString("KEYWORDS"));
				//			 	MD5	VARCHAR2
				weiboDatap.setMediaType(result.getInt("MEDIA_TYPE"));
				//			 	MEDIA_TYPE	NUMBER
				weiboDatap.setMid(result.getString("MID"));
				//			 	MID	VARCHAR2
				weiboDatap.setOriginalId(result.getInt("ORIGINAL_ID"));
				//			 	ORIGINAL_ID	NUMBER
				weiboDatap.setPosiInfo(result.getString("POSI_INFO"));
				//			 	POSI_INFO	VARCHAR2
				weiboDatap.setPosiType(result.getInt("POSI_TYPE"));
				//			 	POSI_TYPE	NUMBER
				//			 	PUBTIME	TIMESTAMP(6)
				weiboDatap.setReliability(result.getInt("RELIABILITY"));
				//			 	RELIABILITY	NUMBER
				weiboDatap.setRoad(result.getString("ROAD"));
				//			 	ROAD	VARCHAR2
				weiboDatap.setRttNum(result.getInt("RTT_NUM"));
				//			 	RTT_NUM	NUMBER
				weiboDatap.setRttUrl(result.getString("RTT_URL"));
				//			 	RTT_URL	VARCHAR2
				weiboDatap.setSearchKeyword(result.getString("SEARCH_KEYWORD"));
				//			 	SEARCH_KEYWORD	VARCHAR2
				weiboDatap.setSearchKeyword(result.getString("SENTI_WORDS"));
				//			 	SENTI_WORDS	VARCHAR2
				weiboDatap.setSiteId(result.getInt("SITE_ID"));
				//			 	SITE_ID	NUMBER
				weiboDatap.setSource(result.getString("SOURCE"));
				//			 	SOURCE	VARCHAR2
				weiboDatap.setTarget(result.getString("TARGET"));
				//			 	TARGET	VARCHAR2
				weiboDatap.setUrl(result.getString("URL"));
				//			 	URL	VARCHAR2
				weiboDatap.setUserId(result.getInt("USER_ID"));
				//			 	USER_ID	NUMBER
				weiboDatap.setWarnLevel(result.getInt("WARN_LEVEL"));
				//			 	WARN_LEVEL	NUMBER
				weiboDatap.setWarnWord(result.getString("WARN_WORD"));
				//			 	WARN_WORD	VARCHAR2
				
				
				SystemCommon.WeiboDataProcessedSet.add(weiboDatap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	public static void insertBloggerMessage(Bloggers blog){
		
		String sqlMd5 = "delete from WEIBO_PERSON where author = '"+blog.getAuthor()+"'";
		excuteOracle(sqlMd5, "delete");
//		if(countMd5>0){
//			String sqlMd5 = "select md5 from WEIBO_PERSON where author = '"+blog.getAuthor()+"'";
//			excuteOracle(sqlMd5, "md5");
//			return;
//		}
		String sqlInsert =  getInsertSQL(blog); 
		excuteOracle(sqlInsert,"insert");
	}
	private static String getInsertSQL(Bloggers blog){
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlId = "select max(id) as id from weibo_person";
		excuteOracle(sqlId, "id");
		int id = maxId;
		String  author = blog.getAuthor();
		String  authorUrl = blog.getAuthorUrl();
		String  authorImg = blog.getAuthorImg();
		String  insertTime = "to_date('"+fm.format(new Date())+"','yyyy-mm-dd hh24:mi:ss')";//fm.format(new Date())
		String  md5 = blog.getMd5();
		int     fansNum = blog.getFansNum(); 
		int     followNum = blog.getFollowNum();  
		int     weiboNum = blog.getWeiboNum(); 
		String  certify = blog.getCertify(); 
		String  address = blog.getAddress(); 
		String  sex = blog.getSex(); 
		String  info = blog.getInfo(); 
		String  tag = blog.getTag(); 
		String  company = blog.getCompany(); 
		String  nick = blog.getNick(); 
		String  registTime = "to_date('"+fm.format(blog.getRegistTime())+"','yyyy-mm-dd hh24:mi:ss')";//blog.getRegistTime(); 

		
		String  fansUrl = blog.getFansUrl(); 
		String  followUrl = blog.getFollowUrl(); 
		String  weiboUrl = blog.getWeiboUrl(); 
		String  infoUrl = blog.getInfoUrl(); 
		String  concact = blog.getConcact(); 
		String  authorUid = blog.getAuthorUid(); 
		int     categoryCode = blog.getCategoryCode(); 
		int     siteId = blog.getSiteId(); 
		String  birth = blog.getBirth(); 
		int     authorFlag = blog.getAuthorFlag(); 
		int     weiboId = blog.getWeiboId(); 
		String  province = blog.getProvince();
		String sql = "insert into  WEIBO_PERSON  (id,author,author_url,author_Img,insert_time,md5,        fans_Num,   follow_Num,   weibo_Num   ,certify,    address   ,sex,           info,      tag,      company       ,nick,     regist_time,   fans_url,     follow_url,     weibo_Url,      info_url     ,concact      ,author_uid,     category_code,    site_id,   birth,    author_flag,weibo_id,province)"
				+ " values("+id+",'"+author+"','"+authorUrl+"','"+authorImg+"',"+insertTime+",'"+md5+"',"+fansNum+","+followNum+","+weiboNum+",'"+certify+"','"+address+"','"+sex+"','"+info+"','"+tag+"','"+company+"','"+nick+"',"+registTime+",'"+fansUrl+"','"+followUrl+"','"+weiboUrl+"','"+infoUrl+"','"+concact+"','"+authorUid+"',  "+categoryCode+","+siteId+",'"+birth+"',"+authorFlag+","+weiboId+",'"+province+"')";
		//System.out.println(sql);
		return sql;
	}
	public static void main(String[] args) throws Exception {
		//excuteOracle(null,"selectHostUrl");
		// new ConnectSql().SelectUser();
		//testOracle();
//		
//		int count = selectMd5("sdfsdf");
//		System.out.println(count);
//		
//		selectOracle(null);
		
		
//		String  url="http://weibo.com/p/1005051807810307/info?mod=pedit_more";
//		String bloggerUrl = null;
//		Bloggers blog = Extractor.getBloggerinfo(url,bloggerUrl);
//		SystemCommon.print(blog);
		System.out.println("cdb970094f55cc24d1253de47b02daae".length());
	}
}
