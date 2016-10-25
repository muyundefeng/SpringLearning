package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Appzyxz_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 自由下载
 * 网站主页：http://www.zyxz.com/
 * Aawap #676
 * @author lisheng
 */
public class Appzyxz implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://so.zyxz.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='footer']").all();
			page.addTargetRequests(pages);
			System.out.println(pages);
		}
		//提取页面信息
			if(page.getUrl().regex("http://www.zyxz.com/.+/\\d+.html").match())
			{
				return Appzyxz_Detail.getApkDetail(page);
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
