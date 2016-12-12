/*
 * @(#)Page.java   	2013-3-27 下午03:45:58
 *
 * Copyright 2011 Socialmedia, Inc. All rights reserved.
 * CAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package refer.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>
 * @CLASS			:	Page
 * @DESCRIPTION	:	
 * @AUTHOR			:	lvhongqiang
 * @VERSION			:	v1.0
 * @DATE			:	2013-3-27 下午03:45:58            
 * </p>   
 *
 */
public class Page implements Serializable {
	private int  total;  //必须设定
    private int  pageSize;  //必须设定
    private int  totalPage;  
    private int  pageNo; //必须设定
    private int  prePage;  
    private int  nextPage;  
    private List list; //必须设定
    //页面的开始索引和结束索引
    private int  start;
    private int  end;
    private List<Integer>range;//页面索引区间
	public List<Integer> getRange() {
		List<Integer>result=new ArrayList<Integer>();
		for (int i = start; i <=end; i++) {
			result.add(i);
		}
		return result;
	}
    
    private String queryHql;
    
    public Page(){
    	pageNo=1;
    	pageSize=20;
    }
    public Page(Integer pageNo, Integer pageSize){
    	this.pageNo=pageNo;
    	this.pageSize=pageSize;
    }
    
    /**
     * 将多个Page对象变为�?��
     * @param pages
     * @return
     */
    public static Page combinePages(Page...pages)
    {
    	//初始�?
    	Page resultPage = new Page();
    	resultPage.setList(new ArrayList());
    	int _total = 0;
    	int _start = 1;
    	int _nextPage = 2;
    	int _prePage = 1;
    	int _totalPage = 0;
    	//变更参数
    	for(Page i : pages)
    	{
    		resultPage.getList().addAll(i.getList());
    		_total += i.getTotal();
    	}
    	//封装结果
    	if(resultPage.getPageSize() != 0)
    	{ 
	    	if(_total % resultPage.getPageSize() == 0)
	    	{  
	    		_totalPage = _total / resultPage.getPageSize();  
	        }
	    	else
	    	{  
	    		_totalPage = _total / resultPage.getPageSize(); 
	        }
    	}
    	resultPage.setEnd(_totalPage);
    	resultPage.setNextPage(_nextPage);
    	resultPage.setPrePage(_prePage);
    	resultPage.setTotal(_total);
    	resultPage.setTotalPage(_totalPage);
		return resultPage;
    }

	public int getStart() {
		return start;
	}
    //设置�?��索引 参数start 即当前页
	public void setStart(int start) {
		if(getTotalPage() <= 10){
			this.start = 1;
		}
		else {
			this.start = start - 5;
			if(this.start>this.totalPage-10)
				this.start=this.totalPage-10;
			if(this.start < 1){
				this.start = 1;
			}
		}
	}
	public int getEnd() {
		return end;
	}
	//设置结束索引 参数end 即当前页
	public void setEnd(int end) {
		if(getTotalPage() <= 10) {
			this.end = getTotalPage();
		}
		else {
			this.end = end + 4;
			if(this.end<10)
				this.end=10;
			if(this.end > getTotalPage()) {
				this.end = getTotalPage();
			}
		}
	}
	public int getTotal() {  
        return total;  
    }  
	/**
	 * 设定总记录数目，同时设定totalPage
	 * (pageSize必须先设定）
	 * 
	 */
    public void setTotal(int total) {  
    	if(pageSize!=0){ 
	    	if(total%pageSize == 0){  
	             totalPage = total/pageSize;  
	         }else{  
	             totalPage = total/pageSize + 1;  
	         }
    	}
    	this.total = total;  
    }  
    public int getPageSize() {  
        return pageSize;  
    }  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
    public int getTotalPage() {  
    	if(pageSize!=0){ 
	    	if(total%pageSize == 0){  
	             totalPage = total/pageSize;  
	         }else{  
	             totalPage = total/pageSize + 1;  
	         }
    	}
        return totalPage;  
    }  
    public void setTotalPage(int totalPage) {  
        this.totalPage = totalPage;  
    }  
    public int getPageNo() {  
        return pageNo;  
    }  
    /**
     * 设置当前页，并自动计算开始索引和结束索引
     * （totalPage必须先设定）
     */
    public void setPageNo(int pageNo) {  
        this.pageNo = pageNo; 
        //设置�?��索引
        if(getTotalPage() <= 10){
			this.start = 1;
		}
		else {
			this.start = this.pageNo - 5;
		}
        //设置结束索引
        if(getTotalPage() <= 10) {
			this.end = getTotalPage();
		}
		else {
			this.end = this.pageNo + 4;
		}
        //�?��、结束索引修�?
		if(this.start < 1){
			this.end+=1-this.start;
			this.start = 1;
		}
		if(this.end > getTotalPage()) {
			this.start-=this.end-getTotalPage();
			this.end = getTotalPage();
		}
        
        //下一�?
        if(pageNo == totalPage){  
            this.nextPage = pageNo;  
        }else{  
            this.nextPage = pageNo + 1;  
        }  
        //上一�?
        if(pageNo == 1){  
            this.prePage = pageNo;  
        }else{  
            this.prePage = pageNo - 1;  
        }  
    }  
    public int getPrePage() {  
        return prePage;  
    }  
    public void setPrePage(int prePage) {  
        this.prePage = prePage;  
    }  
    public int getNextPage() {  
        return nextPage;  
    }  
    public void setNextPage(int nextPage) {  
        this.nextPage = nextPage;  
    }  
    public List getList() {  
        return list;  
    }  
    public void setList(List list) {  
        this.list = list;  
    }
	public String getQueryHql() {
		return queryHql;
	}
	public void setQueryHql(String queryHql) {
		this.queryHql = queryHql;
	}  
}
