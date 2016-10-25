package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Paojiao_Detail;
import com.appCrawler.pagePro.apkDetails.Paojiao_Detail2;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 泡椒网手机软件下载 www.paojiao.cn/
 * Paojiao #133	网页打不开，未完成
 * (1)有网游和单击软件两种详细页面
 * 
 * 2015年11月19日15:33:03 有网游，单击，软件
 * 三种类别的翻页和详情页的提取是一样的
 * @author DMT
 */


public class Paojiao implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		
		if(page.getUrl().toString().equals("http://www.paojiao.cn/")){
			page.addTargetRequest("http://www.paojiao.cn/wangyou/list_default____1.html");//网游
			page.addTargetRequest("http://www.paojiao.cn/danji/list_default____1.html"); // 单击
			page.addTargetRequest("http://www.paojiao.cn/ruanjian/list_0__new_list_1.html");//软件
		}
	
		if(page.getUrl().regex("http://www\\.paojiao\\.cn/.+/list.+").match())
		{
			List<String> urlList_Detail=page.getHtml().links("//ul[@class='recommend']").all();
			List<String> urlList_Page=page.getHtml().links("//div[@class='page']").all();					
			urlList_Detail.addAll(urlList_Page);			
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList_Detail);
			for (String temp : cacheSet) {
				if(PageProUrlFilter.isUrlReasonable(temp))				
					page.addTargetRequest(temp);
			}
			
		}
	
		
		//提取页面信息
		if(	page.getUrl().regex("http://www\\.paojiao\\.cn/ruanjian.*").match()
				|| page.getUrl().regex("http://www\\.paojiao\\.cn/pojie.*").match()
	            || page.getUrl().regex("http://www\\.paojiao\\.cn/danji.*").match() 
	            || page.getUrl().regex("http://www\\.paojiao\\.cn/youxi.*").match()){
	
			Apk apk = null;
			
			if(	page.getUrl().regex("http://www\\.paojiao\\.cn/youxi.*").match()){
				
				
				 apk = Paojiao_Detail2.getApkDetail(page);
			}
			else   apk = Paojiao_Detail.getApkDetail(page);
			
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
