package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.PageProSkycn_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 天空下载  http://www.skycn.com/soft/17.html
 * Aawap #165
 * @author DMT
 */
public class PageProSkycn implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.skycn\\.com/search\\.asp\\?keyword=.*").match()){
			//app的具体介绍页面
			System.out.println("hello boys");
			List<String> detailUrl = page.getHtml().xpath("//div[@id='r_main']/ul/li/div/a/@href").all();
			detailUrl.addAll(page.getHtml().links("//div[@class='tsp_nav']/a/@href").all());
			System.out.println("detailUrl size="+detailUrl.size());
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(detailUrl);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		if(page.getUrl().regex("http://www\\.skycn\\.com/soft/appid/.*").match())
			//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
			{
				
				Apk apk = PageProSkycn_Detail.getApkDetail(page);
				
				page.putField("apk", apk);
				if(page.getResultItems().get("apk") == null){
					page.setSkip(true);
					}
				}
			else{
				page.setSkip(true);
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
