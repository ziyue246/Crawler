package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ConnectSql {// implements DBTest{

	public static Set<String> urlSet = new HashSet<String>();
	public static int countMd5 = 0;
	public static int maxId = 0;
	public static String table = "ieee_data";
	private static String url = "jdbc:mysql://172.18.79.3:3306/2016-12-14-zhinengluntai-ieee";
	private static String user = "root";// 用户名,系统默认的账户名
	private static String password = "root";// 你安装时选设置的密码

	public static void excuteMysql(String sql, String commend) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);// 获取连接
			Statement smt = con.createStatement();
			if (commend.equals("md5")) {
				result = smt.executeQuery(sql);
				countMd5 = 0;
				while (result.next()) {
					String md5 = result.getString("md5");
					if (md5.length() == 32) {
						System.out.println("该词条已经插入 " + md5);
						++countMd5;
					}
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void insertMysql(IEEEinfo ieeeInfo) {
		String sql = "insert into " + table
				+ " (url,title,authors,authors_ids,doi,pubtime,journal,"
				+ "insert_time,brief,keywords,refer_url,md5,cite_url,refer_num,cite_num,"
				+ "category,search_keywords)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if(ieeeInfo==null)return ;
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql驱动程序
			Connection con = DriverManager.getConnection(url, user, password);// 获取连接
			PreparedStatement preparedStmt = con.prepareStatement(sql);
	
			preparedStmt.setString(1, ieeeInfo.getUrl());
			preparedStmt.setString(2, ieeeInfo.getTitle());
			preparedStmt.setString(3, ieeeInfo.getAuthors());
			preparedStmt.setString(4, ieeeInfo.getAuthorsids());
			preparedStmt.setString(5, ieeeInfo.getDoi());
			preparedStmt.setTimestamp(6, new Timestamp(ieeeInfo.getPubtime().getTime()));
			preparedStmt.setString(7, ieeeInfo.getJournal());
			preparedStmt.setTimestamp(8, new Timestamp((new Date()).getTime()));
			preparedStmt.setString(9, ieeeInfo.getBrief());
			preparedStmt.setString(10, ieeeInfo.getKeywords());
			preparedStmt.setString(11, ieeeInfo.getReferUrl());
			preparedStmt.setString(12, ieeeInfo.getMd5());
			preparedStmt.setString(13, ieeeInfo.getCiteUrl());
			preparedStmt.setInt(14, ieeeInfo.getReferNum());
			preparedStmt.setInt(15, ieeeInfo.getCiteNum());
			preparedStmt.setString(16, ieeeInfo.getCategory());
			preparedStmt.setString(17, ieeeInfo.getSearchKeyword());
			preparedStmt.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertMessage(IEEEinfo ieeeInfo) {
		String sqlMd5 = "select md5 from " + table + " where MD5 = '" + ieeeInfo.getMd5() + "'";
		excuteMysql(sqlMd5, "md5");
		if (countMd5 > 0) {
			return;
		}
		insertMysql(ieeeInfo);
	}
	public static void main(String[] args) throws Exception {
		System.out.println("cdb970094f55cc24d1253de47b0'2daae".replace("'", "\\'"));
	}
}
