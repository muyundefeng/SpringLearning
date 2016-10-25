package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game18wan_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 应用
 * 网站主页：http://www.18wan.cn/
 * Aawap #591
 * @author lisheng
 */
public class Game18wan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.18wan.cn/android.php\\?ac=search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@id='tabcontentSearch']").all();
			for(String str:apps)
			{
				if(!str.contains("ac=down"))
				{
					page.addTargetRequest(str);
				}
			}
			//page.addTargetRequests(apps);
			
		}
		if(page.getUrl().regex("http://www.18wan.cn/android.php\\?ac=detail.*").match())
		{
				return Game18wan_Detail.getApkDetail(page);
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
