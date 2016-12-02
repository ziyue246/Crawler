package common.util;

import java.util.Arrays;
import java.util.List;

/**
 * 字符编码类
 * 
 * @author grs
 * @since 2011年7月
 */
public class EncoderUtil {
	/**
	 * 特殊字符集
	 */
	private static final List<Character> SPECIAL_CHAR = Arrays.asList('!', 
			'®', '<', '>', '【', '】', '“', '”', '°', '{', '|', '}', '~',
			'¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '¯',
			'±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', 'º', '»', '¼', '½',
			'¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê',
			'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×',
			'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä',
			'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ',
			'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ',
			'ÿ','[',']',' ');
	private static final String CODE = "%";
	private static final String CODE_25 = "%25";
	// 对中英文和特殊字符的编码
	private static String encode2Hex(String text, String encode, boolean add) {
		if(encode.contains("iso")) {
			encode = "utf-8";
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				if (SPECIAL_CHAR.contains(c))
					result.append(add?CODE_25:CODE).append(Integer.toHexString(c).toUpperCase());
				else
					result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes(encode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append(add?CODE_25:CODE).append(Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}
	/**
	 * 将16进制编码转换为中文
	 * @param text
	 * @return
	 */
	public static String hex2String(String text) {  
	   byte[] baKeyword = new byte[text.length() / 2];  
	   for (int i = 0; i < baKeyword.length; i++) {  
	    try {  
	     baKeyword[i] = (byte) (0xff & Integer.parseInt(text.substring(  
	       i * 2, i * 2 + 2), 16));  
	    } catch (Exception e) {  
	     e.printStackTrace();  
	    }  
	   }  
	   try {  
	    text = new String(baKeyword, "utf-8");
	   } catch (Exception e1) {  
	    e1.printStackTrace();  
	   }  
	   return text;  
	}
	/**
	 * 中英文编码处理
	 * 
	 * @param text
	 * @param Endocing
	 * @return
	 */
	public static String encodeKeyWords(String words, String encode, boolean add) {
		String codes = "", word = "";
		int i = 0;
		words += ' ';
		while (i < words.length()) {
			if (words.charAt(i) != ' ') {
				word += words.charAt(i);
			} else {
				codes += encode2Hex(word+" ", encode, add);
				word = "";
			}
			i++;
			
		}
		codes = codes.substring(0, codes.length() - 3);
		return codes;
	}
	
	public static String encodeKeyWords(String words, String encoding) {
		return encodeKeyWords(words, encoding, false);
	}
	
}
