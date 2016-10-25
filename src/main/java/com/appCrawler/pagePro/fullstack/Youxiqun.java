package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Youxiqun_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 游戏群 http://www.youxiqun.com/
 * 渠道编号:383
 * @author DMT
 */


public class Youxiqun implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getUrl().toString().equals("http://www.youxiqun.com/"))
	{
		page.addTargetRequest("http://www.youxiqun.com/gamelist/2-0-0-0-0-0-1.html");
		return null;
	}
	if(page.getUrl().regex("http://www\\.youxiqun\\.com/gamelist/2-0-0-0-0-0-\\d{1,3}\\.html").match())
	{
 		List<String> apkList=page.getHtml().xpath("//ul[@class='game-poker']/li/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@id='page_change']/ul/li/a/@href").all();
 		apkList.addAll(pageList);
 		//urlList.addAll(urlList3);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.youxiqun\\.com/game/\\.*").match())
		{
			
			Apk apk = Youxiqun_Detail.getApkDetail(page);
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
