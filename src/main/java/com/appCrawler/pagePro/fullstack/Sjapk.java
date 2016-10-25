package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.appCrawler.pagePro.apkDetails.Sjapk_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 卓乐 http://www.sjapk.com/
 * Sjapk #93
 * 2015年11月4日15:49:29 页面过多，使用索引页爬取
 * 爬取较多，增加重试次数
 * @author DMT
 */

public class Sjapk implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(10).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Sjapk.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.sjapk.com/")){
			page.addTargetRequest("http://www.sjapk.com/T-1-8-1-1.html");//添加索引页
		}
		if(page.getUrl().toString().equals("http://www.sjapk.com/T-1-8-1-1.html")){//添加所有的分类索引页
			List<String> url_gameList = page.getHtml().links("//div[@class='down_l']/div[1]").all();//添加所有游戏的分类
			List<String> url_softlList = page.getHtml().links("//div[@class='down_l']/div[2]").all();//添加所有软件的分类
			url_gameList.addAll(url_softlList);
			page.addTargetRequests(url_gameList);
		}
		if(page.getUrl().regex("http://www\\.sjapk\\.com/T.*\\.html").match()){
		List<String> url_page = page.getHtml().links("//div[@class='bottom_down']").all();//添加翻页
		List<String> url_detai = page.getHtml().links("//div[@class='main_down']").all();//添加详情页
		url_page.addAll(url_detai);
		
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(url_page);
		for (String temp : cacheSet) {
			if(!temp.contains("http://www.sjapk.com/GetApk.asp?gameid=") && PageProUrlFilter.isUrlReasonable(temp))
					
			page.addTargetRequest(temp);
		}

		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.sjapk\\.com/[0-9]+\\.html").match()){
	
			
			Apk apk = Sjapk_Detail.getApkDetail(page);
			
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
