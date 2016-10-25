package com.appCrawler.pagePro.fullstack;
/**
 * playcn爱游戏 http://www.play.cn/
 * Play #96
 * 页面较多，修改为从索引页爬取 2015年11月4日16:32:55
 * @author DMT
 */

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.appCrawler.pagePro.apkDetails.Play_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;


public class Play implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Play.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.play.cn/")){
			page.addTargetRequest("http://www.play.cn/game/searchgame?type_code=11&class_code=-1&order_type=0#gamelist");//单击
			page.addTargetRequest("http://www.play.cn/game/searchgame?type_code=12&class_code=-1&order_type=0#gamelist");//网游
		}						 
		if(page.getUrl().regex("http://www\\.play\\.cn/game/searchgame.*").match()){
			List<String> url_pageList = page.getHtml().links("//ul[@class='pages']").all();
			
			List<String> url_detail = page.getHtml().links("//ul[@class='m_list_game fix']").all();
			url_pageList.addAll(url_detail);
		
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(url_pageList);
		for (String temp : cacheSet) {
			if(!temp.endsWith("jpg") && !temp.startsWith("http://www.play.cn/game/download"))				
				page.addTargetRequest(temp);
		}

		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.play\\.cn/game/gamedetail.*").match()){
	
			
			Apk apk = Play_Detail.getApkDetail(page);
			
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
