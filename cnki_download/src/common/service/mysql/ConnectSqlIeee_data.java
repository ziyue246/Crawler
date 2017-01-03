package common.service.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import common.bean.IEEEinfo;
import common.system.SystemCommon;

public class ConnectSqlIeee_data {
	public static String table = "ieee_data";
	private static String url = "jdbc:mysql://172.18.79.3:3306/2016-12-14-zhinengluntai-ieee";
	private static String user = "root";// 用户名,系统默认的账户名
	private static String password = "root";// 你安装时选设置的密码

	public static int countMd5(String md5Code) {
		
		ResultSet result = null;// 创建一个结果集对象
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String sqlMd5 = "select count(*) as md5Count from " + table + " where MD5 = '" + md5Code + "'";
			result = DriverManager.getConnection(url, user, password).
					createStatement().executeQuery(sqlMd5);
			while (result.next()) {
				return result.getInt("md5Count");
			}
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0;
	}

	public static void insertMysql(IEEEinfo ieeeInfo) {
		String sql = "insert into " + table + " (url,title,authors,authors_ids,doi,pubtime,journal,"
				+ "insert_time,brief,keywords,refer_url,md5,cite_url,refer_num,cite_num," + "category,search_keywords)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (ieeeInfo == null)
			return;
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
		if(countMd5(ieeeInfo.getMd5())==0){
			insertMysql(ieeeInfo);
		}else{
			SystemCommon.printLog("the "+ieeeInfo.getTitle()+" was inserted!!");
		}
	}
}
