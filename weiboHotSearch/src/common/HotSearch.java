package common;

import java.util.Date;

public class HotSearch {
	private int     id;					//ID			NUMBER
	private int 	categoryCode = 4; 	//CATEGORY_CODE	NUMBER
	private String 	keyWord;			//KEYWORD		VARCHAR2
	private String 	siteId;				//SITE_ID		VARCHAR2
	private String 	siteName;			//SITE_NAME		VARCHAR2
	private int 	userId;				//USER_ID		NUMBER
	private Date 	proposeTime;		//PROPOSE_TIME	TIMESTAMP(6)
	private int 	status;				//STATUS		NUMBER
	private String 	type;				//TYPE			VARCHAR2
	private int 	category1;			//CATEGORY1		NUMBER
	private String 	ebStype;			//EB_STYPE		VARCHAR2
	private String 	column1;			//COLUMN1		VARCHAR2
	private int 	isUsable;			//IS_USABLE		NUMBER
	private int		highway;            //HIGHWAY		NUMBER
	private int 	debug;				//DEBUG			NUMBER
	private int 	priority;			//PRIORITY		NUMBER
	private int 	searchIndex;		//SEARCH_INDEX	NUMBER
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getProposeTime() {
		return proposeTime;
	}
	public void setProposeTime(Date proposeTime) {
		this.proposeTime = proposeTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCategory1() {
		return category1;
	}
	public void setCategory1(int category1) {
		this.category1 = category1;
	}
	public String getEbStype() {
		return ebStype;
	}
	public void setEbStype(String ebStype) {
		this.ebStype = ebStype;
	}
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	public int getIsUsable() {
		return isUsable;
	}
	public void setIsUsable(int isUsable) {
		this.isUsable = isUsable;
	}
	public int getHighway() {
		return highway;
	}
	public void setHighway(int highway) {
		this.highway = highway;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getSearchIndex() {
		return searchIndex;
	}
	public void setSearchIndex(int searchIndex) {
		this.searchIndex = searchIndex;
	}
}
