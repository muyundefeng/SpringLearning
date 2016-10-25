package com.appCrawler.pagePro.fullstack;


import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;









import com.appCrawler.pagePro.apkDetails.Sina_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 新浪 http://app.sina.com.cn/app_index.php?f=p_dh
 * Sina #98
 * @author DMT
 */


public class Sina implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Sina.class);

	public Apk process(Page page) {
	
		try{//23：59开始休眠30分
			 Calendar now = Calendar.getInstance();  			
		     int hour = now.get(Calendar.HOUR_OF_DAY);
		     int minute = now.get(Calendar.MINUTE);
		     if(hour == 23 && minute ==59){
		    	 LOGGER.info("Sleeping");
		    	 Thread.sleep(1000*60*30);
		    	 LOGGER.info("Wake up");
		     }
		}catch (Exception e) {
			// TODO: handle exception
		}
		//System.out.println(page.getHtml().toString());
		try {
			Thread.sleep(2500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		List<String> urls =page.getHtml().links().regex("http://app\\.sina\\.com\\.cn/.*").all() ;
		
 		
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urls);
		for (String temp : cacheSet) {
			if(!temp.contains("http://app.sina.com.cn/download.php?appID=") && PageProUrlFilter.isUrlReasonable(temp))				
				page.addTargetRequest(temp);
		}

//		page.addTargetRequests(urls);
		
	
		//提取页面信息
		if(page.getUrl().regex("http://app\\.sina\\.com\\.cn/appdetail\\.php.*").match()){
	
			
			Apk apk = Sina_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
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
