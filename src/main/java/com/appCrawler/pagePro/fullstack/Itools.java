package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Itools_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * itools http://android.itools.cn/
 * Itools #192
 * @author DMT
 */


public class Itools implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://android.itools.cn/")) {
			page.addTargetRequest("http://android.itools.cn/games/quanbu/quanbu/1/page/1");// 添加游戏索引
			page.addTargetRequest("http://android.itools.cn/apps/quanbu/quanbu/1/page/1");// 添加应用索引

		}

		if (page.getUrl()
				.regex("http://android\\.itools\\.cn/games/quanbu/quanbu/1/page/\\d+")
				.match()
				|| page.getUrl()
						.regex("http://android\\.itools\\.cn/apps/quanbu/quanbu/1/page/\\d+")
						.match()) {// 获取所有分类页
			List<String> urlList = page
					.getHtml()
					.links("//ul[@class='ios_app_list']")
					.regex("http://android\\.itools\\.cn/details/.+")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pages clearfix']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		
	
		//提取页面信息 http://android.itools.cn/details/apps/com.speedsoftware.rootexplorer
		if(	page.getUrl().regex("http://android\\.itools\\.cn/details/.+").match() ){
	
			
			Apk apk = Itools_Detail.getApkDetail(page);
			
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
