package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Ijiatv_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 爱家市场
 * 网站主页：http://app.ijiatv.com/
 * Aawap #643
 * @author lisheng
 */
public class Ijiatv implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://app.ijiatv.com/\\?act=Index.Seac.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@id='list-app']").all();
			page.addTargetRequests(apps);
			
		}
		//提取页面信息
			if(page.getUrl().regex("http://app.ijiatv.com/.*").match()
					&&!page.getUrl().toString().contains("keyword"))
			{
				return Ijiatv_Detail.getApkDetail(page);
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
