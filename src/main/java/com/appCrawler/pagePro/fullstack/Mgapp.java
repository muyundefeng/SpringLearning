package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mgapp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 应用酷 http://www.mgyapp.com/ Mgapp #119
 * 
 * @author DMT
 */

public class Mgapp implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
	
		if(page.getUrl().toString().equals("http://www.mgyapp.com")){
			page.addTargetRequest("http://www.mgyapp.com/games/page2/all");//游戏列表
			page.addTargetRequest("http://www.mgyapp.com/apps/page2/all");//软件列表
			
		}
		
		if(page.getUrl().regex("http://www\\.mgyapp\\.com/games/page\\d+/all").match()
				|| page.getUrl().regex("http://www\\.mgyapp\\.com/apps/page\\d+/all").match())
		{
			List<String> urlList=page.getHtml().xpath("//ul[@class='all-list']/li/p/a/@href").all();
			List<String> urlList1=page.getHtml().xpath("//div[@class='depage mt20 ac']/a/@href").all();
			urlList.addAll(urlList1);
			urlList.add("http://www.mgyapp.com/apps");
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for (String temp : cacheSet) {
				
				if(!temp.contains("http://www.mgyapp.com/down/") && PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
		}

		// 提取页面信息
		if (page.getUrl().regex("http://www\\.mgyapp\\.com/games.*").match()
				|| page.getUrl().regex("http://www\\.mgyapp\\.com/apps.*").match()) {

			Apk apk = Mgapp_Detail.getApkDetail(page);

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
}
