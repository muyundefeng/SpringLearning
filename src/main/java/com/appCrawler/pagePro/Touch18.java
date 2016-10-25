package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Touch18_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 18touch超好玩游戏中心
 * 网站主页：http://game.18touch.com/game/1-0-0-0-0-1
 * Aawap #395
 * @author lisheng
 */
public class Touch18 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://game\\.18touch\\.com/search\\?name=.*").match()){
			List<String> apkList=page.getHtml().xpath("//div[@class='appstore clearfix']/ul/li/div/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@class='mod-block-fd']/ul/li/a/@href").all();
			apkList.addAll(pageList);
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

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
