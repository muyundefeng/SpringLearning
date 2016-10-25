package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Wostore_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 联通沃商店  http://mstore.wo.com.cn/index.action
 * Wostore #301
 * @author tianlei
 */


public class Wostore implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
  
		if("http://mstore.wo.com.cn/index.action".equals(page.getUrl().toString())){
			cacheSet.add("http://mstore.wo.com.cn/twoCategory.action?catIndex1=154&currentModule=category#");
			cacheSet.add("http://mstore.wo.com.cn/twoCategory.action?catIndex1=47&currentModule=category");
			cacheSet.add("http://mstore.wo.com.cn/twoCategory.action?catIndex1=94&currentModule=category");
			cacheSet.add("http://mstore.wo.com.cn/twoCategory.action?catIndex1=108&currentModule=category");
	 
		}
		else{
			String nextPage = null;
			List<String> urlList=page.getHtml().xpath("//li[@class='list_a']/a/@href").all();
			if(urlList.size() > 0 ){
				urlList.addAll(page.getHtml().xpath("//li[@class='list_b']/a/@href").all());
			}

			nextPage = page.getHtml().xpath("//td[@class='fanye textright']/a/@href").toString();
			cacheSet.addAll(urlList);
			if(nextPage != null){
				cacheSet.add(nextPage);
				}
		}
		for(String url : cacheSet){		
				page.addTargetRequest(url);			
		}
		

	
		//提取页面信息
		if(page.getUrl().regex("http://mstore.wo.com.cn/appDetail.action.*").match()){
	
			Apk apk = Wostore_Detail.getApkDetail(page);		
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
