package common.bean;

/**
 * 网页信息
 * @author grs
 *
 */
public class HtmlInfo {

	private String site;
	private String orignUrl;
	private String realUrl;
	private String encode;
	private String content;
	private boolean agent;
	private String type;//采集的页面类型：列表页，内容页，引文页，被引页
	
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
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
