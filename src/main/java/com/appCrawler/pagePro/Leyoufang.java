package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Leyoufang_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 乐游坊
 * 网站主页：http://leyoufang.com/androidgames
 * Aawap #675
 * @author lisheng
 */
public class Leyoufang implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://leyoufang.com/Search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='sgameitem']").all();
			page.addTargetRequests(apps);
			
		}
		//提取页面信息
			if(page.getUrl().regex("http://leyoufang.com/game/\\d+.html").match())
			{
				Leyoufang_Detail.getApkDetail(page);
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
