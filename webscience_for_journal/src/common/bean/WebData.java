package common.bean;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class WebData implements Serializable {
	private int wid;
	private String title;
	private String url;
	private String author;
	private Date pubtime;
	private String pubyear;
	private Date inserttime;
	private String brief;
	private String md5;
	private String sitename;
	private String refer_url;
	private String cite_url;
	private int refer_num;
	private int cite_num;
	private int site_id;
	private String keyword;
	private String email;
	private String fund;
	private String category;
	private String journal;
	private String source;
	private String address;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int vid) {
		this.wid = vid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String sourceurl) {
		this.url = sourceurl;
	}
	public String getPubyear() {
		return pubyear;
	}
	public void setPubyear(String pubyear) {
		this.pubyear = pubyear;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPubtime() {
		return pubtime;
	}
	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String urlMd5) {
		md5 = urlMd5;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int siteId) {
		site_id = siteId;
	}
	public String getRefer_url() {
		return refer_url;
	}
	public void setRefer_url(String refer_url) {
		this.refer_url = refer_url;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCite_url() {
		return cite_url;
	}
	public void setCite_url(String cite_url) {
		this.cite_url = cite_url;
	}
	public int getRefer_num() {
		return refer_num;
	}
	public void setRefer_num(int refer_num) {
		this.refer_num = refer_num;
	}
	public int getCite_num() {
		return cite_num;
	}
	public void setCite_num(int cite_num) {
		this.cite_num = cite_num;
	}
}
