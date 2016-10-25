package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
/**
 * PPTV  http://game.g.pptv.com/game/
 * Pptv #304
 * @author tianlei
 */

public class Pptv_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Pptv_Detail.class);
	public static List<Apk> getApkDetail(Page page){
		List<Apk> list = new ArrayList<Apk>();	
		Apk apk = null;
		String appName = null;				//app名字
		String appDetailUrl = null;			//具体页面url
		String appDownloadUrl = null;		//app下载地址
		String osPlatform = null ;			//运行平台
		String appVersion = null;			//app版本
		String appSize = null;				//app大小
		String appUpdateDate = null;		//更新日期
		String appType = null;				//下载的文件类型 apk？zip？rar？
		String appVenderName = null;			//app开发者  APK这个类中尚未添加
		String appDownloadedTime=null;		//app的下载次数
		String appDescription = null;		//app的详细描述
		List<String> appScrenshot = null;			//app的屏幕截图
		String appTag = null;				//app的应用标签
		String appCategory = null;			//app的应用类别 
		String html = null;
		List<Map> appList = new ArrayList<Map>();
	
		Map map = new HashMap();
		
		Map<String,Object> result = JSON.parseObject(page.getJson().toString().replace("var mob_pic_list =",""), new TypeReference<Map<String,Object>>() { });     
		for ( int i=0 ;i<12;i++){
        	html = SinglePageDownloader.getHtml("http://game.g.pptv.com/game/alist?gc=mobile&initial=all&gsort=all&sbg=all&page="+i+"&cb=jQuery18209301845007576048_1453774462172&_=1453775999426");
        	html = convert(html);
        	appList = getInfo(html);
        	for(int j=0;j<appList.size();j++){
        	      map=appList.get(j);
        	      appName = map.get("gname").toString();      	    
        	      if(result.get(map.get("gid"))!=null){
        	    	  appDownloadUrl= getUrl(result.get(map.get("gid")).toString());
        	    	  appScrenshot = getImage(result.get(map.get("gid")).toString());
        	    	  apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
        	    	  apk.setAppScreenshot(appScrenshot);
        	    	  list.add(apk);    	 
        	      }
        	}     	
        }
		return list;
	}		
	//unicode 转 中文
	public static String convert(String utfString){  
	    StringBuilder str = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	      
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        str.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            str.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }       
	    return str.toString();  
	}
	
	private static List<Map> getInfo(String str){
    	List<Map> tmp=new ArrayList<Map>();
		String regex="\"gid\":\"([^\"]+)\",\"gname\":\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){
        	Map map = new HashMap();
        	map.put("gid", matcher.group(1).toString());
        	map.put("gname", matcher.group(2).toString());
        	tmp.add(map);
        }
    	return tmp;   	
    }
	
	private static String getUrl(String str){
    	String tmp =null;
		String regex="\"link\":\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){
        	tmp= matcher.group(1).toString();
        }
    	return tmp;   	
    }
	private static List<String> getImage(String str){
    	List<String> tmp =new ArrayList<String>();
		String regex="\"pic\":\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){
        	tmp.add( matcher.group(1).toString());
        }
    	return tmp;   	
    }
}
