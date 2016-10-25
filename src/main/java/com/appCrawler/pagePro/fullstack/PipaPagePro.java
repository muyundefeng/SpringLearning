package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

  

import com.appCrawler.pagePro.apkDetails.PipaPagePro_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 *  琵琶网：http://www.pipaw.com/
 * @author DMT
 *
 */


public class PipaPagePro implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(PipaPagePro.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.pipaw.com/")) {
			page.addTargetRequest("http://www.pipaw.com/game/game-android/");// 添加游戏索引
			page.addTargetRequest("http://www.pipaw.com/apps/android/");// 添加应用索引

		}

		if (page.getUrl()
				.regex("http://www.pipaw.com/game/game-android-\\d+/")
				.match()
				|| page.getUrl()
						.regex("http://www.pipaw.com/apps/android-\\d+/")
						.match()
						|| page.getUrl().toString().equals("http://www.pipaw.com/game/game-android/")
						|| page.getUrl().toString().equals("http://www.pipaw.com/apps/android/")) {// 获取所有分类页
			//游戏类详情链接
			List<String> urlList_game = page.getHtml()
					.links("//div[@class='game_bank_list']").all();
			//应用类详情链接
			List<String> urlList_soft = page.getHtml()
					.links("//div[@class='lib_left_list']").all();
			List<String> pageList = page.getHtml()
					.links("//ul[@class='yiiPager']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList_game);
			cacheSet.addAll(urlList_soft);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)
						&& !url.contains("?")) {
					page.addTargetRequest(url);
				}
			}

		}
		
	
//http://wy\\.pipaw\\.com/[a-zA-Z0-9]+
//http://www.pipaw.com/wjzjmy/
//http://wy.pipaw.com/game210/
//http://www.pipaw.com/apps/1.html  
		if(page.getUrl().regex("http://wy\\.pipaw\\.com/[a-zA-Z0-9]+/").match()
				|| page.getUrl().regex("http://www.pipaw.com/.*").match()){			
			//System.out.println( "in"+" "+page.getUrl().toString());
			Apk apk = PipaPagePro_Detail.getApkDetail(page);
			
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
