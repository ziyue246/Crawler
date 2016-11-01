package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
			System.out.println("开始尝试连接数据库！");
			String url = "jdbc:oracle:thin:@172.18.79.3:1521:ORCL";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = "TOPSEARCH";// 用户名,系统默认的账户名TOPSEARCH
			String password = "TOPSEARCH";// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			Statement smt = con.createStatement();
			//System.out.println("连接成功！");
			
			//System.out.println(sql);
			if(commend.equals("insert")){
				result = smt.executeQuery(sql);
				SystemCommon.printLog("该数据已插成功！！！");
			}else if(commend.equals("Md5")){
				result = smt.executeQuery(sql);
				countMd5=0;
				while (result.next()) {
					++countMd5;
				}
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
				//System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 	
	public static void insertHotSearchMessage(HotSearch hotSearch){
		
		String keywordTimeSql = "select * from SEARCH_KEYWORD "
				+ " where to_char(sysdate,'yyyy-mm-dd') = to_char(PROPOSE_TIME,'yyyy-mm-dd')"+
				" and KEYWORD = '"+ hotSearch.getKeyWord() +"'";
		excuteOracle(keywordTimeSql, "Md5");
		if(countMd5>0){	
			SystemCommon.printLog("该数据已插入！！！");
			return;
		}
		String sqlInsert =  getInsertSQL(hotSearch); 
		excuteOracle(sqlInsert,"insert");
	}
	private static String getInsertSQL(HotSearch hotSearch){
		
//		String keywordTime = "select insert_time from SEARCH_KEYWORD "
//				+ " where to_char(sysdate,'yyyy-mm-dd') = to_char(PROPOSE_TIME,'yyyy-mm-dd')"+
//				" and "+b;
//
//		String sqlMd5 = "select * from WEIBO_PERSON where MD5 = '"+blog.getMd5()+"'";
//		excuteOracle(sqlMd5, "md5");
//		if(countMd5>0){
//			
//			return;
//		}
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//ID	NUMBER
		int    categoryCode = 4;					//CATEGORY_CODE	NUMBER
		String keyword = hotSearch.getKeyWord();	//KEYWORD	VARCHAR2
		//String siteId ="";//SITE_ID	VARCHAR2
		//String siteName ="";//SITE_NAME	VARCHAR2
		//int userId ;//USER_ID	NUMBER
		String proposeTime =  "to_date('"+fm.format(new Date())+"','yyyy-mm-dd hh24:mi:ss')";//PROPOSE_TIME	TIMESTAMP(6)
		int status=2;//STATUS	NUMBER
		String type=";1;2;4;8;";//TYPE	VARCHAR2
		//int category1;//CATEGORY1	NUMBER
		//String ebStype="";//EB_STYPE	VARCHAR2
		//String column1="";//COLUMN1	VARCHAR2
		//int isUsable;//IS_USABLE	NUMBER
		//int highway;//HIGHWAY	NUMBER
		//int bebug;//DEBUG	NUMBER
		//int priority;//PRIORITY	NUMBER
		int searchIndex=hotSearch.getSearchIndex();//SEARCH_INDEX	NUMBER

//
//		String sql = "insert into  SEARCH_KEYWORD(CATEGORY_CODE,    KEYWORD,      SITE_ID,     SITE_NAME,    USER_ID,   PROPOSE_TIME,   STATUS,     TYPE ,  CATEGORY1,     EB_STYPE,     COLUMN1,     IS_USABLE,   HIGHWAY,    DEBUG,    PRIORITY,   SEARCH_INDEX)"
//				                     + " values("+categoryCode+",'"+keyword+"','"+siteId+"','"+siteName+"',"+userId+","+proposeTime+","+status+","+type+","+category1+",'"+ebStype+"','"+column1+"',"+isUsable+","+highway+","+bebug+","+priority+","+searchIndex+")";
//		//System.out.println(sql);
		//return sql;	

		String sql = "insert into  SEARCH_KEYWORD(CATEGORY_CODE,    KEYWORD,     PROPOSE_TIME,   STATUS,    TYPE  ,  SEARCH_INDEX)"
				                     + " values("+categoryCode+",'"+keyword+"',"+proposeTime+","+status+",'"+type+"',"+searchIndex+")";
		return sql;
	}
}
