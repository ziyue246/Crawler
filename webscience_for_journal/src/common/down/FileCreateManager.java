package common.down;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import common.down.FileAttr;
import common.down.FileCreateManager;


/**
 * 生成快照文件管理类
 * @author grs
 * @version 2013.4
 */
public class FileCreateManager {

	private String url;
	private final BlockingQueue<FileAttr> queue;
	private final FileThread fileThread;
	private volatile int count;//文件数量
	private volatile boolean isShutdown;//是否关闭
	
	public FileCreateManager(String category) {
		queue = new ArrayBlockingQueue<FileAttr>(Integer.MAX_VALUE>>11);
		fileThread = new FileThread();
		this.url = category;
	}
	
	/**
	 * 线程启动
	 */
	public void start() {
		fileThread.start();
	}
	/**
	 * 关闭当前线程
	 */
	public void stop() {
		synchronized (this) {
			isShutdown=true;
		}
		fileThread.interrupt();
	}
	
	public void createFile(FileAttr fa) {
		synchronized (this) {
			if(isShutdown) {
				throw new IllegalStateException("生成文件服务已关闭！");
			}
			++count;
		}
		try {
			queue.put(fa);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private class FileThread extends Thread {
		@Override
		public void run() {
			while(true) {
				synchronized (FileCreateManager.class) {
					if(isShutdown && count==0) 
						break;
				}
				FileAttr fa = null;
				try {
					fa = queue.take();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				synchronized(FileCreateManager.class) {
					--count;
				}
				FileOutputStream fos = null;
				OutputStreamWriter osw = null;
				try {
					String p = "";
					String ff = fa.name;
					if(fa.name.indexOf(File.separator) > -1) {
						p = fa.name.substring(0, fa.name.lastIndexOf(File.separator));
						ff = fa.name.substring(fa.name.lastIndexOf(File.separator)+1, fa.name.length());
					}
					File f = new File(url+File.separator+p);
					if(!f.exists()) f.mkdirs();
					
					fos = new FileOutputStream(f.getAbsolutePath()+
							File.separator+ff+fa.endfix);
					osw = new OutputStreamWriter(fos, fa.encode);
					osw.write(fa.content);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(osw!=null)
							osw.close();
						if(fos!=null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}

}
