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
 * 快投
 * 网站主页：http://www.qcast.cn/topic-download.html
 * Aawap #593
 * @author lisheng
 */


public class Qcast implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Qcast.class);

	public Apk process(Page page) {
	
		if("http://www.qcast.cn/topic-download.html".equals(page.getUrl().toString()))
		{
			List<Apk> apks = new ArrayList<Apk>();
			
			List<String> downloadUrls = page.getHtml().xpath("//div[@class='download_content']/ul/li/div[@class='btn']/a/@href").all();
			List<String> names = page.getHtml().xpath("//div[@class='download_content']/ul/li/div[@class='info']/p[@class='info_title']/a/text()").all();
			List<String> versions = page.getHtml().xpath("//div[@class='download_content']/ul/li/div[@class='info']/p[2]/span/text()").all();
			List<String> appsizes = page.getHtml().xpath("//div[@class='download_content']/ul/li/div[@class='info']/p[3]/text()").all();
			List<String> updates = page.getHtml().xpath("//div[@class='download_content']/ul/li/div[@class='info']/p[4]/text()").all();

			for(int i=0;i<downloadUrls.size()-1;i++)
			{
				System.out.println(names.get(i));
				System.out.println(downloadUrls.get(i));
				System.out.println(versions.get(i));
				System.out.println(appsizes.get(i));
				Apk apk = new Apk(names.get(i),null,downloadUrls.get(i),null ,versions.get(i),appsizes.get(i),updates.get(i),null,null);
				apks.add(apk);
			}
			page.putField("apks", apks);
			if(page.getResultItems().get("apks") == null){
				page.setSkip(true);
				}
			
			
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
