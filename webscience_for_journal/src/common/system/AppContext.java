package common.system;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;


import org.apache.log4j.PropertyConfigurator;
import org.apache.xpath.XPathAPI;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.siteinfo.Siteinfo;
import common.util.DOMUtil;
import common.util.StringUtil;

/**
 * 系统初始启动
 * @author grs
 *
 */
public class AppContext {

	public static ApplicationContext appCtx;

	/**
	 * 配置文件的加载及初始化方法
	 * 
	 * @param 配置文件路径
	 */
	public static void initAppCtx(String path) {
		PropertyConfigurator.configure(path + "./config/log4j.properties");
		File[] files = new File(path + "config").listFiles();
		ArrayList<String> list = new ArrayList<String>();
		for (File file : files) {
			if (file.getName().startsWith("app")) {
				list.add(path + "config" + File.separator
						+ file.getName());
			}
		}
		String[] arry = new String[list.size()];
		list.toArray(arry);
		appCtx = new FileSystemXmlApplicationContext(arry);
		
		readSiteConfig();
	}
	
	private static String filepath = "config" + File.separator+ "site";
	private static String xpath = "site";
	private static void readSiteConfig() {
		Map<String, String> map = new HashMap<String, String>();
		File[] xpathFs= new File(xpath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return !f.getName().replace(xpath, "").replace(File.separator, "").startsWith(".");
			}
		});
		for (File f : xpathFs) {
			map.put(f.getName(), StringUtil.getContent(f.getAbsolutePath()));
		}
		//读取简单配置后，处理详细配置
		File[] fs= new File(filepath).listFiles();
		for (File f : fs) {
			String name = f.getName();
			if (name.startsWith("app-") && name.endsWith(".xml")) {
				name = name.substring(4);
				if(map.containsKey(name)) {
					String content = StringUtil.getContent(f.getAbsolutePath());
					DOMUtil dom = new DOMUtil();
					Node domtree = dom.ini(map.get(name), "utf-8");
					NodeList nameList = null;
					NodeList valueList = null;
					try {
						nameList = XPathAPI.selectNodeList(domtree, "/SITE/PROP/@name");
						valueList = XPathAPI.selectNodeList(domtree, "/SITE/PROP/@value");
					} catch (TransformerException e) {
						e.printStackTrace();
					}
					for(int i = 0;i < nameList.getLength();i++) {
						content = content.replace("${"+nameList.item(i).getTextContent()+"}", filterCode(valueList.item(i).getTextContent()));
					}
					StringUtil.writeFile(f.getAbsolutePath()+".temp", content);
					
					loadDynamicBean(filepath + File.separator + f.getName()+".temp");
				}
			}
		}
	}
	private static String filterCode(String str) {
		return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("“", "&quot;");
	}
	
	public static synchronized void loadDynamicBean(String configLocationString) {
		System.out.println("ini:"+configLocationString);
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
				((BeanDefinitionRegistry)  ((ConfigurableApplicationContext)appCtx).getBeanFactory()));
		beanDefinitionReader.setResourceLoader(appCtx);
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(
				appCtx));
		try {
			Resource[] resources = appCtx.getResources(configLocationString);
			beanDefinitionReader.loadBeanDefinitions(resources);
			String substring = configLocationString.substring(configLocationString.lastIndexOf("app-")+4, configLocationString.indexOf(".xml"));
			
			Siteinfo si=(Siteinfo)(appCtx.getBean(substring));
			//验证站点信息数据是否完整,成功后添加站点
			Systemconfig.allSiteinfos.put(si.getSiteName(),si);
			
			File f = new File(configLocationString);
			if(!f.delete()) {
				System.out.println(f+"没有被删除");
			}
			
			System.out.println("系统初始化站点："+ si.getSiteName());
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
