package common.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import common.bean.WebData;
import common.system.Systemconfig;
import common.util.StringUtil;



/**
 * 数据库操作
 * @author root
 *
 */
public class MysqlServiceImpl implements MysqlService {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final String csql = "insert into wos_refersearch(" +
			"author," +
			"refer_count," +
			"refer_url," +
			"pubyear," +
			"title," +
			"insert_time) values(?,?,?,?,?,?)";
	@Override
	public void saveDatas(List<WebData> list) {
		for(WebData wd : list)
			saveReferSearch(wd);
	}
	
	private void saveReferSearch(final WebData wd) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(csql, new String[]{"id"});
				ps.setString(1, wd.getAuthor());
				ps.setInt(2, wd.getRefer_num());
				ps.setObject(3, wd.getRefer_url());
				ps.setObject(4, wd.getPubyear());
				ps.setString(5, wd.getTitle());
				ps.setObject(6, wd.getInserttime());
				return ps;
			}
		}, keyHolder);
	}

	private static final String vsql = "insert into wos_data(" +
			"url, " +
			"title," +
			"author," +
			"pubtime," +
			"journal," +
			"address, " +
			"insert_time," +
			"brief," +
			"keywords," +
			"refer_url," +
			"md5," +
			"cite_url," +
			"refer_num," +
			"cite_num," +
			"email," +
			"fund," +
			"category) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	@Override
	public void saveData(final WebData vd) {
		if(findId(vd.getMd5(), "wos_data") > 0) return;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(vsql, new String[]{"id"});
				ps.setString(1, vd.getUrl());
				ps.setString(2, vd.getTitle());
				ps.setString(3, vd.getAuthor());
				ps.setString(4, vd.getPubyear());
				ps.setString(5, vd.getJournal());
				ps.setString(6, vd.getAddress());
				ps.setObject(7, vd.getInserttime());
				ps.setString(8, vd.getBrief());
				ps.setString(9, vd.getKeyword());
				ps.setString(10, vd.getRefer_url());
				ps.setString(11, vd.getMd5());
				ps.setString(12, vd.getCite_url());
				ps.setInt(13, vd.getRefer_num());
				ps.setInt(14, vd.getCite_num());
				ps.setString(15, vd.getEmail());
				ps.setString(16, vd.getFund());
				ps.setString(17, vd.getCategory());
				return ps;
			}
		}, keyHolder);
//		vd.setWid(Integer.parseInt(StringUtil.extrator(keyHolder.getKeyList().get(0).toString(), "\\d")));
	}

	@Override
	public void updateMetaOrdata(String url, String table, String col) {
		String sql = "update "+table+" set flag=1 where "+col+"=?";
		this.jdbcTemplate.update(sql, new Object[]{url});
	}
	
	@Override
	public List<WebData> getNorepeatData(List<WebData> cdlist, String table) {
		Iterator<WebData> iter = cdlist.iterator();
		List<WebData> repeatDatas = new ArrayList<WebData>();
		while(iter.hasNext()){
			WebData cd = iter.next();
			if(!Systemconfig.urm.checkNoRepeat(cd.getMd5())) {
				iter.remove();
				repeatDatas.add(cd);
			}
		}
		
		return repeatDatas;
	}
	private int findId(String md5, String table) {
		String col = "id";
		String caluse = "md5";
		String sql = "select "+col+" from "+table+" where "+caluse+"=?";
		int id = 0;
		try {
			id = this.jdbcTemplate.queryForInt(sql, new Object[]{md5});
		} catch (Exception e) {
			id = 0;
		}
		return id;
	}

	@Override
	public void deleteReduplicationUrls(List<String> urlList, String table) {
		String sql = "select id from "+table+" where md5=?";
		String DELETE_SQL = "delete from "+ table +" where id=?";
		Iterator<String> urlIter = urlList.iterator();
		while(urlIter.hasNext()) {
			String url = urlIter.next();
			synchronized (urlList) {
				urlIter.remove();
			}
			List<Integer> idList = this.jdbcTemplate.queryForList(sql,new Object[]{url}, Integer.class);
			if(idList.size() > 1) {
				for(int i = idList.size()-2;i >= 0 ;i--) {//只留一条数据
					this.jdbcTemplate.update(DELETE_SQL, new Object[]{idList.get(i)});
				}
			}
		}
	}
	@Override
	public int getAllMd5(String table, Map<String, List<String>> map) {
		int num = 0;
		String SQL_WEB = "select md5 from "+ table;
		List<String> list = this.jdbcTemplate.queryForList(SQL_WEB, String.class);
		num += list.size();
		map.put(table, list);
		return num;
	}
	@Override
	public List<String> getAllTitle(String table) {
		String SQL_WEB = "select title from "+ table;
		List<String> list = this.jdbcTemplate.queryForList(SQL_WEB, String.class);
		return list;
	}
}
