package http;

public class SinaUserLoginEntity{
	
	private String nonce;
	private String pcid;
	private String pubkey;
	private String retcode;
	private String showpin;
	private String rsakv;
	private String servertime;
	private String username;
	private String usernameBase64;
	private String password;
	private String picNO;
	
	public String getUsernameBase64() {
		return usernameBase64;
	}
	public void setUsernameBase64(String usernameBase64) {
		this.usernameBase64 = usernameBase64;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPicNO() {
		return picNO;
	}
	public void setPicNO(String picNO) {
		this.picNO = picNO;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getPcid() {
		return pcid;
	}
	public void setPcid(String pcid) {
		this.pcid = pcid;
	}
	public String getPubkey() {
		return pubkey;
	}
	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getServertime() {
		return servertime;
	}
	public void setServertime(String servertime) {
		this.servertime = servertime;
	}
	public String getShowpin() {
		return showpin;
	}
	public void setShowpin(String showpin) {
		this.showpin = showpin;
	}
	public String getRsakv() {
		return rsakv;
	}
	public void setRsakv(String rsakv) {
		this.rsakv = rsakv;
	}

}