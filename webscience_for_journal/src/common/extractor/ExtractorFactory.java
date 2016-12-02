package common.extractor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.TransformerException;


import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.bean.WebData;
import common.system.Systemconfig;


/**
 * 抽取工厂，获取实际的站点抽取类
 * @author grs
 *
 */
public class ExtractorFactory {

	private static Object obj = new Object();
	private final static ConcurrentHashMap<String, XpathExtractor> map = new ConcurrentHashMap<String, XpathExtractor>(Systemconfig.siteExtractClass.size());
	/**
	 * 利用注册的抽取类名称反射得到相应实例
	 * @param name
	 * @return
	 */
	public static XpathExtractor createExtractor(String siteFlag) {
		int in = siteFlag.indexOf("_");
		if(in==-1) in = siteFlag.length();
		String site = siteFlag.substring(0, in);
		XpathExtractor be = null;
		synchronized (obj) {
			if(map.containsKey(site)) {
				be = map.get(site);
			} else {
				try {
					be = (XpathExtractor) Class.forName(Systemconfig.siteExtractClass.get(site)).newInstance();
					map.putIfAbsent(site, be);
				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
					//"未找到注册类，更新后处理"
				} catch (InstantiationException e) {
//					e.printStackTrace();
					//注册类实例化异常
				} catch (IllegalAccessException e) {
//					e.printStackTrace();
					//非法访问注册类
				} catch (Exception e) {
					
				}
			}
		}
		if(be==null)
			be = new XpathExtractor();
		return be;
	}
	/**
	 * 利用反射获得设定值
	 * @param key 关键词，用于获得方法名
	 * @param mod 数据对象
	 * @param siteFlag 站点标识
	 * @param args 使用的参数
	 * @return
	 */
	public Object reflect(String key, WebData mod, String[] args) {
		XpathExtractor be = createExtractor(args[0]);
		String methodName = getMethodName(key);
		try {
			Method method = be.getClass().getMethod(methodName, WebData.class, String[].class);
			return method.invoke(be, mod, args);
		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
		} catch (SecurityException e) {
//			e.printStackTrace();
		} catch (IllegalAccessException e) {
//			e.printStackTrace();
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
		} catch (InvocationTargetException e) {
//			e.printStackTrace();
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 通用处理获得数据
	 * @param key
	 * @param domtree
	 * @return
	 */
	public List<String> getNodeList(String xpath, Node domtree) {
		List<String> list = new ArrayList<String>();
		NodeList nl = null;
		try {
			nl = XPathAPI.selectNodeList(domtree, xpath);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		for(int i =0;i < nl.getLength();i++) {
			list.add(nl.item(i).getTextContent());
		}
		return list;
	}
	/**
	 * 反射获得数据
	 * @param key
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getNodeList(String key, Object... objs) {
		
		check(objs);
		//利用站点标识得到类的全名
		boolean success = false;
		String methodName = getMethodName(key);
		List<String> list = null;
		try {
			XpathExtractor be = createExtractor((String)objs[2]);
			Method method = null;
			if(objs.length==3) {
				method = be.getClass().getMethod(methodName, String.class, Node.class, String.class);
				list = (List<String>) method.invoke(be, objs[0], objs[1], objs[2]);
			} else if(objs.length==4) {
				method = be.getClass().getMethod(methodName, String.class, Node.class, String.class, String[].class);
				list = (List<String>) method.invoke(be, objs[0], objs[1], objs[2], objs[3]);
			}
			success = true;
		} catch (NoSuchMethodException e) {
			
		} catch (SecurityException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (IllegalArgumentException e) {
			
		} catch (InvocationTargetException e) {
			
		} catch (NullPointerException e) {
			
		}
		//吞入异常后，使用通用抽取策略
		if(!success) {
			list = getNodeList((String) objs[0], (Node) objs[1]);
		}
		return list;
	}
	/*
	 * 反射方法名
	 */
	private static String getMethodName(String key) {
		String name = null;
		int fg = key.indexOf("_");
		if(fg!=-1) {
			name = "parse" + key.substring(0,1).toUpperCase() + key.substring(1, fg) + 
					key.substring(fg+1, fg+2).toUpperCase()+key.substring(fg+2);
		} else
			name = "parse" + key.substring(0,1).toUpperCase() + key.substring(1);
		return name;
	}
	/*
	 * 检查参数
	 */
	private void check(Object... objs) {
		if(objs.length<3) {
			throw new IllegalArgumentException("请求参数数量不匹配！！");
		} else if(!(objs[0] instanceof String) ||
				!(objs[1] instanceof Node) || !(objs[2] instanceof String)) {
			throw new IllegalArgumentException("请求参数类型不匹配！！");
		}
	}
	
}
