package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Xdowns_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 绿盟下载
 * 网站主页： http://www.xdowns.com/soft/184/phone/Android/
 * Aawap #492
 * @author lisheng
 */
public class Xdowns implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://tag\\.xdowns\\.com/tag/.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@id='searchpageTitle']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='searchpageReadList']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.xdowns\\.com/soft/184/phone/Android/\\d+/Soft_\\d+\\.html").match())
			{
				return Xdowns_Detail.getApkDetail(page);
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
