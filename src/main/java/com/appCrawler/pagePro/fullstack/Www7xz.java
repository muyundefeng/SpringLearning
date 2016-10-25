package com.appCrawler.pagePro.fullstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Www7xz_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 7匣子 http://www.7xz.com Www7xz #135
 * 
 * @author DMT
 */

public class Www7xz implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Www7xz.class);

	public Apk process(Page page) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.7xz.com/")){
			page.addTargetRequest("http://www.7xz.com/apps/1?sort=rate");
		}

		if(page.getUrl().regex("http://www\\.7xz\\.com/apps/\\d+\\?sort=rate").match()){//匹配索引页
			List<String> urlList =page.getHtml().links("//ul[@class='row gamelist clearfix mg10']").regex("http://www\\.7xz\\.com/.*/").all() ;
			List<String> pageList =page.getHtml().links("//ul[@class='pagination']").regex("http://www\\.7xz\\.com/apps/\\d+\\?sort=rate").all() ;
			
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url) && !url.contains("add=/apps/")){
					page.addTargetRequest(url);
				}
			}
		}
		// 提取页面信息
		if (page.getUrl().regex("http://www\\.7xz\\.com/.*/").match()) {
			Apk apk = Www7xz_Detail.getApkDetail(page);
			page.putField("apk", apk);
			if (page.getResultItems().get("apk") == null) {
				page.setSkip(true);
			}
		} else {
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
