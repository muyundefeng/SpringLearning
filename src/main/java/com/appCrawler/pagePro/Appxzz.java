package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Appxxz_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * app下载站
 * 网站主页：http://www.appxzz.com/
 * Aawap #677
 * @author lisheng
 */
public class Appxzz implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.appxzz.com/index.php\\?tpl=search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='app-list boutique-cnt']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pagebar']").all();
	 		page.addTargetRequests(apps);
	 		page.addTargetRequests(pages);
		}
		//提取页面信息
			if(page.getUrl().regex("http://www.appxzz.com/app/\\d+.html").match())
			{
				return Appxxz_Detail.getApkDetail(page);
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
