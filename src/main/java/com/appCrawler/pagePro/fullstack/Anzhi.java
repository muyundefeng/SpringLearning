package com.appCrawler.pagePro.fullstack;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Anzhi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 安智网 http://www.anzhi.com/index.html Anzhi #127 下载apk的url需要手动构造
 * 页面分类通过js获取
 * 该网站在每天凌晨的几秒钟会出现获取不到页面的现象，因此在每天晚上23:59分开始，每个线程sleep两分钟
 * @author DMT
 */

public class Anzhi implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try{
			 Calendar now = Calendar.getInstance();  			
		     int hour = now.get(Calendar.HOUR_OF_DAY);
		     int minute = now.get(Calendar.MINUTE);
		     if(hour == 23 && minute ==59){
		    	 LOGGER.info("Sleeping");
		    	 Thread.sleep(1000*60*2);
		    	 LOGGER.info("Wake up");
		     }
		}catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.anzhi.com")) {
			page.addTargetRequest("http://www.anzhi.com/sort_67_1_hot.html");// 应用类的其中一类
			page.addTargetRequest("http://www.anzhi.com/sort_21_1_hot.html");// 游戏类的其中一类
		}

		if (page.getUrl().toString().equals("http://www.anzhi.com/sort_67_1_hot.html")) {// 添加所有的应用类索引
			Html html = new Html(SinglePageDownloader.getHtml("http://www.anzhi.com/widgetcat_1.html"));
			page.addTargetRequests(html.links().all());
		}

		if (page.getUrl().toString().equals("http://www.anzhi.com/sort_21_1_hot.html")) {// 添加所有的游戏类索引
			Html html = new Html(SinglePageDownloader.getHtml("http://www.anzhi.com/widgetcat_2.html"));
			page.addTargetRequests(html.links().all());
		}

		if (page.getUrl().regex("http://www\\.anzhi\\.com/sort_\\d+_\\d+_hot\\.html")
				.match()) {// 获取所有分类页

			List<String> urlList = page.getHtml()
					.links("//div[@class='app_list border_three']")
					.regex("http://www\\.anzhi\\.com/soft_\\d+\\.html").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pagebars']")
					.regex("http://www\\.anzhi\\.com/sort_\\d+_\\d+_hot\\.html")
					.all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (!url.contains("www.anzhi.com/dl_app.php?s=")
						&& PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		// 提取页面信息	http://www.anzhi.com/soft_622294.html
		if (page.getUrl().regex("http://www\\.anzhi\\.com/soft_\\d+\\.html").match()) {

			Apk apk = Anzhi_Detail.getApkDetail(page);

			page.putField("apk", apk);
			if (page.getResultItems().get("apk") == null) {
				page.setSkip(true);
			}
		} else {
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
	
	public static void main(String[] args){
		Calendar now = Calendar.getInstance();  
		System.out.println("年: " + now.get(Calendar.YEAR));  
	     System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
	    System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));  
		System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));  
        System.out.println("分: " + now.get(Calendar.MINUTE));  
        System.out.println("秒: " + now.get(Calendar.SECOND));  
        try {
        	int hour = now.get(Calendar.HOUR_OF_DAY);
   	     int minute = now.get(Calendar.MINUTE);
           if(hour == 17 && minute >=1){
   	    	 System.out.println("Sleeping");
   	    	 Thread.sleep(5000);
   	    	 System.out.println("Wake up");
   	     }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        
	}
}
