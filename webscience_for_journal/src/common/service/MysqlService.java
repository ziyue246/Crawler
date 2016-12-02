package common.service;

import java.util.List;
import java.util.Map;

import common.bean.WebData;

/**
 * mysql数据库操作
 * @author Administrator
 *
 */
public interface MysqlService {

	/**
	 * 保存数据
	 * @param snmd
	 */
	public void saveDatas(List<WebData> list);
	
	public void saveData(WebData list);
	/**
	 * 更新table表flag数据状态
	 * @param url
	 * @param cols  列名
	 * @param table
	 */
	public void updateMetaOrdata(String url, String table, String col);

	public List<WebData> getNorepeatData(List<WebData> list, String table);
	
	public void deleteReduplicationUrls(List<String> url, String table);

	public int getAllMd5(String string, Map<String, List<String>> map);

	List<String> getAllTitle(String table);
	
}
