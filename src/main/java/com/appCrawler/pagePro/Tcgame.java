package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Tcgame_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 天畅游戏
 * 网站主页：http://www.tcgame.com.cn/
 * @id 533
 * @author lisheng
 */
public class Tcgame implements PageProcessor{
	Site site = Site.me().setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://so\\.tcgame\\.com\\.cn/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result-item result-game-item']").all();
			for(String str:apps)
			{
				if(str.startsWith("http://m."))
				{
					page.addTargetRequest(str.replace("http://m.", "http://www"));
				}
				else{
					page.addTargetRequest(str);
				}
			}
			List<String> pages=page.getHtml().links("//div[@id='pageFooter']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.tcgame\\.com.cn/danji/.*\\.html").match()
				||page.getUrl().regex("http://www\\.tcgame\\.com.cn/wangyou/.*\\.html").match())
			{
				return Tcgame_Detail.getApkDetail(page);
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
