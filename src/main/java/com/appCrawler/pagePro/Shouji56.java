package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Shouji56_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 56手机游戏
 * 搜索接口：http://s.shouji56.com/cse/search?s=2332214539738916605!%!%!%entry=1!%!%!%q=*#*#*#
 * 渠道编号：406
 * @author lisheng
 */
public class Shouji56 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://s\\.shouji56\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			if(flag==1)
			{
				if(pages.size()>10)
				{
					for(int i=0;i<10;i++)
					{
						page.addTargetRequest(pages.get(i));
					}
				}
				else{
					page.addTargetRequests(pages);
				}
			}
			flag++;
		}
		if(page.getUrl().regex("http://www\\.shouji56\\.com/soft/.*").match()
				||page.getUrl().regex("http://www\\.shouji56\\.com/game/.*").match())
			{
				return Shouji56_Detail.getApkDetail(page);
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
