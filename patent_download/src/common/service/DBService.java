package common.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import common.bean.CommonData;

/**
 * mysql数据库操作
 *
 * @author Administrator
 */
public interface DBService<T> {

    /**
     * 保存数据
     *
     * @param list
     * @throws IOException
     */
    public void saveDatas(List<T> list) throws IOException;

    public void saveData(T t) throws IOException;

    /**
     * 删除表中重复的数据
     *
     * @param url
     * @param table
     */
    public void deleteReduplicationUrls(List<String> url, String table);

    /**
     * 获得表中的md5
     *
     * @param string
     * @param map
     * @return
     */
    public int getAllMd5(String string, Map<String, List<String>> map);

    /**
     * 处理异常数据
     *
     * @param md5
     * @param table
     */
    public void exceptionData(String md5, String table);

    /**
     * 过滤重复数据
     *
     * @param list
     * @param table
     * @return
     */
    List<? extends CommonData> getNorepeatData(List<? extends CommonData> list, String table);



    /**
     * 根据crawlerType过滤，获得采集类型配置
     *
     * @return
     */
    String getTypeConfig();


    void updateUserOrder(String userName);



    /**
     * 更新代理使用时间
     *
     * @param proxyInfo: ip:port:domainId
     */
    public void updateProxyOrder(String proxyInfo);



    public void saveCommentDatas(List<T> list) throws IOException;

    public void saveCommentData(T t) throws IOException;
}