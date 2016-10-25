package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Apppc18_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * pc18
 * 网站主页：http://www.pc18.com/
 * Aawap #672
 * @author lisheng
 */

public class Apppc18 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.pc18.com/index.php\\?m=search.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='search-list1']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='page-sy']").all();
			page.addTargetRequests(pages);
		}
		//提取页面信息
		if(page.getUrl().regex("http://www.pc18.com/android/\\d+\\.html").match())
		{
				return Apppc18_Detail.getApkDetail(page);
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
