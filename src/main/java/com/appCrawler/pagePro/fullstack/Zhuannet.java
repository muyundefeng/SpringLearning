package com.appCrawler.pagePro.fullstack;
/*
 * 有软件和游戏两类索引页面
 * #73*/

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.appCrawler.pagePro.apkDetails.Zhuannet_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;


public class Zhuannet implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(15).
			setSleepTime(PropertiesUtil.getInterval());
//			.addCookie("Cookie", "bdshare_firstime=1431568233933; Hm_lvt_cb5b80cb9f5736995750b4467fe163d0=1447293771; cck_lasttime=1447293770532; cck_count=0; a1235_pages=1; a1235_times=4");

	private Logger LOGGER = LoggerFactory.getLogger(Zhuannet.class);

	public Apk process(Page page) {
	
		page.addTargetRequest("http://apk.zhuannet.com/soft/690.html");
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://apk.zhuannet.com/soft/")){
			page.addTargetRequest("http://apk.zhuannet.com/game/2_0.html");
			page.addTargetRequest("http://apk.zhuannet.com/soft/2_0.html");
		}
		if(page.getUrl().regex("http://apk\\.zhuannet\\.com/soft/\\d+_0\\.html").match()
				|| page.getUrl().regex("http://apk\\.zhuannet\\.com/game/\\d+_0\\.html").match()){
			
			List<String> urls =page.getHtml().links("//div[@class='app_list_right']").regex("http://apk\\.zhuannet\\.com/soft/\\d+\\.html").all();
			List<String> urls2 =page.getHtml().links("//div[@class='app_list_right']").regex("http://apk\\.zhuannet\\.com/game/\\d+\\.html").all();			
			List<String> pageList = page.getHtml().links("//p[@class='list_pager']").all();
			urls.addAll(urls2);
			LOGGER.info("get "+urls.size()+" page");
			urls.addAll(pageList);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urls);
			for (String temp : cacheSet) {
				if(PageProUrlFilter.isUrlReasonable(temp) 
						&& !temp.contains("down.aspx?"))				
					page.addTargetRequest(temp);
			}
		
		}
		//提取页面信息
		if(page.getUrl().regex("http://apk\\.zhuannet\\.com/soft/\\d+\\.html").match() 
				|| page.getUrl().regex("http://apk\\.zhuannet\\.com/game/\\d+\\.html").match()){
			
			
			Apk apk = Zhuannet_Detail.getApkDetail(page);
			
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
