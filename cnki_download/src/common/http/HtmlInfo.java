package common.http;

/**
 * ��ҳ��Ϣ
 * 
 *
 */
public class HtmlInfo {

	private String site;// webվ��
	private String orignUrl;// �ɼ�Դ���ӡ����������Ӳ��ֿ��ܻ᲻ͬ
	private String realUrl;// �������ӣ��ޱ仯Ϊnull
	private String encode;// ҳ�����
	private String content;// ҳ������
	private boolean agent;// ����
	private String crawlerType;// �ɼ���ҳ�����ͣ��б�ҳ������ҳ������ҳ������ҳ
	private boolean addHead;// ��������ͷ
	private String referUrl;// ��һ��ҳ������
	private String cookie;// ҳ��cookie
	private boolean fixEncode;
	private String fileType = ".htm";// �ļ�����
	private String ua;// user agent
	private String acceptEncoding;
	private int siteId;// �����������ݿ���proxy_server����domain_idһһ��Ӧ
	private String responseCookie;// ���ص�cookie
	private String searchKeyword;
	

	public String getResponseCookie() {
		return responseCookie;
	}

	public void setResponseCookie(String responseCookie) {
		this.responseCookie = responseCookie;
	}

	public String getCrawlerType() {
		return crawlerType;
	}

	public void setCrawlerType(String crawlerType) {
		this.crawlerType = crawlerType;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getOrignUrl() {
		return orignUrl;
	}

	public void setOrignUrl(String orignUrl) {
		this.orignUrl = orignUrl;
	}

	public String getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getAgent() {
		return agent;
	}

	public void setAgent(boolean agent) {
		this.agent = agent;
	}

	public String getType() {
		return crawlerType;
	}

	public void setType(String type) {
		this.crawlerType = type;
	}

	public boolean getAddHead() {
		return addHead;
	}

	public void setAddHead(boolean addHead) {
		this.addHead = addHead;
	}

	public String getReferUrl() {
		return referUrl;
	}

	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public boolean getFixEncode() {
		return fixEncode;
	}

	public void setFixEncode(boolean changeEncode) {
		this.fixEncode = changeEncode;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

}
