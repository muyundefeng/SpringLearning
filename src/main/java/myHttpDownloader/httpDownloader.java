package myHttpDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author lisheng
 *	apk 下载器的实现
 *	APK下载器进行了相关的重构实现,主要从多多线程下载apk文件,
 *	对失败的下载任务进行相关的重试下载,至多重试三次,如果依然失败
 *	将相关的失败任务进行持久化(写入文件)
 */
public class httpDownloader {
	//public static String path = "http://imtt.dd.qq.com/16891/7C9967AE592C6B1AEC77B9BD71B4F53B.apk?fsname=com.changba_7.4.0_740.apk&csr=4d5s";
	public static final int threadCount = 3;
	public volatile AtomicInteger  atomicInteger = new AtomicInteger(threadCount);
	public static int flag = threadCount;
	public static final int retryTimes = Property.getRetryTimes();
	public static Stack<Map<Integer,Map<String,String>>> stack = new Stack<Map<Integer,Map<String,String>>>();//失败的下载任务返回到这里
	public ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    private static final Logger logger = LoggerFactory.getLogger(httpDownloader.class);
//	public String[] filePath = {"/home/lisheng/GradleWorkspace/apks/360/",
//    		"/home/lisheng/GradleWorkspace/apks/baidu/",
//    		"/home/lisheng/GradleWorkspace/apks/tencent/"};
	public String[] filePath = {"/home/suifeng/FileTemp/lisheng/httpDownloader/360/",
			"/home/suifeng/FileTemp/lisheng/httpDownloader/baidu/",
			"/home/suifeng/FileTemp/lisheng/httpDownloader/tencent/"};
	public static final String failedPath = "/home/suifeng/lisheng/httpDownloader/failedTask/";
	
