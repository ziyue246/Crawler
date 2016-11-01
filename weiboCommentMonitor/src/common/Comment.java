package common;

import java.util.Date;

public class Comment {
	private int id;
	private String url;
	private Date pubtime;
	private Date insertTime;
	private String md5;
	private int userId;
	private int commentCount;
	private int rttCount;
	private String mid;
	private String commentUrl;
	private String rttUrl;
	private String author;
	private String authorUrl;
	private String searchKeyword;
	private int categoryCode;
	private String authorImg;
	private String content;
	private String source;
	private int siteId;
	private String imgUrl;
	private int reliability;
	private String gps;
	private int likeCount;
	private int po_score;
	private String words;
	/*
	NUMBER	ID
	VARCHAR2	URL
	TIMESTAMP(6)	PUBTIME
	TIMESTAMP(6)	INSERT_TIME
	VARCHAR2	MD5
	NUMBER	USER_ID
	NUMBER	COMMENT_COUNT
	NUMBER	RTT_COUNT
	VARCHAR2	MID
	VARCHAR2	COMMENT_URL
	VARCHAR2	RTT_URL
	VARCHAR2	AUTHOR
	VARCHAR2	AUTHOR_URL
	VARCHAR2	SEARCH_KEYWORD
	NUMBER	CATEGORY_CODE
	VARCHAR2	AUTHOR_IMG
	CLOB	CONTENT
	VARCHAR2	SOURCE
	NUMBER	SITE_ID
	CLOB	IMG_URL
	NUMBER	RELIABILITY
	VARCHAR2	GPS
	NUMBER	LIKE_COUNT
	NUMBER	PO_SCORE
	VARCHAR2	WORDS
*/
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getRttCount() {
		return rttCount;
	}
	public void setRttCount(int rttCount) {
		this.rttCount = rttCount;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getCommentUrl() {
		return commentUrl;
	}
	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}
	public String getRttUrl() {
		return rttUrl;
	}
	public void setRttUrl(String rttUrl) {
		this.rttUrl = rttUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public int getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getAuthorImg() {
		return authorImg;
	}
	public void setAuthorImg(String authorImg) {
		this.authorImg = authorImg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getReliability() {
		return reliability;
	}
	public void setReliability(int reliability) {
		this.reliability = reliability;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getPo_score() {
		return po_score;
	}
	public void setPo_score(int po_score) {
		this.po_score = po_score;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	
}
