package cn.smy.dama2;


public class Dama2 {
	{
		System.loadLibrary("dama2lib/Dama2Interface");
		//System.loadLibrary("C:\\Users\\Administrator\\Desktop\\dama2\\dama2_sdk_java\\Dama2Interface.dll");
	}

	public native int getOrigError();
	public native int init(String softwareName, String softwareID);
	public native int uninit();
	public native int login(String userName, String userPassword, String dyncVCode, String [] retSysAnnouncement, String [] retAppAnnouncement);
	public native int logoff();
	public native int register(String userName, String userPassword, String qq, String telNo, String email, int nDyncVCodeSendMode);
	public native int recharge(String userName, String cardNo, long[] balance);
	public native int queryBalance(long [] balance);
	public native int readInfo(String [] userName, String [] qq, String [] telNo, String [] email, int []nDyncVCodeSendMode);
	public native int changeInfo(String oldPassword, String newPassword, String qq, String telNo, String email, String dyncVCode, int nDyncVCodeSendMode);
	public native int decode(String url, String cookie, String referer, byte vcodeLen, short timeout, long vcodeTypeID, boolean downloadFromLocalMachine, long [] requestID);
	public native int decodeBuf(byte [] data, String extName, byte vcodeLen, short timeout, long vcodeTypeID, long [] requestID);
	public native int decodeWnd(String wndDef, int x, int y, int cx, int cy, byte vcodeLen, short timeout, long vcodeTypeID, long [] requestID);
	public native int getResult(long requestID, long waitTimeout, String [] vcode, long [] vcodeID, String [] retCookie);
	public native int reportResult(long vcodeID, boolean correct);
	public native int d2Buf(String softwareID, String userName, String userPassword, byte [] data, short timeout, long vcodeTypeID, String [] retVCodeText);
	public native int d2File(String softwareID, String userName, String userPassword, String fileName, short timeout, long vcodeTypeID, String [] retVCodeText);
	public native int writeStringToPic(String picFileName, String strInfo);
	
//error code definition	
	//success code
	static final int ERR_CC_SUCCESS					=0;
		//parameter error
	static final int ERR_CC_SOFTWARE_NAME_ERR		=-1;
	static final int ERR_CC_SOFTWARE_ID_ERR			=-2;
	static final int ERR_CC_FILE_URL_ERR			=-3;
	static final int ERR_CC_COOKIE_ERR				=-4;
	static final int ERR_CC_REFERER_ERR				=-5;
	static final int ERR_CC_VCODE_LEN_ERR			=-6;
	static final int ERR_CC_VCODE_TYPE_ID_ERR		=-7;
	static final int ERR_CC_POINTER_ERROR			=-8;
	static final int ERR_CC_TIMEOUT_ERR				=-9;
	static final int ERR_CC_INVALID_SOFTWARE		=-10;
	static final int ERR_CC_COOKIE_BUFFER_TOO_SMALL	=-11;
	static final int ERR_CC_PARAMETER_ERROR			=-12;
		//user error
	static final int ERR_CC_USER_ALREADY_EXIST		=-100;
	static final int ERR_CC_BALANCE_NOT_ENOUGH		=-101;
	static final int ERR_CC_USER_NAME_ERR			=-102;
	static final int ERR_CC_USER_PASSWORD_ERR		=-103;
	static final int ERR_CC_QQ_NO_ERR				=-104;
	static final int ERR_CC_EMAIL_ERR				=-105;
	static final int ERR_CC_TELNO_ERR				=-106;
	static final int ERR_CC_DYNC_VCODE_SEND_MODE_ERR=-107;
	static final int ERR_CC_INVALID_CARDNO			=-108;
	static final int ERR_CC_DYNC_VCODE_OVERFLOW		=-109;
	static final int ERR_CC_DYNC_VCODE_TIMEOUT		=-110;
	static final int ERR_CC_USER_SOFTWARE_NOT_MATCH	=-111;
	static final int ERR_CC_NEED_DYNC_VCODE			=-112;
		//logic error
	static final int ERR_CC_NOT_LOGIN				=-201;
	static final int ERR_CC_ALREADY_LOGIN			=-202;
	static final int ERR_CC_INVALID_REQUEST_ID		=-203;		//invalid request id, perhaps request is timeout
	static final int ERR_CC_INVALID_VCODE_ID		=-204;		//invalid captcha id, perhaps request is timeout
	static final int ERR_CC_NO_RESULT				=-205;
	static final int ERR_CC_NOT_INIT_PARAM			=-206;
	static final int ERR_CC_ALREADY_INIT_PARAM		=-207;
	static final int ERR_CC_SOFTWARE_DISABLED		=-208;
	static final int ERR_CC_NEED_RELOGIN			=-209;
	static final int EER_CC_ILLEGAL_USER			=-210;
	static final int EER_CC_REQUEST_TOO_MUCH		=-211;		//concurrent request is too much

		//system error
	static final int ERR_CC_CONFIG_ERROR			=-301;
	static final int ERR_CC_NETWORK_ERROR			=-302;
	static final int ERR_CC_DOWNLOAD_FILE_ERR		=-303;
	static final int ERR_CC_CONNECT_SERVER_FAIL		=-304;
	static final int ERR_CC_MEMORY_OVERFLOW			=-305;
	static final int ERR_CC_SYSTEM_ERR				=-306;
	static final int ERR_CC_SERVER_ERR				=-307;
	static final int ERR_CC_VERSION_ERROR			=-308;
	static final int ERR_CC_READ_FILE				=-309;
	
}
