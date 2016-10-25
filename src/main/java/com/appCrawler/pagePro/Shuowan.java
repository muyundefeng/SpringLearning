package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Shuowan_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 说玩网
 * 网站主页：http://www.shuowan.com/list_0_1_0_0.html
 * 搜索接口：http://www.shuowan.com/so.html?q=qq&t=yx
 * Aawap #680
 * @author lisheng
 */
public class Shuowan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.shuowan.com/so.html.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='yxjg wrap2 yh overf']").all();
			for(String str:apps){
				if(!str.contains("down"))
				{
					page.addTargetRequest(str);
				}
			}
			List<String> pages=page.getHtml().links("//div[@class='fany wrap2 overf']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www.shuowan.com/game/\\d+\\.html").match())
		{
				return Shuowan_Detail.getApkDetail(page);
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
