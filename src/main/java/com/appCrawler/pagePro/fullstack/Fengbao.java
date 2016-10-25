package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Fengbao_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 风暴网
 * 网站主页：http://www.fengbao.com/
 * Aawap #539
 * @author lisheng
 */


public class Fengbao implements PageProcessor{

	Site site = Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.fengbao.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.fengbao.com/android/recommend/index.php?ac=recommend&orderby=weeknum&page=1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.fengbao\\.com/android/recommend/index\\.php\\?ac=recommend&orderby=weeknum&page=\\d+").match())
		{
	 		List<String> apps=page.getHtml().links("//div[@class='rebang_con']").all();
	 		
			for(String url : apps){
				if(!url.contains("down")){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.fengbao\\.com/android/.*/\\d+\\.html").match())
		{
			
			Apk apk = Fengbao_Detail.getApkDetail(page);
			
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
