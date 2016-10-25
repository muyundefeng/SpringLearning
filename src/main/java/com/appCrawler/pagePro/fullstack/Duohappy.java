package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 多乐市场
 * 网站主页：http://www.duohappy.cn/zt/tv/app.html
 * Aawap #647
 * @author lisheng
 */


public class Duohappy implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Duohappy.class);

	public Apk process(Page page) {
		List<Apk> apks = new ArrayList<Apk>();
		if("http://www.duohappy.cn/zt/tv/app.html".equals(page.getUrl().toString()))
		{
			List<String> appNames = page.getHtml().xpath("//div[@class='wp-container']/ul[1]/li/div[2]/h3/a/text()").all();
			List<String> appSizes = page.getHtml().xpath("//div[@class='wp-container']/ul[1]/li/div[2]/div[1]/text()").all();
			List<String> appDownloadurls = page.getHtml().xpath("//div[@class='wp-container']/ul[1]/li/div[2]/div[2]/a/@href").all();
			List<String> appNames1 = page.getHtml().xpath("//div[@class='wp-container']/ul[2]/li/div[2]/h3/a/text()").all();
			List<String> appSizes1 = page.getHtml().xpath("//div[@class='wp-container']/ul[2]/li/div[2]/div[1]/text()").all();
			List<String> appDownloadurls1 = page.getHtml().xpath("//div[@class='wp-container']/ul[2]/li/div[2]/div[2]/a/@href").all();
			appNames .addAll(appNames1);
			appSizes.addAll(appSizes1);
			appDownloadurls.addAll(appDownloadurls1);
			for(int i=0;i<appNames.size();i++)
			{
				Apk apk = new Apk(appNames.get(i), null, appDownloadurls.get(i), null, null,null, null, null,null, null, null, null);
				apk.setAppSize(appSizes.get(i));
				apks.add(apk);
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
