package common;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import common.download.CnkiDownload;
import common.service.mysql.ConnectSql;

public class CnkiMain {
	
	
	public static void getPDF(){
		CnkiDownload ma = new CnkiDownload();
		List<String> downloadedlist =  new LinkedList<>();
		getAllFile("downloadPDF",downloadedlist);
		List<String> kwList = ConnectSql.getSearchMessageList(downloadedlist);
		System.out.println("org get message count: "+kwList.size());
		
		System.out.println("processed message count: "+kwList.size());
		ma.download(kwList);
	}
	public static void main(String[] args) {
		getPDF();
	}

	public static void getAllFile(String path,List<String> list) {
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				String filename = tempList[i].toString();
				filename = filename.split("_")[1].split(".pdf")[0];
				list.add(filename);
			}
			if (tempList[i].isDirectory()) {
				getAllFile(tempList[i].toString(),list);
			}
		}
	}
}
