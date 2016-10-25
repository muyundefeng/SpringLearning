package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Tgbus_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 巴士手游网
 * 搜索接口：http://zhannei.baidu.com/cse/search?q=qq&p=21&s=13217253911394282549&entry=1
 * @id 409
 * @author lisheng
 */

public class Tgbus implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://zhannei\\.baidu\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			//page.addTargetRequests(pages);
			if(flag<3)
			{
				page.addTargetRequests(pages);
				flag++;
			}
		}
		if(page.getUrl().regex("http://shouji\\.tgbus\\.com/.*").match())
			{
				return Tgbus_Detail.getApkDetail(page);
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
