package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Cnd8_Detail;
import com.appCrawler.pagePro.apkDetails.Edown7_Detail;
import com.appCrawler.pagePro.apkDetails.Veryhuo_Detail;
import com.appCrawler.pagePro.apkDetails.Wei2008_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 星星软件园
 * 网站主页：http://s.cnd8.com/cse/search?s=558319935242865942&entry=1&ie=gbk&q=qq
 * Aawap #579
 * @author lisheng
 */
public class Cnd8 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://s.cnd8.com/cse/search.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			//System.out.println(apps);;
			List<String> pages=page.getHtml().links("//div[@id='footer']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.cnd8\\.com/downinfo/\\d+\\.html").match())
			{
				return Cnd8_Detail.getApkDetail(page);
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