package common;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import common.download.PatentDownload;
import common.system.FileOperation;
import common.system.SystemCommon;

public class PatentMain {
	public static void getPDF(){
		PatentDownload ma = new PatentDownload();
		List<String> downloadedlist =  new LinkedList<>();
		getAllFile("pdfDownload",downloadedlist);
		
		String []notGets = FileOperation.read("file/notGet").split("\n");
		for (String s : notGets) {
			String id = s.split("##")[0];
			downloadedlist.add(id);
		}
		
		
		String []lines = FileOperation.read("config/keyword").split("\n");
		List<String> kwList =  new LinkedList<>();
		for (String line : lines) {
			if(!line.startsWith("##")){
				String id = line.split("##")[0];
				if(!downloadedlist.contains(id)){
					//if(line.contains("CN20"))
					kwList.add(line.replace("/", "_"));
				}
			}
		}
		for (String line : kwList) {
			SystemCommon.printLog(line);
		}
		System.out.println("need download count: "+kwList.size());
		//kwList.clear();
		//kwList.add("4778##轮胎失压遥控报警气压传感气门芯##CN95215857.4##轮胎传感器");
		ma.download(kwList);
	}
	public static void main(String[] args) {
		getPDF();
		//System.out.println("http://www8.drugfuture.com/cnpat/package/%E5%8F%91%E6%98%8E%E4%B8%93%E5%88%A9%E7%94%B3%E8%AF%B7%E8%AF%B4%E6%98%8E%E4%B9%A6CN201210260819.2.pdf");
	}
	
	public static void getAllFile(String path,List<String> list) {
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				String filename = tempList[i].toString();
				filename = filename.split("\\\\")[2];
				String id = filename.split("_")[0];
				filename = filename.split(id+"_")[1].split(".pdf")[0];
				list.add(id);
			}
			if (tempList[i].isDirectory()) {
				getAllFile(tempList[i].toString(),list);
			}
		}
	}
}

