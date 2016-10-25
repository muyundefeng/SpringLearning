package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.apache.xalan.xsltc.compiler.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Apk3 http://www.apk3.com/
 * Apk3 #107
 * 只有所有应用的索引，没有找到所有游戏的索引。
 * 游戏类的只有分类索引
 * @author DMT
 */


public class Apk3 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.apk3.com/")){
			page.addTargetRequest("http://www.apk3.com/list/112-1.html");//所有应用的索引
			page.addTargetRequest("http://www.apk3.com/game/");
		}
		if(page.getUrl().toString().equals("http://www.apk3.com/game/")){
			List<String> game_categorty = page.getHtml().links("//div[@class='c_menu']/div[1]/ul").all();//添加所有的游戏分类
			for(String url : game_categorty){
				url = url.toLowerCase();
				if(PageProUrlFilter.isUrlReasonable(url) && !url.contains("<")){
					page.addTargetRequest(url);
				}
			}	
		
		}
		if(page.getUrl().regex("http://www\\.apk3\\.com/list/\\d+-\\d+\\.html").match()){
			List<String> url_page = page.getHtml().links("//div[@class='pages']").all() ;
			List<String> url_detail = page.getHtml().links("//div[@class='l_app_list']").all() ;
			url_page.addAll(url_detail);		
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
		for(String url : cacheSet){
			url = url.toLowerCase();
			if(PageProUrlFilter.isUrlReasonable(url) && !url.contains("<")){
				page.addTargetRequest(url);
			}
		}		
		}
		//提取页面信息
		if(page.getUrl().regex("http://www\\.apk3\\.com/soft.*").match()
				|| page.getUrl().regex("http://www\\.apk3\\.com/game.*").match()){
	
			
			Apk apk = Apk3_Detail.getApkDetail(page);
			
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
