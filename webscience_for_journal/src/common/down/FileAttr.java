package common.down;

public class FileAttr {

	public String name;
	public String content;
	public String encode;
	public String endfix;
	public long size;
	
	public FileAttr(String name, String value, String encode, String endfix) {
		this.name = name;
		this.content = value;
		this.encode = encode;
		this.endfix = endfix;
		this.size = value.length();
	}
	
}
