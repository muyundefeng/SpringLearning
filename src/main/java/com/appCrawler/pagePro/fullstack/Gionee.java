package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Gionee_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 金立游戏中心（单机）  http://game.gionee.com/
 * Gionee #350
 * @author tianlei
 */


public class Gionee implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		//System.out.println(page.getHtml().toString());
		if("http://game.gionee.com/".equals(page.getUrl().toString())){
			cacheSet.add("http://game.gionee.com/Front/Category/index/?cku=1289938780_null&action=visit&object=category&intersrc=homepage");
		}else{
			List<String> urlList=page.getHtml().xpath("//ul[@class='game_list clearfix']//a[1]/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@class='pagination paddingright10']/a/@href").all();
			cacheSet.addAll(urlList);
			cacheSet.addAll(nextPage);
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://game.gionee.com/Front/Index/.*").match()){			
			Apk apk = Gionee_Detail.getApkDetail(page);		
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
			}
		}else{
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
