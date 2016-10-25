package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Gooyo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Gooyo http://www.gooyo.com/
 * Gooyo #201
 * @author DMT
 * @modify author lisheng
 */


public class Gooyo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		
		if("http://www.gooyo.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.gooyo.com/soft_list_0_1_3_1.html");
			page.addTargetRequest("http://www.gooyo.com/game_list_0_2_3_1.html");
			return null;
		}
		if(page.getUrl().regex("http://www\\.gooyo\\.com/soft_list_0_1_3_\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.gooyo\\.com/game_list_0_2_3_\\d+\\.html").match())
		{
		
			List<String> apks=page.getHtml().xpath("//div[@class='boxF-mid']/div/div/div/a/@href").all() ;
			System.out.println(apks);
			List<String> pages=page.getHtml().xpath("//div[@class='pageNumber']/a/@href").all();
			apks.addAll(pages);
			for(String url:apks)
			{
				if(PageProUrlFilter.isUrlReasonable(url))
				{
					page.addTargetRequest(url);
				}
			}
		}
		
		
//		nexturl =page.getHtml().xpath("//div[@class='pageNumber']").toString() ;
//		if(nexturl!=null){
//			nexturl=getLastUrl(nexturl);
//		}	
//		Set<String> cacheSet = Sets.newHashSet();
//		if(page.getUrl().toString().equals("http://www.gooyo.com/soft_list_0_1_3_1.html"))
//			cacheSet.add("http://www.gooyo.com/game.html");
//		if(nexturl!=null){ 
//		cacheSet.add(nexturl);
//		}
//			
//		cacheSet.addAll(urls);
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url) 
//					&& !url.contains("http://www.gooyo.com/down")){	
//				page.addTargetRequest(url);
//			}
//		
//		}
		//http://www.gooyo.com/game_detail_56347_2_3.html
		//提取页面信息
		if(page.getUrl().regex("http://www\\.gooyo\\.com/soft_detail_.*\\.html" ).match()
		   || (page.getUrl().regex("http://www\\.gooyo\\.com/game_detail_.*\\.html" ).match()))
		   {
			Apk apk = Gooyo_Detail.getApkDetail(page);
			
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
	
	private static String getLastUrl(String str){
    	String tmp=null;    	
		String regex="<a href=\"([^\"]+)\">下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
	
}
