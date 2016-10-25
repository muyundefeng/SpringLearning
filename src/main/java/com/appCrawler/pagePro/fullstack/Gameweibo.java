package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Gameweibo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 新浪微游戏 http://game.weibo.cn/
 * Gameweibo #305
 * @author tianlei
 */


public class Gameweibo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> cacheSet = Sets.newHashSet();	
		if("http://game.weibo.cn/category.php?id=27".equals(page.getUrl().toString())){
			for(int i = 27;i<=32;i++){
				page.addTargetRequest("http://game.weibo.cn/category.php?cate="+i+"&action=categamelist&page=1&size=1000");
		    }
		}
		//提取页面信息
		if(page.getUrl().regex("http://game.weibo.cn/category.*").match()){
			List<Apk> apk =Gameweibo_Detail.getApkDetail(page);		
			page.putField("apks", apk);
			if(page.getResultItems().get("apks") == null){
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
