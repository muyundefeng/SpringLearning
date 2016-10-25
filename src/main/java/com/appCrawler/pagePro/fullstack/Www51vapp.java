package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Www51vapp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓商店  http://www.51vapp.com/
 * Www51vapp #121
 * @author DMT
 */


public class Www51vapp implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(page.getUrl().toString().equals("http://www.51vapp.com/")){
			page.addTargetRequest("http://www.51vapp.com/market/cat/apply.vhtml?p=1");// 应用
			page.addTargetRequest("http://www.51vapp.com/market/cat/game.vhtml?p=1");// 游戏
		}
	
		if(page.getUrl().regex("http://www\\.51vapp\\.com/market/cat/apply\\.vhtml\\?p=.*").match()
				|| page.getUrl().regex("http://www\\.51vapp\\.com/market/cat/game\\.vhtml\\?p=.*").match())
		{
			List<String> url=page.getHtml().xpath("//div[@class='l_list']/ul/li/a/@href").all();
			
			List<String> url2=page.getHtml().xpath("//div[@class='pager']/a/@href").all();	
			url.addAll(url2);
			LOGGER.info("This page add "+url.size()+" pages");
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url);
					for (String temp : cacheSet) {
						if(!temp.contains("http://cdn.sinoimage.com/download/apks") && PageProUrlFilter.isUrlReasonable(temp))
									page.addTargetRequest(temp);
					}

		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.51vapp\\.com/market/apps/detail.*").match()){
	
			
			Apk apk = Www51vapp_Detail.getApkDetail(page);
			
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
