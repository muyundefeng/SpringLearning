package com.appCrawler.pagePro.fullstack;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Dsy5_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 5DSY 网站主页:http://www.5dsy.cn/game
 * 渠道编号:327
 * 本应用商店一共九款app,将全部链接加入page中
 * app的详细信息存放在json中
 */


public class Dsy5 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	if(page.getUrl().toString().equals("http://www.5dsy.cn/game"))
	{
		String str1=SinglePageDownloader.getHtml("http://img.5dsy.cn/5dsy.icon");
		try {
			String str = new String(str1.getBytes("ISO-8859-1"), "UTF-8");
			System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 		List<String> urlList=new ArrayList<String>();
 		for(int i=1;i<=3;i++)
 		{
 			urlList.add("http://www.5dsy.cn/game/100000"+i);
 		}
// 		urlList.add("http://www.5dsy.cn/game/1000053");
// 		urlList.add("http://www.5dsy.cn/game/1000051");
 		urlList.add("http://www.5dsy.cn/game/1000050");
// 		urlList.add("http://www.5dsy.cn/game/1000046");
 		urlList.add("http://www.5dsy.cn/game/1000048");
 		urlList.add("http://www.5dsy.cn/game/1000045");
 		page.addTargetRequests(urlList);
 		return null;
	}
	//获取app的gameId
	List<Apk> apkList=new ArrayList<Apk>();
	String gameId=page.getHtml().xpath("//input[@id='gameId']/@value").toString();
	System.out.println(gameId);
	//构造json的url链接
	String jsonUrl="https://api.5dsy.cn/game/"+gameId;
	String downloadPage=SinglePageDownloader.getHtml(jsonUrl);
	//构造标准json格式文本
	String rawStr=(downloadPage.replace("{\"name\"", "[{\"name\"")).replace("}}", "}]}");
	downloadPage=rawStr;
	
	System.out.println(downloadPage);
	 Map<String, Object> urlMap = null;
	try{
	 urlMap = new ObjectMapper().readValue(downloadPage, Map.class);
     if (null != urlMap) {
    	 List<Map<String, Object>> infos = (List<Map<String, Object>>) urlMap.get("data");
         for(int j=0;j<infos.size();j++)
         {
        	 System.out.println(infos.get(j));
        	 apkList.add(Dsy5_Detail.getApkDetail(infos.get(j)));
         }
     }
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	page.putField("apks", apkList);
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
