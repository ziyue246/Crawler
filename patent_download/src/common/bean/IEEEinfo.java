package common.bean;

import java.util.Date;

public class IEEEinfo extends CommonData{
	private int id;
	private String url;//private String htmlLink;
	private String title;
	private String authors;
	private String authorsids;
	private String doi;
	private Date pubtime;//
	private String journal;// "publicationTitle":"2006 Annual IEEE India Conference",
	private Date insertTime;
	private String brief;
	private String keywords;//
	//http://ieeexplore.ieee.org/document/4086280/references?ctx=references
	private String referUrl;	
	private String md5;
	//http://ieeexplore.ieee.org/document/4086280/citations?ctx=citations
	private String citeUrl;
	private int referNum;//
	private int citeNum;// "citationCount":2,
	private String category;//"articleContentType":"Conference Publications"
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
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getAuthorsids() {
		return authorsids;
	}
	public void setAuthorsids(String authorsids) {
		this.authorsids = authorsids;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public Date getPubtime() {
		return pubtime;
	}
	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getReferUrl() {
		return referUrl;
	}
	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getCiteUrl() {
		return citeUrl;
	}
	public void setCiteUrl(String citeUrl) {
		this.citeUrl = citeUrl;
	}
	public int getReferNum() {
		return referNum;
	}
	public void setReferNum(int referNum) {
		this.referNum = referNum;
	}
	public int getCiteNum() {
		return citeNum;
	}
	public void setCiteNum(int citeNum) {
		this.citeNum = citeNum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	
	public void print(){
		System.out.println("authors    : " + this.getAuthors());
		System.out.println("authorsids : " + this.getAuthorsids());
		System.out.println("url        : " + this.getUrl());
		System.out.println("referUrl   : " + this.getReferUrl());
		System.out.println("referNum   : " + this.getReferNum());
		System.out.println("citeUrl    : " + this.getCiteUrl());
		System.out.println("citeNum    : " + this.getCiteNum());
		System.out.println("category   : " + this.getCategory());
		System.out.println("doi        : " + this.getDoi());
		System.out.println("title      : " + this.getTitle());
		System.out.println("keyword    : " + this.getKeywords());
		System.out.println("pubtime    : " + this.getPubtime().toLocaleString());
		System.out.println("MD5        : " + this.getMd5());
		System.out.println();
	}
	
}