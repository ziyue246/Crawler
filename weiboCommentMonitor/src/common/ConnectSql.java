package common;

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

import org.apache.commons.configuration.SystemConfiguration;

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
			
			// 插入
			if(commend.equals("insert")){
				result = smt.executeQuery(sql);
			}
			//查询
			else if(commend.equals("selectUrl")){
				if(sql==null){
					sql = "select * from weibo_data_processed "
							+ "where category_code in (select category_id from leaders) "
							+ "and URL not in (select URL from weibo_comment_data)";
//					sql = "select * from weibo_data_processed "
//					+ "where category_code between 561 and 759";
//					sql = "select * from WEIBO_DATA "
//							+ " where to_char(sysdate-1,'yyyy-mm-dd') = to_char(insert_time,'yyyy-mm-dd')";
				}
				SystemCommon.printLog(sql);
				result = smt.executeQuery(sql);
				if(result!=null)
					sqlreslut2WeiboDateProcessed(result);
			}
			//md5
			else if(commend.equals("md5")){
				result = smt.executeQuery(sql);
				countMd5=0;
				while (result.next()) {
					String md5 = result.getString("md5");
					if(md5.length()==32){
						System.out.println("该词条已经插入 "+ md5);
						++countMd5;
					}
				}
			}
			// get maxid
			else if(commend.equals("id")){
				result = smt.executeQuery(sql);
				maxId=0;
				while (result.next()) {
					maxId = result.getInt("id");
				}
				++maxId;
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
 	public static void sqlreslut2WeiboDate(ResultSet result){
 		try {
 			if(!SystemCommon.weiboDataSet.isEmpty())
 				SystemCommon.weiboDataSet.clear();
			while (result.next()) {
				WeiboData weibodate = new WeiboData();
				weibodate.setAuthor(result.getString("AUTHOR").replaceAll(" ", ""));
				weibodate.setAuthorImg(result.getString("AUTHOR_IMG"));
				weibodate.setAuthorUrl(result.getString("AUTHOR_URL"));
				weibodate.setCategoryCode(result.getInt("CATEGORY_CODE"));
				weibodate.setCommentCount(result.getInt("COMMENT_COUNT"));
				weibodate.setCommentUrl(result.getString("COMMENT_URL"));
				weibodate.setContent(result.getString("CONTENT"));
				weibodate.setGPS(result.getString("GPS"));
				weibodate.setId(result.getInt("ID"));
				weibodate.setImgUrl(result.getString("IMG_URL"));
				weibodate.setInsertTime(result.getDate("INSERT_TIME"));
				weibodate.setLikeCount(result.getInt("LIKE_COUNT"));
				weibodate.setMd5(result.getString("MD5"));
				weibodate.setMid(result.getString("MID"));
				weibodate.setPubtime(result.getDate("PUBTIME"));
				weibodate.setReliability(result.getInt("RELIABILITY"));
				weibodate.setRttCount(result.getInt("RTT_COUNT"));
				weibodate.setRttUrl(result.getString("RTT_URL"));
				weibodate.setSearchKeyword(result.getString("SEARCH_KEYWORD"));
				weibodate.setSiteId(result.getInt("SITE_ID"));
				weibodate.setSource(result.getString("SOURCE"));
				weibodate.setUrl(result.getString("URL"));
				weibodate.setUserId(result.getInt("USER_ID"));
				SystemCommon.weiboDataSet.add(weibodate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	public static void insertMessage(Comment comment){
		
		String sqlMd5 = "select md5 from WEIBO_COMMENT_DATA where MD5 = '"+comment.getMd5()+"'";
		excuteOracle(sqlMd5, "md5");
		if(countMd5>0){
			return;
		}
		String sqlInsert =  getInsertSQL(comment); 
		excuteOracle(sqlInsert,"insert");
	}
	private static String getInsertSQL(Comment comment){
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sqlId = "select max(id) as id from WEIBO_COMMENT_DATA";
			excuteOracle(sqlId, "id");
			int id = maxId;
		
		 String url = comment.getUrl();
		 String pubtime = "to_date('"+fm.format(comment.getPubtime())+"','yyyy-mm-dd hh24:mi:ss')";
		 String insertTime = "to_date('"+fm.format(new Date())+"','yyyy-mm-dd hh24:mi:ss')";
		 String md5 = comment.getMd5();
		 int userId = comment.getUserId();
		 int commentCount = comment.getCommentCount();
		 int rttCount = comment.getRttCount();
		 String mid = comment.getMid();
		 String commentUrl = comment.getCommentUrl();
		 String rttUrl = comment.getRttUrl();
		 String author = comment.getAuthor();
		 String authorUrl = comment.getAuthorUrl();
		 String searchKeyword = comment.getSearchKeyword();
		 int categoryCode = comment.getCategoryCode();
		 String authorImg = comment.getAuthorImg();
		 String content = comment.getContent();
		 String source = comment.getSource();
		 int siteId = comment.getSiteId();
		 String imgUrl = comment.getImgUrl();
		 int reliability = comment.getReliability();
		 String gps = comment.getGps();
		 int likeCount = comment.getLikeCount();
		 int po_score = comment.getPo_score();
		 String words = comment.getWords();
		
		 String sql = "insert into  WEIBO_COMMENT_DATA (	 ID     ,URL      ,PUBTIME,   INSERT_TIME    ,MD5,    USER_ID,    COMMENT_COUNT   ,RTT_COUNT,    MID     ,COMMENT_URL     ,RTT_URL      ,AUTHOR,      AUTHOR_URL,     SEARCH_KEYWORD,    CATEGORY_CODE,    AUTHOR_IMG,    CONTENT,     SOURCE,     SITE_ID,    IMG_URL,    RELIABILITY,     GPS,     LIKE_COUNT,     PO_SCORE,    WORDS)"
                 							 +   " values("+id+",'"+url+"',"+pubtime+","+insertTime+",'"+md5+"',"+userId+","+commentCount+","+rttCount+",'"+mid+"','"+commentUrl+"','"+rttUrl+"','"+author+"','"+authorUrl+"','"+searchKeyword+"',"+categoryCode+",'"+authorImg+"','"+content+"','"+source+"',"+siteId+",'"+imgUrl+"',"+reliability+",'"+gps+"',"+likeCount+",  "+po_score+",'"+words+"')";
		 SystemCommon.printLog(sql);
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
