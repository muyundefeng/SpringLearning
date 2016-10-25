package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Wuji_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 无机游戏
 * 网站主页：http://www.wuji.com/gamelib/shouji_1_0_0_0_1.html
 * Aawap #673
 * @author lisheng
 */
public class Wuji implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://search.wuji.com/.*").match())
		{
			List<String> apps=page.getHtml().links("//p[@class='yxname']").all();
			page.addTargetRequests(apps);
		}
		//提取页面信息
			if(page.getUrl().regex("http://www.wuji.com/gamelib/.*").match())
			{
				return Wuji_Detail.getApkDetail(page);
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
