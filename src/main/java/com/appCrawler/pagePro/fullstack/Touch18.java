package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.bcel.verifier.exc.StaticCodeConstraintException;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Touch18_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 18touch超好玩游戏中心
 * 网站主页：http://game.18touch.com/game/1-0-0-0-0-1
 * Aawap #395
 * @author lisheng
 */


public class Touch18 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);
	private static int pageNo=1;
	public Apk process(Page page) {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	if(page.getHtml().links().regex("http://game\\.18touch\\.com/game/1-0-0-0-0-\\d+").match())
	{
 		List<String> apkList=page.getHtml().xpath("//div[@class='a-list-wrapper']/ul/li/a/@href").all();
 		//List<String> pageList=page.getHtml().xpath("//div[@class='mod-block-fd']/ul/li/a/@href").all();
 		//System.out.println(apkList);
 		//System.out.println(pageList);
 		String url1="http://game.18touch.com/game/1-0-0-0-0-"+pageNo;
 		String html=SinglePageDownloader.getHtml(url1);
 		//page.addTargetRequest("http://game.18touch.com/game/1-0-0-0-0-2");
 		if(html.contains("a-list-wrapper"))
 		{
 			System.out.println("*****"+pageNo);
 			pageNo++;
 			page.addTargetRequest("http://game.18touch.com/game/1-0-0-0-0-"+pageNo);
 		}
 		//apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://game\\.18touch\\.com/game/\\d+\\.html").match())
		{
			
			Apk apk = Touch18_Detail.getApkDetail(page);
			
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
