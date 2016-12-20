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
			String user = "JINRONG";// 用户名,系统默认的账户名
			String password = "jinrong";// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			Statement smt = con.createStatement();
			System.out.println("连接成功！");
			
			
			if(commend.equals("insert")){
				result = smt.executeQuery(sql);
			}else if(commend.equals("selectHostUrl")){
				if(sql==null){
					sql = "select author_url from WEIBO_DATA "
							+ " where to_char(sysdate-1,'yyyy-mm-dd') = to_char(insert_time,'yyyy-mm-dd')";
				}
				result = smt.executeQuery(sql);
				urlSet.clear();
				while (result.next()) {
					System.out.println("url:" + result.getString("author_url"));
					urlSet.add(result.getString("author_url"));
				}
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
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 	
	public static void insertBloggerMessage(Bloggers blog){
		
		String sqlMd5 = "select md5 from WEIBO_PERSON where MD5 = '"+blog.getMd5()+"'";
		excuteOracle(sqlMd5, "md5");
		if(countMd5>0){
			
			return;
		}
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
