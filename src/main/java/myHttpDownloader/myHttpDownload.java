package myHttpDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class myHttpDownload {

    public String[] filePath = {"/home/lisheng/GradleWorkspace/apks/360/",
    		"/home/lisheng/GradleWorkspace/apks/baidu/",
    		"/home/lisheng/GradleWorkspace/apks/tencent/"};
//	public String[] filePath = {"/home/suifeng/FileTemp/lisheng/httpDownloader/360/",
//	"/home/suifeng/FileTemp/lisheng/httpDownloader/baidu/",
//	"/home/suifeng/FileTemp/lisheng/httpDownloader/tencent/"};
    private static final Logger logger = LoggerFactory.getLogger(myHttpDownload.class);
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        logger.info("start download apk file");
    	URL url = new URL(urlStr);
        String proxyHost = "";
		int proxyPort = 0;
		Random random = new Random();
        String proxys[] = Property.getProxys();
		int rand = random.nextInt(proxys.length);
      
        //设置超时间为3秒
        if(Property.getUseProxy()){
			proxyHost =  proxys[rand].split(":")[0];
			logger.info("proxyHost="+proxyHost);
			proxyPort = Integer.parseInt(proxys[rand].split(":")[1]);//分离出端口号
			logger.info("proxyPort="+proxyPort);

		}
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));  

        HttpURLConnection conn = (HttpURLConnection)url.openConnection(proxy);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestProperty("referer", "http://apk.gfan.com/Product/App179631.html");
        conn.setRequestProperty("cookie","http://apk.gfan.com/Product/App179631.html");//http://apk.gfan.com/Product/App179631.html
        Map<String,List<String>>map=conn.getHeaderFields();
        System.out.println("-----"+conn);
        List<String> a=map.get("Content-Length");
        long contentSize=Integer.parseInt(a.get(0));
        System.out.println(contentSize);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        //byte[] getData = readInputStream(inputStream,contentSize);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        double size = 0;
        while((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
            size = size +len;
            System.out.println("====="+(double)size/(double)contentSize*100);
        }
        //fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success");
        logger.info("success download apk file");
    }

	public  void singleDownload(String url,String appName) {
		//SingleDownloader(appName, url);
		try {
			downLoadFromUrl(url, appName, filePath[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void SingleDownloader(String appName,String appUrl){
//		//String proxyIpAndPort = Property.getHostName();
//		String proxyHost = "";
//		int proxyPort = 0;
//        logger.info("downloading url="+appUrl+","+"appName="+appName);
//
//        Random random = new Random();
//        String proxys[] = Property.getProxys();
//		int rand = random.nextInt(proxys.length);
//		//int index = random.nextInt();
//		if(Property.getUseProxy()){
//			proxyHost =  proxys[rand].split(":")[0];
//			logger.info("proxyHost="+proxyHost);
//			proxyPort = Integer.parseInt(proxys[rand].split(":")[1]);//分离出端口号
//			logger.info("proxyPort="+proxyPort);
//
//		}
//		try {
//			//打开一个网址，获取源文件			
//			URL url=new URL(appUrl);	
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));  
//			HttpURLConnection urlConnection = null;
//			//是否使用代理
//			if(Property.getUseProxy())
//					urlConnection = (HttpURLConnection) url.openConnection(proxy);
//			else 
//					urlConnection = (HttpURLConnection) url.openConnection();
//			urlConnection.setRequestMethod("GET");
//			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			System.out.println(urlConnection.getResponseCode());
//			//urlConnection.setReadTimeout(Integer.MAX_VALUE);
//	        InputStream inputStream = urlConnection.getInputStream();
//	        FileOutputStream fs = new FileOutputStream(getFilePath(appUrl, appName));
//	        int byteread = 0;
//	        int bytesum = 0;
//            byte[] buffer = new byte[BUFFER_SIZE];
//            while ((byteread = inputStream.read(buffer)) != -1) {
//                bytesum += byteread;
//                //System.out.println(bytesum);
//                fs.write(buffer, 0, byteread);
//            }
//             fs.close();
//             inputStream.close();
//             logger.info("download apk successfully");
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			logger.error("validate url");
//		}
//	      
//	}
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
		//String filepath = root + splash;
		//String filename = getFileNameFromUrl(url,appName);
		//if (filename != null) {
			path += (appName+".apk");
		//} 
		return path;
	}
	
	
	
	
	public static void main(String[] args) {
		//String dir = "info3.txt";
		
		//String url = "http://shouji.360tpcdn.com/160621/d63f27d6417067c3f98c1fcb587d481c/com.qihoo.haosou_707.apk";
//		String[] sourceFile = {"info.txt","info1.txt","info2.txt"};
//		String dir = "";
//		if(args.length<=0){
//			logger.info("please enter validate argument");
//		}
//		else{
//			dir = sourceFile[Integer.parseInt(args[0])];
//		}
		myHttpDownload httpDownload  = new myHttpDownload();
//		logger.info("access file "+dir);
		String dir = "info.txt";
		String json = httpDownload.readInfoFromText("/home/lisheng/data/"+dir);
//		String json = httpDownload.readInfoFromText("/home/suifeng/FileTemp/lisheng/httpDownloader/info/"+dir);
		logger.info(json);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, String>> list = null;
		try {
			list = objectMapper.readValue(json, List.class);
			System.out.println(list);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			logger.error(e1.toString());
		}
		for(Map<String,String> map:list)
		{
			String appName = map.get("appName").toString();
			String appDownloadUrl = map.get("appDownloadUrl").toString();
			String code = "";
			httpDownload.singleDownload(appDownloadUrl, appName);
			}
		}
	}
	