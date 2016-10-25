package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Bar52_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 渠道编号:317
 * 名称:玩吧
 * 网站主页:http://www.52bar.com/
 *
 */


public class Bar52 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getUrl().toString().equals("http://www.52bar.com/"))
	{
 		List<String> urlList2=page.getHtml().xpath("//div[@id='in_Top_GameAll2']/div[3]/ul/li/a/@href").all();
 		page.addTargetRequests(urlList2);
	}
		if(page.getUrl().regex("http://www\\.52bar\\.com/game\\.php\\?mod=game.*").match())
		{
			
			Apk apk = Bar52_Detail.getApkDetail(page);
			
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
