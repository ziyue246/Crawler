package cn.smy.dama2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import common.system.SystemCommon;

/**
 *
 * @author __USER__
 */
public class Dame2Code {
	
	private static Dama2 dama2 = new Dama2();
	
	
//	public static String d2BufActionPerformed(String filename) {
//		File file = new File(filename);
//		long len = file.length();
//		byte[] data = new byte[(int) len];
//		FileInputStream fs;
//		try {
//			fs = new FileInputStream(file);
//			fs.read(data);
//		} catch (FileNotFoundException e) { 
//			e.printStackTrace();
//			return null;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		String[] vcode = new String[1];
//		int ret = dama2.d2Buf(
//				"9503ce045ad14d83ea876ab578bd3184", //software ID 
//				"crawler1", //user name 
//				"123654", //user password
//				data,	//file data
//				(short) 40, //timeout , seconds 
//				(long) 42, //code type id 
//				vcode); //return code text
//		System.out.println(ret);
//		if(ret>0){
//			return vcode[0];
//		}
//		return null;
//	}

	public static int d2FileActionPerformed(String filename,String []result) {
		if(filename==null)return 0;
		String[] vcode = new String[1];
		int ret = dama2.d2File("9503ce045ad14d83ea876ab578bd3184", // software
																	// ID
				"crawler1", // user name
				"123654", // user password
				filename, // file name
				(short) 40, // timeout , seconds
				(long) 42, // code type id
				vcode); // return code text
		result[0] = vcode[0]; 
		return ret;		
	}
	public static void errorReport(int ulVCodeID) {
		int result = dama2.reportResult(ulVCodeID, false);
		if(result!=0){
			SystemCommon.printLog("dama2 wrong reture code: "+result);
		}
	}
	
//	public static void main(String args[]) {
//
//		String filename = "C:\\Users\\Administrator\\Desktop\\vid\\t.jpg";
//		String result  = d2FileActionPerformed(filename);
//		System.out.println(result);
//		
//	}
	
}