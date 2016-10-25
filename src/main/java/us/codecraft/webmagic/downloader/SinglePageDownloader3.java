package us.codecraft.webmagic.downloader;


/*
 * 及时下载一个页面，并获取页面内容保存到String中
 * (1)如果是post请求，map1里保存参数
 * (2)如果map2不为空，则里面保存的是需要添加的请求头内容
 * */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.appCrawler.utils.PropertiesUtil;



public class SinglePageDownloader3 {
	
	public static String getHtml(String urlString,String charset){
		return getHtml(urlString, "GET", null,null,charset);
	}
	
	
	public static String getHtml(String urlString,String method,Map<String, String> map1,String charset){
		return getHtml(urlString, method, map1,null,charset);
	}
	
	
	public static String getHtml(String urlString,String method,Map<String, String> map1,Map<String, String> map2,String charset){
		//Html html =null;
		String lines = "";
		String sourcefile="";
		String proxyIpAndPort = getProxyIpAndPort();//获取代理的域名和端口号格式为xxx.xxx.xxx.xxx:xxxx
		String proxyHost = "";
		int proxyPort = 0;
		if(PropertiesUtil.getCrawlerProxyEnable()){
			proxyHost = proxyIpAndPort.split(":")[0];//分离出域名
			proxyPort = Integer.parseInt(proxyIpAndPort.split(":")[1]);	//分离出端口号
		}
		try {
			//打开一个网址，获取源文件			
			URL url=new URL(urlString);	
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));  
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(proxy);
			if(map2 != null){//如果需要添加请求头，怎添加请求头
				for (Map.Entry<String, String> entry : map2.entrySet()) {
					urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
				}
//				urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
			}
			
			if(method.equals("POST")){//如果是post请求，添加post参数
				urlConnection.setRequestMethod("POST");// 提交模式	       
				urlConnection.setDoOutput(true);// 是否输入参数
				StringBuffer params = new StringBuffer();
		        // 表单参数与get形式一样
				for (Map.Entry<String, String> entry : map1.entrySet()) {  
					params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");			  
				} 

		        byte[] bypes = params.toString().getBytes();
		        urlConnection.getOutputStream().write(bypes);	// 输入参数		        
			}				
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			 while ((lines = reader.readLine()) != null){
				 	sourcefile=sourcefile+lines;
				 	
				}
			 //System.out.println(sourcefile);				
		} catch (Exception e) {
			System.out.println("download "+urlString+" error");
		}
		//System.out.println(getProxyIpAndPort());
	//	html = Html.create(sourcefile);
		return sourcefile;
	}
	private static String getProxyIpAndPort(){
		
		if (PropertiesUtil.getCrawlerProxyEnable()) {			
			String[] hostAndPost = PropertiesUtil.getCrawlerProxyHostAndPort();			
		//获取随机数
			Random random = new Random();
			int rand = random.nextInt(hostAndPost.length);
			return hostAndPost[rand];
		}
		return null;
	}
	public static void main(String[] args){	   	
//    	for(int i=1;i<450;i++){
//    		Map<String, String> map = new HashMap<String, String>();  
//    		map.put("gchannel", "all");
//    		map.put("order", "update");  
//    		map.put("page", String.valueOf(i));
//    		map.put("plat", "1");
//    		String temp = SinglePageDownloader.getHtml("http://www.shuiguo.com/app.php?action=gchannel","POST",map,null);
//        	System.out.println(temp);
//        	
//    	}
//    	Map<String, String> map = new HashMap<String, String>();  
//		map.put("Cookie", "sogou_ts_ads0=popdate:115%u5E7411%u67081%u65E5&vt:0&ht:&qt:0&jd:; ASPSESSIONIDQACDBCCB=DOMBMLFDPDKFGHIKKJHJHJBJ; __jsluid=1ec644ac3958b8f2ea9e93e9deb64a0f; ASPSESSIONIDSCACDDDA=OGHJOKFDPJMPFDPPEDOMKNAE; ASPSESSIONIDQCBCCDCA=FFOLPLFDLGKGIEFPNHLMABLC; CNZZDATA1221717=cnzz_eid%3D1220294204-1446169774-%26ntime%3D1446426220; ASPSESSIONIDSCACCCDA=OICDKMFDIEBIBPEAMKNLEPEO; ASPSESSIONIDQACACDCB=BLCHKMFDBBFNOHBEMPLCIEFJ; ASPSESSIONIDQCAACDCA=PBGNCNFDHBDDFDCNEOPLPLEB; ASPSESSIONIDSCDDACDB=DHJBLNFDKOIHOFLONFJIBOEF");
//		map.put("Host", "www.lzvw.com");  
//		map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36" );
	
		String temp = SinglePageDownloader3.getHtml("http://www.uzzf.com/apk/214778.html","gb2312");
		System.out.println(temp);
	}
}
//		Map<String, String> map = new HashMap<String, String>();  
//		map.put("Cookie", "sogou_ts_ads0=popdate:115%u5E7411%u67081%u65E5&vt:0&ht:&qt:0&jd:; ASPSESSIONIDQACDBCCB=DOMBMLFDPDKFGHIKKJHJHJBJ; __jsluid=1ec644ac3958b8f2ea9e93e9deb64a0f; ASPSESSIONIDSCACDDDA=OGHJOKFDPJMPFDPPEDOMKNAE; ASPSESSIONIDQCBCCDCA=FFOLPLFDLGKGIEFPNHLMABLC; CNZZDATA1221717=cnzz_eid%3D1220294204-1446169774-%26ntime%3D1446426220; ASPSESSIONIDSCACCCDA=OICDKMFDIEBIBPEAMKNLEPEO; ASPSESSIONIDQACACDCB=BLCHKMFDBBFNOHBEMPLCIEFJ; ASPSESSIONIDQCAACDCA=PBGNCNFDHBDDFDCNEOPLPLEB; ASPSESSIONIDSCDDACDB=DHJBLNFDKOIHOFLONFJIBOEF");
//		map.put("Host", "www.lzvw.com");  
//		map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36" );
//		map.put("plat", "1");
    	
//    	String temp = SinglePageDownloader.getHtml("http://www.lzvw.com/js/hits.asp?id=33382","",null);
//    	//System.out.println(temp);
//    	
//
//	}
//}
