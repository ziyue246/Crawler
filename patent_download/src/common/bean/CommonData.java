package common.bean;

import java.util.Date;

public class CommonData {
	private int id;
	private String url;//private String htmlLink;
	private String title;
	private Date pubtime;
	private Date insertTime;
	private String md5;
	private String searchKeyword;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getPubtime() {
		return pubtime;
	}
	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public void print(){
		System.out.println("url        : " + this.getUrl());
		System.out.println("title      : " + this.getTitle());
		System.out.println("pubtime    : " + this.getPubtime().toLocaleString());
		System.out.println("MD5        : " + this.getMd5());
		System.out.println();
	}
	
}