package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Hdpfans_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 高清范
 * 网站主页：http://down.hdpfans.com/
 * Aawap #644
 * @author lisheng
 */
public class Hdpfans implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://down.hdpfans.com/tv.php\\?ac=search&sectype.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> apps=page.getHtml().links("//div[@id='tabcontentSearch']").all();
			//System.out.println(apps);
			for(String str:apps)
			{
				if(!str.contains("down/"))
				{
					page.addTargetRequest(str);
				}
			}
			//page.addTargetRequests(apps);
			
		}
		if(page.getUrl().regex("http://down.hdpfans.com/.+/\\d+\\.htm").match())
		{
				return Hdpfans_Detail.getApkDetail(page);
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
