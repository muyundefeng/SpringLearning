package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mogu17_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 蘑菇游戏
 * 网站主页：http://www.17mogu.com/youxi/
 * Aawap #410
 * @author lisheng
 */


public class Mogu17 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.17mogu\\.com/youxi/\\?page=\\d+").match()
				||page.getUrl().toString().equals("http://www.17mogu.com/youxi/"))
		{
			List<String> apps=page.getHtml().xpath("//div[@class='task']/@onclick").all();
			for(String str:apps)
			{
				String url=str.replace("window.open('", "").replace("', '_blank')", "");
				System.out.println(url);
				page.addTargetRequest(url);
			}
	 		for(int i=0;;i++)
	 		{
	 			String url="http://www.17mogu.com/youxi/?page="+i;
	 			String string=SinglePageDownloader.getHtml(url);
	 			if(!string.contains("game-info"))
	 			{
	 				break;
	 			}
	 			else{
	 				page.addTargetRequest(url);
	 			}
	 		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.17mogu\\.com/games/game_details_\\d+\\.html").match())
		{
			
			Apk apk = Mogu17_Detail.getApkDetail(page);
			
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
