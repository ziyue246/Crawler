/*
 * @(#)BaseDaoImpl.java   	2014-4-28 下午03:30:22
 *
 * Copyright 2011 Socialmedia, Inc. All rights reserved.
 * CAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package refer.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>
 * @CLASS			:	BaseDaoImpl
 * @DESCRIPTION	:	
 * @AUTHOR			:	lvhongqiang
 * @VERSION			:	v1.0
 * @DATE			:	2013-3-27 下午03:30:22            
 * </p>   
 *
 */

@Repository
public class BaseDao {
	
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 通过Id获取信息
	 * @description	
	 * @author  JiaCao
	 * @param clazz
	 * @param id
	 * @return  
	 */
	
	public Object get(Class clazz, Integer id) {    
	    try {   
	        Session session = getSession();   
	        return session.get(clazz, id);
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	

	public Object load(Class clazz, Integer id) {   
	    try {   
	        Session session = getSession();   
	        return session.load(clazz, id);
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}



	/**
	 * 保存对象
	 * @description	
	 * @author  JiaCao
	 * @param obj
	 * @return  
	 */
	
	public void save(Object obj) {        
	    try {   
	        Session session = getSession();   
	        session.save(obj); 
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	
	public void saveOrUpdate(Object obj){    
	    try {   
	        Session session = getSession();   
	        session.saveOrUpdate(obj); 
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 更新
	 * @description	
	 * @author  JiaCao
	 * @param obj  
	 */
	
	public void update(Object obj) {    
	    try {   
	        Session session = getSession();   
	        session.update(obj); 
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 删除对象
	 * @description	
	 * @author  JiaCao
	 * @param obj  
	 */
	
	public void delete(Object obj) {    
	    try {   
	        Session session = getSession();   
	        session.delete(obj); 
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 使用HQL语句进行查询
	 * @param hql 
	 * @return list
	 */
	public List find(final String hql) {   
	    try {   
	        Session session = getSession();   
	        return session.createQuery(hql).list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 使用HQL语句进行查询
	 * @param hql 
	 * @param value 参数
	 * @return list
	 */
	public List find(final String hql , final Object value){
	    try {   
	        Session session = getSession();   
	        return session.createQuery(hql).setParameter(0, value).list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 使用HQL语句进行查询.
	 * 
	 * @param hql 
	 * @param values 参数数组
	 * @return list
	 */
	public List find(final String hql, final Object[] values)
	{
	    try {   
	        Session session = getSession();   
	        Query query = session.createQuery(hql);
	        for(int i=0;i<values.length;i++){
	        	query.setParameter(i, values[i]);
	        }
	        return query.list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 使用HQL语句进行分页查询
	 * @param hql HQL语句
	 * @param offset 第一条记录索引位�?
	 * @param pageSize 每页记录�?
	 * @return 当前页的list
	 */
	public List findList(final String hql, 
		final int offset, final int pageSize)
	{
	    try {   
	        Session session = getSession();   
	        return session.createQuery(hql)
	        		.setFirstResult(offset)
	        		.setMaxResults(pageSize)	        		
	        		.list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}

	/**
	 * 使用HQL语句进行分页查询
	 * @param hql HQL语句
	 * @param offset 第一条记录索引位�?
	 * @param value 参数
	 * @param pageSize 每页记录�?
	 * @return 当前页的list
	 */
	public List findList(final String hql , final Object value ,
		final int offset, final int pageSize)
	{
	    try {   
	        Session session = getSession();   
	        return session.createQuery(hql)
	        		.setParameter(0, value)
	        		.setFirstResult(offset)
	        		.setMaxResults(pageSize)	        		
	        		.list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}

	/**
	 * 使用HQL语句进行分页查询
	 * @param hql HQL语句
	 * @param offset 第一条记录索引位�?
	 * @param values 参数数组
	 * @param pageSize 每页记录�?
	 * @return 当前页的list
	 */
	public List findList(final String hql, final Object[] values,
		final int offset, final int pageSize)
	{
	    try {   
	        Session session = getSession();   
	        Query query = session.createQuery(hql);
	        for(int i=0;i<values.length;i++){
	        	query.setParameter(i, values[i]);
	        }
	        return query.setFirstResult(offset)
		        		.setMaxResults(pageSize)	        		
		        		.list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	

	/**
	 * 使用HQL语句进行分页查询
	 * @param hql HQL语句
	 * @param offset 第一条记录索引位�?
	 * @param name where in参数�?
	 * @param vals where in括号内容
	 * @param pageSize 每页记录�?
	 * @return 当前页的list
	 */
	public List findList(final String hql , final String name, final List vals ,
		final int offset, final int pageSize)
	{
	    try {   
	        Session session = getSession();   
	        return session.createQuery(hql).setParameterList(name, vals).list();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	
	
	
	/**
	 * Execute page.
	 * 
	 * @param queryHql the query hql
	 * @param countHql the count hql
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * 
	 * @return the page
	 */
	public Page findPage(String queryHql, String countHql, 
			int pageNo, int pageSize) {
		int total = 0;
		pageNo = pageNo > 0 ? pageNo : 1;
		pageSize = pageSize > 0 && pageSize < 100 ? pageSize : 20;
		total = this.countHql(countHql);
		if ( (pageNo-1) * pageSize > total) {
			pageNo = 1;
		}
		List list = this.findList(queryHql, (pageNo - 1) * pageSize, pageSize);
		//页面bean
		Page page = new Page();
		//封装page
		page.setList(list);
		page.setPageSize(pageSize);
		page.setTotal(total);//设定总记录数，同时设定totalPage（pageSize必须先设定）
		page.setPageNo(pageNo);//设定当前页，同时设定start �?end，上�?��和下�?��（total必须已经设定�?
		
		return page;
	}
	
	/**
	 * Execute page.
	 * 
	 * @param queryHql the query hql
	 * @param countHql the count hql
	 * @param value the value
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * 
	 * @return the page
	 */
	public Page findPage(String queryHql, String countHql, Object value,
			int pageNo, int pageSize) {
		int total = 0;
		pageNo = pageNo > 0 ? pageNo : 1;
		pageSize = pageSize > 0 && pageSize < 100 ? pageSize : 20;
		List list = null;
		//参数为空
		if(value == null) {
			total = this.countHql(countHql);
			if ( (pageNo-1) * pageSize > total) {
				pageNo = 1;
			}
			list = this.findList(queryHql, (pageNo-1)*pageSize, pageSize);
		} else {
			total = this.countHql(countHql, value);
			if ( (pageNo-1) * pageSize > total) {
				pageNo = 1;
			}
			list = this.findList(queryHql, value, (pageNo-1)*pageSize, pageSize);
		}
		//页面bean
		Page page = new Page();
		//封装page
		page.setList(list);
		page.setPageSize(pageSize);
		page.setTotal(total);//同时设定totalPage（pageSize必须先设定）
		page.setPageNo(pageNo);//同时设定start �?�?�?7 end，上�?�?�?7页和下一页（total必须已经设定�?�?�?7
		
		return page;
	}
	/**
	 * Execute page.
	 * 
	 * @param queryHql the query hql
	 * @param countHql the count hql
	 * @param values the values
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * 
	 * @return the page
	 */
	public Page findPage(String queryHql, String countHql, Object[] values,
			int pageNo, int pageSize) {
		int total = 0;
		pageNo = pageNo > 0 ? pageNo : 1;
		pageSize = pageSize > 0 && pageSize < 100 ? pageSize : 20;
		List list = null;
		//参数为空
		if(values == null) {
			total = this.countHql(countHql);
			if ( (pageNo-1) * pageSize > total) {
				pageNo = 1;
			}
			list = this.findList(queryHql, (pageNo-1)*pageSize, pageSize);
		} else {
			total = this.countHql(countHql, values);
			if ( (pageNo-1) * pageSize > total) {
				pageNo = 1;
			}
			list = this.findList(queryHql, values, (pageNo-1)*pageSize, pageSize);
		}
		//页面bean
		Page page = new Page();
		//封装page
		page.setList(list);
		page.setPageSize(pageSize);
		page.setTotal(total);//同时设定totalPage（pageSize必须先设定）
		page.setPageNo(pageNo);//同时设定start �?�?�?7 end，上�?�?�?7页和下一页（total必须已经设定�?�?�?7
		
		return page;
	}

	
	/**
	 * Execute page.
	 * 
	 * @param queryHql the query hql
	 * @param countHql the count hql
	 * @param value the value
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * 
	 * @return the page
	 */
	public Page findPage(String queryHql, String countHql, final String name, final List vals ,
			int pageNo, int pageSize) {
		int total = 0;
		pageNo = pageNo > 0 ? pageNo : 1;
		pageSize = pageSize > 0 && pageSize < 100 ? pageSize : 20;
		List list = null;
		//参数为空
		if(vals == null) {
			total = this.countHql(countHql);
			if ( (pageNo-1) * pageSize > total) {
				pageNo = 1;
			}
			list = this.findList(queryHql, (pageNo-1)*pageSize, pageSize);
		} else {
			total = this.countHql(countHql, name,vals);
			if ( (pageNo-1) * pageSize > total) {
				pageNo = 1;
			}
			list = this.findList(queryHql, name,vals, (pageNo-1)*pageSize, pageSize);
		}
		//页面bean
		Page page = new Page();
		//封装page
		page.setList(list);
		page.setPageSize(pageSize);
		page.setTotal(total);//同时设定totalPage（pageSize必须先设定）
		page.setPageNo(pageNo);//同时设定start �?�?�?7 end，上�?�?�?7页和下一页（total必须已经设定�?�?�?7
		
		return page;
	}
	
	/**
	 * 根据count(*)没有参数的hql语句，获取所有记录数
	 * 
	 * @param hql the hql
	 * 
	 * @return the toatal by hql
	 */
	public int countHql(String hql)
	{
		//查询
		Query query= getSession().createQuery(hql);
		Long result = (Long)query.uniqueResult();
		if (result != null) {
			return result.intValue();
		}
		return 0;
	}
	/**
	 * 根据count(*)有一个参数的hql语句，获取所有记录数
	 * 
	 * @param hql the hql
	 * 
	 * @return the toatal by hql
	 */
	public int countHql(String hql, Object value)
	{
		//查询
		Query query= getSession().createQuery(hql);
		//参数赋�?
		query.setParameter(0, value);   
		Long result = (Long)query.uniqueResult();
		if (result != null) {
			return result.intValue();
		}
		return 0;
		//return total;
	}
	/**
	 * 根据count(*)多个参数的hql语句，获取所有记录数
	 * 
	 * @param hql the hql
	 * 
	 * @return the toatal by hql
	 */
	public int countHql(String hql, Object[] values)
	{
		//查询
		Query query= getSession().createQuery(hql);
		//参数赋�?
		if(values != null && values.length > 0) {
			for(int i=0;i<values.length;i++)
				query.setParameter(i, values[i]);
		}
		Long result = (Long)query.uniqueResult();
		if (result != null) {
			return result.intValue();
		}
		return 0;
	}

	/**
	 * 根据count(*)有一个where in参数的hql语句，获取所有记录数
	 * 
	 * @param hql the hql
	 * 
	 * @return the toatal by hql
	 */
	public int countHql(String hql, String name, List vals)
	{
		//查询
		Query query= getSession().createQuery(hql);
		//参数赋�?
		query.setParameterList(name,vals);   
		Long result = (Long)query.uniqueResult();
		if (result != null) {
			return result.intValue();
		}
		return 0;
		//return total;
	}	
	
	/**
	 * 执行无参数的hql
	 * @description	
	 * @author  JiaCao
	 * @param hql  
	 */
	
	@SuppressWarnings("unchecked")
	public void executeHql(final String hql) {
	    try {   
	        Session session = getSession();   
	        session.createQuery(hql).executeUpdate();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	
	/**
	 * 执行带一个参数的hql
	 * @description	
	 * @author  JiaCao
	 * @param hql
	 * @param value  
	 */
	
	@SuppressWarnings("unchecked")
	public void executeHql(final String hql, final Object value) {
	    try {   
	        Session session = getSession();   
	        session.createQuery(hql)
			        .setParameter(0, value)
			        .executeUpdate();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	/**
	 * 执行带参数数组的hql
	 * @description	
	 * @author  JiaCao
	 * @param hql
	 * @param values  
	 */
	@SuppressWarnings("unchecked")
	public void executeHql(final String hql, final Object[] values) {
	    try {   
	        Session session = getSession();   
			Query query = session.createQuery(hql);
			for (int i = 0;i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
			query.executeUpdate();
	    } catch (RuntimeException re) {  
	        throw re;  
	    } 
	}
	

	public List search(String queryHql, Object[][] filter){
		return search(queryHql, filter, null, null, null, null);
	}
	public List search(String queryHql, Object[][] filter, String sort,
			String dir, Integer offset, Integer length) {
		List<Object> values=new ArrayList<Object>();
		String hql = buildSql(queryHql, filter, sort, dir, values);
		if(length==null){
			return find(hql, values.toArray());
		}else{
			if(offset==null)offset=0;
			return findList(hql,values.toArray(), offset, length);
		}
	}

	public Page searchPage(String queryHql, String countHql, Object[][] filter,
			String sort, String dir, Integer pageNo, Integer pageSize) {
		List<Object> values=new ArrayList<Object>();
		String queryhql = buildSql(queryHql, filter, sort, dir, values);
		String counthql = buildSql(countHql, filter, null, null, null);
		Page page=findPage(queryhql, counthql, values.toArray(), pageNo, pageSize);
		return page;
	}

	public Integer searchCount(String countHql, Object[][] filter) {
		List<Object> values=new ArrayList<Object>();
		String hql = buildSql(countHql, filter, null, null, values);
		return countHql(hql, values.toArray());
	}
	/**
	 * 根据filtermap补全queryhql的where条件
	 * @param queryhql
	 * @param filtermap
	 * @param sort
	 * @param dir
	 * @param values
	 * @return
	 */
	public String buildSql(String queryhql, Object[][] filtermap,
			String sort, String dir, List<Object> values) {
		String hql=queryhql;
		if(!hql.toLowerCase().contains("where")){
			hql+=" where 1=1 ";
		}
		if(filtermap!=null){
			for (Object[] map : filtermap) {
				if(map[1]!=null&&!"".equals(map[1])){
					hql+=" and "+map[0]+"? ";
					if(values != null)
					{	
						values.add(map[1]);
					}
				}
			}
		}
		if(sort!=null&&sort.length()>0){
			hql+=" order by "+sort;
			if(dir!=null&&dir.length()>0){
				hql+=" "+dir;
			}
		}
		return hql;
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