	public  void download(String path1,String appName1) throws Exception{
		//1.连接服务器，获取一个文件，获取文件的长度，在本地创建一个跟服务器一样大小的临时文件
		String path = path1;
		String appName = appName1;
		int time = 0;
		if(!stack.isEmpty()){
			Map<Integer, Map<String,String>> map = stack.pop();
			for(Map.Entry<Integer, Map<String, String>> entry:map.entrySet()){
				time = entry.getKey();
				logger.info("第"+time+"重试下载");
			}
			logger.info("retry time is "+time);
			Map<String, String> map2 = map.get(time);
			for(Map.Entry<String, String> entry:map2.entrySet()){
				appName = entry.getKey();
				logger.info("第"+time+"重试下载");
				path = entry.getValue();
				logger.info("download url="+path+","+"appName="+appName);
				//download(path, appName);
			}
			
		}
		logger.info("下载appName="+appName);
		URL url = new URL(path);
		String proxyHost = "";
		int proxyPort = 0;
		String proxys[] = Property.getProxys();
		Random random = new Random();
		int rand = random.nextInt(proxys.length);
		//int index = random.nextInt();
		if(Property.getUseProxy()){
			proxyHost =  proxys[rand].split(":")[0];
			proxyPort = Integer.parseInt(proxys[rand].split(":")[1]);//分离出端口号
			logger.info("proxyHost="+proxyHost+","+"proxyPort="+proxyPort);
		}
		
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));  
		HttpURLConnection conn = null;
		//是否使用代理
		if(Property.getUseProxy())
			conn = (HttpURLConnection) url.openConnection(proxy);
		else 
			conn = (HttpURLConnection) url.openConnection();
		//HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		int code = 0;
		
		try {
			code = conn.getResponseCode();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
		}
		if (code == 200) {
			//服务器端返回的数据的长度，实际上就是文件的长度
			int length = conn.getContentLength();
			logger.info("文件总长度"+length);
			//在客户端本地创建出来一个大小跟服务器端一样大小的临时文件
			String fileName = getFilePath(path, appName);
			RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");
			//指定创建的这个文件的长度
			raf.setLength(length);
			raf.close();
			//平均每一个线程下载的文件大小.
			int blockSize = length / threadCount;
			for (int threadId = 1; threadId <= threadCount; threadId++) {
				//第一个线程下载的开始位置
				int startIndex = (threadId - 1) * blockSize;
				int endIndex = threadId * blockSize - 1;
				if (threadId == threadCount) {//最后一个线程下载的长度要稍微长一点
					endIndex = length;
				}
				logger.info("线程："+threadId+"下载:---"+startIndex+"--->"+endIndex);
				executorService.submit(new DownLoadThread(threadId, startIndex, endIndex,fileName,path,atomicInteger,proxyHost,proxyPort));
			}
			while(true){
				if(atomicInteger.get()==0){
					break;
				}
				Thread.sleep(5000);
			}
			atomicInteger.set(threadCount);
		
		}else {
			logger.error("服务器错误!");
			logger.error("本次下载失败,添加入堆栈进行下一次重试");
			time++;
			if(time<retryTimes){
				Map<String, String> map3 = new HashMap<String,String>();
				map3.put(appName, path);
				Map<Integer, Map<String, String>> map4 = new HashMap<Integer,Map<String, String>>();
				map4.put(time, map3);
				stack.push(map4);
			}
			else{
				logger.error(retryTimes+"之后仍然失败,将失败任务写入文件");
				writeFailedTaskTOFile(appName+":"+path+"|");
			}
			
		}
	}
	
	/**将失败任务写入文件
	 * @param url
	 */
	public void writeFailedTaskTOFile(String info){
		System.out.println(info);
		String fileName =  "";
		try{
			if(info.contains("gdown.baidu.com/")){
				fileName = "baidu-failed.txt";
			}
			else{
				if(info.contains("shouji.360tpcdn.com")){
					fileName = "360-failed.txt";
				}
				else{
					fileName = "tencent-failed.txt";
				}
			}
			System.out.println(fileName);
		    
			File file = new File(failedPath+fileName);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileOutputStream out = null;   
	        try {   
	            out = new FileOutputStream(file,true);   
	            out.write(info.getBytes());    
	            out.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();  
			    logger.info("写入文件失败");
	        }   
             logger.info("写入文件成功");
	   }
		catch(Exception e){
		      e.printStackTrace();
		      logger.info("写入文件失败");
	     }
	}
	/**
	 * @param url
	 * @param response
	 * @param appName
	 * @return
	 */
	public  String getFilePath(String url,String appName) {
		String path = null;
		if(url.contains("http://p.gdown.baidu.com/")){
			path = filePath[1]; 
		}
		else{
			if(url.contains("shouji.360tpcdn.com")){
				path = filePath[0];
			}
			else{
				path = filePath[2];
			}
		}
			path += (appName+".apk");
		return path;
	}
	/**
	 * @param filePath　存放文件得路径名称．
	 */
	public  String readInfoFromText(String filePath){
		String json = "";
		System.out.println(filePath);
		File file = new File(filePath);
		FileInputStream fis = null;
        byte[] buf = new byte[1024];
		try {
			fis=new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//BufferedReader br=new BufferedReader(fr);
		StringBuffer sb=new StringBuffer();
		try {
			while(fis.read(buf)!=-1){
			  sb.append(new String(buf));
			  buf=new byte[1024];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		String[] sourceFile = {"info.txt","info1.txt","info2.txt"};
		String dir = "";
		if(args.length<=0){
			logger.info("please enter validate argument");
		}
		else{
			dir = sourceFile[Integer.parseInt(args[0])];
		}
		
		httpDownloader httpDownloader = new httpDownloader();
//		String json = httpDownloader.readInfoFromText("/home/lisheng/data/info2.txt");

		String json = httpDownloader.readInfoFromText("/home/suifeng/FileTemp/lisheng/httpDownloader/info/"+dir);
		logger.info(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Queue<Map<String, String>> queue = null;
		try {
			queue = objectMapper.readValue(json, Queue.class);
			System.out.println(queue);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			logger.error(e1.toString());
		}
		boolean flag = false;
		while(!queue.isEmpty())
		{
			while(!stack.isEmpty()){
				try {
					httpDownloader.download("", "");
					flag = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(flag == true){
				flag = false;
				continue;
			}
			Map<String, String> map = queue.poll();
			String appName = map.get("appName").toString();
			String appDownloadUrl = map.get("appDownloadUrl").toString();
			logger.info("download url="+appDownloadUrl+","+"appName="+appName);
			try {
				httpDownloader.download(appDownloadUrl, appName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	
	/**
	 * 下载文件的子线程  每一个线程下载对应位置的文件
	 * @author lisheng
	 *
	 */
	class DownLoadThread implements Callable<String>{
		private int threadId;
		private int startIndex;
		private int endIndex;
		private String fileName;
		private String path;
		private AtomicInteger atomicInteger;
		private String proxyHost;
		private int proxyport;
	    private static final Logger logger = LoggerFactory.getLogger(DownLoadThread.class);
		/**
		 * @param path1 下载文件在服务器上的路径
		 * @param threadId 线程Id
		 * @param startIndex 线程下载的开始位置
		 * @param endIndex	线程下载的结束位置
		 */
		public DownLoadThread(int threadId, int startIndex, int endIndex,String fileName,String path,AtomicInteger atomicInteger,String proxyHost,int proxyPort) {
			super();
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.fileName = fileName;
			this.path = path;
			this.atomicInteger = atomicInteger;
			this.proxyHost = proxyHost;
			this.proxyport = proxyPort;
		}

		@Override
		public String call() throws Exception {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = null;
				
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyport));  
				//HttpURLConnection urlConnection = null;
				//是否使用代理
				if(Property.getUseProxy())
					conn = (HttpURLConnection)url.openConnection(proxy);
				else 
					conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				//重要:请求服务器下载部分文件 指定文件的位置
				conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				//从服务器请求全部资源返回200 ok如果从服务器请求部分资源 返回 206 ok
				//int code = conn.getResponseCode();
				
				InputStream is = conn.getInputStream();//已经设置了请求的位置，返回的是当前位置对应的文件的输入流
				RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");
				//随机写文件的时候从哪个位置开始写
				raf.seek(startIndex);//定位文件
				int len = 0;
				byte[] buffer = new byte[10240];
				while ((len = is.read(buffer)) != -1) {
					//System.out.println(len);
					raf.write(buffer, 0, len);
				}
				is.close();
				raf.close();
				logger.info("线程："+threadId+"下载完毕");
				atomicInteger.decrementAndGet();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "success";
		}
	}