package com.appCrawler.pagePro;

import java.util.List;
import java.util.Set;


import com.appCrawler.pagePro.apkDetails.Appgame_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 任玩堂
 * 网站主页：http://www.appgame.com/
 * 搜索接口：http://app.appgame.com/s.php?name=*#*#*#
 * @lisheng
 */
public class Appgame implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://app\\.appgame\\.com/s\\.php\\?name=.*").match()){
			List<String> apkList=page.getHtml().xpath("//div[@id='primary']/div/div/a/@href").all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apkList);
			for(String url : cacheSet){
			page.addTargetRequest(url);
			}
		}
		//提取页面信息
				if(page.getUrl().regex("http://app\\.appgame\\.com/game/\\d*\\.html").match())
				{
					
					return Appgame_Detail.getApkDetail(page);
					
					
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
