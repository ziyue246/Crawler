package common.system;

import java.io.*;

public class FileOperation {

	public static void main(String[] args) {
		String file = "123.jpg";
		int a = file.lastIndexOf(".");
		String b = file.substring(0,a);
		String c = file.substring(a);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	public static void renameFile(String path,String oldname,String newname){ 
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
            File oldfile=new File(path+"/"+oldname); 
            File newfile=new File(path+"/"+newname); 
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 {
            {
                System.out.println(newname+"已经存在！");
                if(newname.contains(".")){
                	int pointSite = newname.lastIndexOf(".");
                	String newnameHead = newname.substring(0,pointSite);
                	String newnameRear = newname.substring(pointSite);
                	renameFile(path,oldname,newnameHead+"."+newnameRear);
                }else{
                	renameFile(path,oldname,newname+"1");
                }
            }
            else{ 
                oldfile.renameTo(newfile); 
            } 
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }
	public static void renameFile(String oldname,String newname){ 
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
            File oldfile=new File(oldname); 
            File newfile=new File(newname); 
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
                System.out.println(newname+"已经存在！"); 
            else{ 
                oldfile.renameTo(newfile); 
            } 
        }else{
            System.out.println("new file and old file have same name...");
        }
    }
	public FileOperation() {

	}
	public static void appendWrite(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static String read(String fileName) {
		
		try {
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				sb.append(line+"\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
	public static void write(String content) {
		BufferedOutputStream Buff = null;
		try {
			Buff = new BufferedOutputStream(new FileOutputStream(new File("add0.txt")));

			Buff.write(content.getBytes());
			Buff.flush();
			Buff.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				Buff.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
