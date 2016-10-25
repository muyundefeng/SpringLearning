package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import com.appCrawler.pagePro.apkDetails.Nubia_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #177 Nubia
 * http://app.nubia.cn/app
 * @author DMT
 *
 */
public class Nubia implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Nubia.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if(page.getUrl().regex("http://app\\.nubia\\.cn/app").match())
		{
			List<String> urls =page.getHtml().links().regex("http://app\\.nubia\\.cn/app/.*").all() ;
			
	 		
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urls);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
			
		}
			
		//提取页面信息
		if(page.getUrl().regex("http://app\\.nubia\\.cn/app/.*").match()){
			
			Apk apk = Nubia_Detail.getApkDetail(page);
			
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
