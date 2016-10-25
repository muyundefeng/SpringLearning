package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.UrlEncoded;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App155_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 155游戏厅
 * 网站主页：http://android.155.cn/
 * Aawap #548
 * @author lisheng
 */
public class App155 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(page.getUrl().regex("http://android\\.155\\.cn/search.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='gmc-c']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//span[@class='page_num']").all();
			System.out.println(pages);
			for(String str:pages)
			{
				if(str!=null)
				{
					String temp[]=str.split("kw=");
					String temp1[]=temp[1].split("&index");
					String string=UrlEncoded.encodeString(temp1[0]);
					page.addTargetRequest(temp[0]+"kw="+string+"&index"+temp1[1]);
				}
				
			}
			
		}
		if(page.getUrl().regex("http://android\\.155\\.cn/game/\\d+\\.html").match()
				||page.getUrl().regex("http://wy\\.155\\.cn/spec\\d+/").match())
			{
				return App155_Detail.getApkDetail(page);
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
