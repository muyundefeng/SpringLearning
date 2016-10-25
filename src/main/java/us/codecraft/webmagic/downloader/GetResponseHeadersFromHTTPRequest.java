package us.codecraft.webmagic.downloader;
 

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GetResponseHeadersFromHTTPRequest {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://g.10086.cn/");
        URLConnection conn = url.openConnection();

        Map<String, List<String>> headerFields = conn.getHeaderFields();

        Set<String> headerFieldsSet = headerFields.keySet();
        Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

        while (hearerFieldsIter.hasNext()) {

             String headerFieldKey = hearerFieldsIter.next();
             if("Date".equals(headerFieldKey))
             {
	             List<String> headerFieldValue = headerFields.get(headerFieldKey);
	             
	             StringBuilder sb = new StringBuilder();
	             for (String value : headerFieldValue) {
	                 sb.append(value);
	                 sb.append("");
	            }
	
	            System.out.println(headerFieldKey + "=" +sb.toString());
	            SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE, dd MMM yyyy HH:mm:ss Z", Locale.UK);
	             //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            Date date = sdf1.parse(sb.toString());
	            System.out.println(date.getTime());
	           // String url1="http://oss.cmgame.com/e/public/shownextsearch?q=qq&p=1&l=10&f=www";
	            String url1="http://oss.cmgame.com/e/public/shownextsearch?_="+new Date().getTime()+"&q=qq&p=2&l=10&f=www&type=&i=&game_type=&content_id=";
	            System.out.println(url1);
	            //String url1="http://oss.cmgame.com/e/public/shownextsearch?_="+new Date().getTime()+"&q=qq&p=1&l=10&f=www";
	        	Map<String, String> map = new HashMap<String, String>();  
	    		//map.put("Referer", "http://www.zhuodown.com/plus/download.php?open=0&aid=34750&cid=3");
	    		map.put("Referer", "http://oss.cmgame.com/search/game?q=qq");
	    		map.put("Accept","application/json, text/javascript, */*");
	    		map.put("Content-Type","application/x-www-form-urlencoded");
	    		map.put("Accept-Language", "zh-CN,zh;q=0.8");
	    		map.put("Proxy-Connection", "keep-alive");
	    		map.put("Host", "oss.cmgame.com");
	    		map.put("Cache-Control", "max-age=0");
	    		map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
	    		map.put("X-Requested-With", "XMLHttpRequest");
	    		map.put("Cookie", "CLIENT_USER_FLAG=13b8c2393d4f17bb7c97fedd02fe543b20160311165116; bdshare_firstime=1458800343054; PHPSESSID=uu3jc17ivd76rvkhs08d5s4si2; Hm_lvt_b95f3bd05557e980969a42e428b0fc68=1457686278,1458725358,1459069898,1459322335; Hm_lpvt_b95f3bd05557e980969a42e428b0fc68=1459324724; a7637_pages=16; a7637_times=8");
	    		//String url1="http://oss.cmgame.com/e/public/shownextsearch?q=qq&p=1&l=10&f=www";
	            String json=SinglePageDownloader.getHtml(url1,"GET",map);
	           // String json=SinglePageDownloader.getHtml(url1);
	     		System.out.println(json);
             }
             
        }

    }

}

