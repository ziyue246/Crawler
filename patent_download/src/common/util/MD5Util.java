package common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static final String MD5 = "MD5";
	public static final String CODE = "%02x";
	private static MessageDigest digest;
	static {
		try {
			digest = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("û�д˼����㷨��");
		}
	}
	/**
	 * md5���ܴ���
	 * @param text
	 * 		����md5���ܵ�String����
	 * @return
	 * 		md5���ܺ��String����
	 */
	public synchronized static String MD5(String text) {
		byte[] bytes = digest.digest(text.getBytes());
		StringBuilder output = new StringBuilder(bytes.length);
		for (byte entry : bytes) {
			output.append(String.format(CODE, entry));
		}
		digest.reset();
		bytes = null;
		return output.toString();
	}
}
