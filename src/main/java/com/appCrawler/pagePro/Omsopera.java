package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.impl.cookie.IgnoreSpec;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.pagePro.apkDetails.Omsopera_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Opera http://android.oms.apps.opera.com/zh_cn/
 * Aawap #234
 * @author DMT
 */
public class Omsopera implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://android\\.oms\\.apps\\.opera\\.com/zh_cn/catalog\\.php\\?search=.*").match()){
			List<String> apps=page.getHtml().xpath("//div[@class='product']/a/@href").all();
			String pageUrl=page.getHtml().xpath("//div[@class='footer']/a/@href").toString();
			if(flag==1)
			{
				for(int i=2;i<=5;i++)
				{
					String string=page.getUrl().toString();
					page.addTargetRequest(string+"&p="+i);
				}
				flag++;
			}
			//apps.addAll(categorylist);
			//apps.addAll(pages);
			System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
					//System.out.println(url.toString());
				}
			}
		}
		if(page.getUrl().regex("http://android\\.oms\\.apps\\.opera\\.com/zh_.*/.+\\?pos=\\d+").match())
		{
			return Omsopera_Detail.getApkDetail(page);
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
