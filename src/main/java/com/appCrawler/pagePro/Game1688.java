package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game1688_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 1688玩
 * 网站搜索接口：http://www.1688wan.com/search/findgame/1_0_qq
 * @id 403
 * @author lisheng
 */
public class Game1688 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.1688wan\\.com/search/findgame/.*").match())
		{
			List<String> apps=page.getHtml().xpath("//ul[@class='search-game-list']/li/div/div[1]/a/@href").all();
			page.addTargetRequests(apps);
			//List<String> pages=page.getHtml().xpath("//ul[@class='jPag-pages']/a/@href").all();
			String string=page.getHtml().toString().split("counts =")[1];
			int i;
			for(i=0;;i++)
			{
				if(string.charAt(i)==';')
				{
					break;
				}
			}
			String couns=string.substring(0,i).replace(" ", "");
			System.out.println(couns);
			String str=page.getUrl().toString().split("/")[5];
			System.out.println(str);
			//String str1=str.split("_")[0];
			String string2=str.split("_")[1]+"_"+str.split("_")[2];
			for(int j=1;j<Integer.parseInt(couns);j++)
			{
				page.addTargetRequest("http://www.1688wan.com/search/findgame/"+j+"_"+string2);
			}	
		}
		if(page.getUrl().regex("http://www\\.1688wan\\.com/game/info/\\d+\\.html").match())
			{
				return Game1688_Detail.getApkDetail(page);
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
