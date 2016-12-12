/**
 * 
 */
package refer.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lvhongqiang
 *
 */
public class StringUtil {

	public static String inputStream2String(InputStream inputStream) {
		try {
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = inputStream.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 首字母大写
	 * @param string
	 * @return
	 */
	public static String UpperFirst(String string){
		char[] cs=string.toCharArray();
		if(cs[0]>96&&cs[0]<123)//[a-z]
			cs[0]-=32;
        return String.valueOf(cs);
	}
}
