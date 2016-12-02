package common.system;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;


import org.apache.log4j.Logger;

import common.down.FileCreateManager;
import common.down.LoggerManager;
import common.service.MysqlService;
import common.siteinfo.Siteinfo;
import common.util.UrlReduplicationRemove;

/**
 * 系统相关配置
 * @author grs
 * @since 0.5
 */
public class Systemconfig {

	/** 系统运行日志 */
	public static LoggerManager sysLog = new LoggerManager(Logger.getLogger("system"));
	/** 文件下载管理 */
	public static FileCreateManager fcManager = null;
	public static boolean createFile;
	public static boolean createPic;
	/** 注册的站点信息，包括各子站点，字符串标识 */
	public static Map<String, Siteinfo> allSiteinfos = new HashMap<String, Siteinfo>();
	/** 注册的下载类 */
	public static Map<String, String> siteHttpClass = new HashMap<String, String>();
	/** 注册的抽取类 */
	public static Map<String, String> siteExtractClass = new HashMap<String, String>();
	/**域名信息和站点标识*/
	public static Map<String, String> hostDomain = new HashMap<String, String>();
	/** 文件存储路径 */
	public static String filePath;
	
	public static String agentIp;
	public static int agentPort;
	
	public static MysqlService mysqlService;
	
	public static String keywords;
	public static boolean style;
	
	public static StringBuffer swinghtml = new StringBuffer();
	public static StringBuffer swinginfo = new StringBuffer();
	public static StringBuffer swingcomm = new StringBuffer();
	
	/**
	 * 初始化站点线程
	 */
	public static Map<String, ExecutorService> metaexec;
	public static Map<String, ExecutorService> dataexec;
	/**某个任务是否完成*/
	public static Map<String, Boolean> finish = new HashMap<String, Boolean>();
	public static UrlReduplicationRemove urm;
	public static String table;
	/**
	 * 配置加载完成后，系统初始化操作
	 */
	public void initial() {
		sysLog.start();
		fcManager =  new FileCreateManager(filePath);
		fcManager.start();
		metaexec = new HashMap<String, ExecutorService>();
		dataexec = new HashMap<String, ExecutorService>();
		
		urm = new UrlReduplicationRemove();
	}
	
	public void setSiteExtractClass(Map<String, String> sitesClassName) {
		Systemconfig.siteExtractClass = sitesClassName;
	}
	public void setSiteHttpClass(Map<String, String> siteDownClass) {
		Systemconfig.siteHttpClass = siteDownClass;
	}
	public void setFilePath(String filePath) {
		Systemconfig.filePath = filePath;
	}
	public void setAgentIp(String agentIp) {
		Systemconfig.agentIp = agentIp;
	}
	public void setAgentPort(int agentPort) {
		Systemconfig.agentPort = agentPort;
	}
	public void setCreateFile(boolean createFile) {
		Systemconfig.createFile = createFile;
	}
	public void setCreatePic(boolean createPic) {
		Systemconfig.createPic = createPic;
	}
	public void setMysqlService(MysqlService mysqlService) {
		Systemconfig.mysqlService = mysqlService;
	}
	public void setKeywords(String keywords) {
		Systemconfig.keywords = keywords;
	}
	public void setStyle(boolean style) {
		Systemconfig.style = style;
	}
	public void setTable(String table) {
		Systemconfig.table = table;
	}

}
