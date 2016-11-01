package common;

import java.util.Date;

public class WeiboDataProcessed {
	

	
	
	
//	AUTHOR	VARCHAR2
//	AUTHOR_IMG	VARCHAR2
//	AUTHOR_URL	VARCHAR2
//	CATEGORY1	NUMBER
//	CATEGORY2	NUMBER
//	CATEGORY3	NUMBER
//	CATEGORY_CODE	VARCHAR2
//	COLLECTED_NUM	NUMBER
//	COMMENT_NUM	NUMBER
//	COMMENT_URL	VARCHAR2
//	CONTENT	CLOB
//	EMOTION_SCORE	NUMBER
//	ENTITIES	VARCHAR2
//	GPS_INFO	VARCHAR2



	private int id;
	private String author;
	private String authorImg;
	private String authorUrl;
	private int category1;
	private int category2;
	private int category3;
	private String categoryCode;
	
	private int collectedNum;
	
	private int commentNum;
	private String commentUrl;
	private String content;
	
	private int emotionScore;//EMOTION_SCORE
	private String entities;//ENTITIES
	private String GPSinfo;
	
	
	
	private int hotIndex;//	HOT_INDEX	NUMBER
	private String imgUrl;// IMG_URL	VARCHAR2
	private Date insertTime;//	INSERT_TIME	TIMESTAMP(6)
	private int isJunk;//	IS_JUNK	NUMBER
	private String keywords;//	KEYWORDS	VARCHAR2
	private int likeCount;//	LIKE_COUNT	NUMBER
	private String md5;//	MD5	VARCHAR2
	private int mediaType;//	MEDIA_TYPE	NUMBER
	private String mid;//	MID	VARCHAR2
	private int originalId;//	ORIGINAL_ID	NUMBER
	private String posiInfo;//	POSI_INFO	VARCHAR2
	private int posiType;//	POSI_TYPE	NUMBER
	private Date pubtime;//	PUBTIME	TIMESTAMP(6)
	
	private int reliability;//	RELIABILITY	NUMBER
	private String road;//	ROAD	VARCHAR2
	private int rttNum;//	RTT_NUM	NUMBER
	private String rttUrl;//	RTT_URL	VARCHAR2
	private String searchKeyword;//	SEARCH_KEYWORD	VARCHAR2
	private String sentiWords;//	SENTI_WORDS	VARCHAR2
	private int siteId;//	SITE_ID	NUMBER
	private String source;//	SOURCE	VARCHAR2
	private String target;//	TARGET	VARCHAR2
	private String url;//	URL	VARCHAR2
	private int userId;//	USER_ID	NUMBER

	private int warnLevel;//	WARN_LEVEL	NUMBER
	private String warnWord;//	WARN_WORD	VARCHAR2
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorImg() {
		return authorImg;
	}
	public void setAuthorImg(String authorImg) {
		this.authorImg = authorImg;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public int getCategory1() {
		return category1;
	}
	public void setCategory1(int category1) {
		this.category1 = category1;
	}
	public int getCategory2() {
		return category2;
	}
	public void setCategory2(int category2) {
		this.category2 = category2;
	}
	public int getCategory3() {
		return category3;
	}
	public void setCategory3(int category3) {
		this.category3 = category3;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public int getCollectedNum() {
		return collectedNum;
	}
	public void setCollectedNum(int collectedNum) {
		this.collectedNum = collectedNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public String getCommentUrl() {
		return commentUrl;
	}
	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getEmotionScore() {
		return emotionScore;
	}
	public void setEmotionScore(int emotionScore) {
		this.emotionScore = emotionScore;
	}
	public String getEntities() {
		return entities;
	}
	public void setEntities(String entities) {
		this.entities = entities;
	}
	public String getGPSinfo() {
		return GPSinfo;
	}
	public void setGPSinfo(String gPSinfo) {
		GPSinfo = gPSinfo;
	}
	public int getHotIndex() {
		return hotIndex;
	}
	public void setHotIndex(int hotIndex) {
		this.hotIndex = hotIndex;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public int getIsJunk() {
		return isJunk;
	}
	public void setIsJunk(int isJunk) {
		this.isJunk = isJunk;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public int getMediaType() {
		return mediaType;
	}
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getOriginalId() {
		return originalId;
	}
	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}
	public String getPosiInfo() {
		return posiInfo;
	}
	public void setPosiInfo(String posiInfo) {
		this.posiInfo = posiInfo;
	}
	public int getPosiType() {
		return posiType;
	}
	public void setPosiType(int posiType) {
		this.posiType = posiType;
	}
	public Date getPubtime() {
		return pubtime;
	}
	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	public int getReliability() {
		return reliability;
	}
	public void setReliability(int reliability) {
		this.reliability = reliability;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public int getRttNum() {
		return rttNum;
	}
	public void setRttNum(int rttNum) {
		this.rttNum = rttNum;
	}
	public String getRttUrl() {
		return rttUrl;
	}
	public void setRttUrl(String rttUrl) {
		this.rttUrl = rttUrl;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSentiWords() {
		return sentiWords;
	}
	public void setSentiWords(String sentiWords) {
		this.sentiWords = sentiWords;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getWarnLevel() {
		return warnLevel;
	}
	public void setWarnLevel(int warnLevel) {
		this.warnLevel = warnLevel;
	}
	public String getWarnWord() {
		return warnWord;
	}
	public void setWarnWord(String warnWord) {
		this.warnWord = warnWord;
	}

}	
