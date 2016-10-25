package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.appCrawler.utils.PropertiesUtil;


import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 乐视电视应用市场
 * 网站主页：http://www.letvstore.com/appIndex.html
 * Aawap #633
 * @author lisheng
 */


public class LetvStore implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		List<Apk> apks = new ArrayList<Apk>();
		if("http://www.letvstore.com/".equals(page.getUrl().toString()))
		{
			
			String categroy[] = {"http://www.letvstore.com/tvstore-tv-service/api/tvservice/core/categoryApp?ccode=20130418164308&model=100&categoryId=1&appId=110573&&orderPyItem=downloadNum&page=1&pageSize=28",
					"http://www.letvstore.com/tvstore-tv-service/api/tvservice/core/categoryApp?ccode=20130418164308&model=100&categoryId=25&appId=110573&&orderPyItem=downloadNum&page=1&pageSize=28",
					"http://www.letvstore.com/tvstore-tv-service/api/tvservice/core/categoryApp?ccode=20130418164308&model=100&categoryId=23&appId=110573&&orderPyItem=downloadNum&page=1&pageSize=28",
					"http://www.letvstore.com/tvstore-tv-service/api/tvservice/core/categoryApp?ccode=20130418164308&model=100&categoryId=22&appId=110573&&orderPyItem=downloadNum&page=1&pageSize=28",
					"http://www.letvstore.com/tvstore-tv-service/api/tvservice/core/categoryApp?ccode=20130418164308&model=100&categoryId=83&appId=110573&&orderPyItem=downloadNum&page=1&pageSize=28"};
			List<String> apps = new ArrayList<String>();
			for(String str:categroy)
			{
				for(int i=1;;i++)
				{
					String str1 = str.replace("page=1", "page="+i);
					String raw = SinglePageDownloader.getHtml(str1);
					System.out.println(str1);
					if(!raw.contains("appId"))
						break;
					else
					{
						System.out.println(str.replace("page=1", "page="+i));
						apps.add(str.replace("page=1", "page="+i));}
				}
			}
		for(String str:apps)
		{
			try{
				String json = SinglePageDownloader.getHtml(str);
				System.out.println(json);
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String,Object> map = objectMapper.readValue(json, Map.class);
				List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("categoryAppList");
				for(Map<String, Object> map1:list)
				{
					 String appName = map1.get("name").toString();
	            	 String appUpadte = map1.get("createTime").toString();
	            	 String appDesp = map1.get("description").toString();
	            	 String appDownloadTimes =map1.get("downloadCount").toString();
	            	 String appDownloadUrl = map1.get("apk").toString();
	            	 String appSize = map1.get("size").toString();
	            	 String appId = map1.get("appId").toString();
	            	 String url = "http://www.letvstore.com/tvstore-tv-service/api/tvservice/core/v2/getAppDetail?ccode=20130418164308&model=100&appId="+appId;
	            	 String appDetailUrl = "http://www.letvstore.com/appDetail.html?id="+appId;
	            	
		             System.out.println("appName="+appName);
		 			System.out.println("appDetailUrl="+appDetailUrl);
		 			System.out.println("appDownloadUrl="+appDownloadUrl);
		 			System.out.println("appSize="+appSize);
		 			System.out.println("appUpdateDate="+appUpadte);
		 			System.out.println("appDownloadedTime="+appDownloadTimes);
		 			System.out.println("appDescription="+appDesp);
		            Apk apk = new Apk(appName,appDetailUrl,appDownloadUrl,null ,null,appSize,appUpadte,null,null);
		            apk.setAppDownloadTimes(appDownloadTimes);
					apk.setAppDescription(appDesp);
					apks.add(apk);
				}
				
			}
			catch(Exception e){e.printStackTrace();}
		}
			
		}
			page.putField("apks", apks);
			if(page.getResultItems().get("apks") == null){
				page.setSkip(true);
				}
		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
}
