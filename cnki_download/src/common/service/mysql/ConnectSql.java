package common.service.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import common.bean.IEEEinfo;
import common.system.SystemCommon;

public class ConnectSql {
	
	
	private static String url = "jdbc:mysql://172.18.79.3:3306/2016-12-14-zhinengluntai-cnki";
	//String url = "jdbc:mysql://localhost:3306/company";
	private static String user = "root";// 用户名,系统默认的账户名
	private static String password = "root";// 你安装时选设置的密码

	public static List<String>  getSearchMessageList(List<String> downloadedlist) {
		
		
		List<String> searChMessageList = new LinkedList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String sql = "SELECT t1.id AS id,t1.title AS title ,t2.name AS category "
					+ "FROM paper_filtered t1 RIGHT  JOIN category t2 "
					+ "ON t1.category_id = t2.classify_order "
					+ "where t1.id is not null";
			
			Connection con = DriverManager.getConnection(url, user, password);
			Statement st = con.createStatement();
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title").trim();
				String category = result.getString("category").trim();
				if(!downloadedlist.contains(title))
					searChMessageList.add(id+"##"+title+"##"+category);
			}
			con.close();
			st.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return searChMessageList;
	}
}
