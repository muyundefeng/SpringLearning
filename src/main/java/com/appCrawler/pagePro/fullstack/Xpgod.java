package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Xpgod_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 手机天堂 http://www.xpgod.com/shouji/game/
 * Aawap #243
 * @author DMT
 */


public class Xpgod implements PageProcessor{

	Site site = Site.me().setCharset("GBK").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	//private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
	if(page.getUrl().toString().equals("http://www.xpgod.com/shouji/game/"))
	{
		String str=SinglePageDownloader.getHtml("http://www.xpgod.com/shouji/game/juese.html").toString();
		System.out.println(str+"++++");
		List<String> urlList=page.getHtml().xpath("//div[@class='search_pingtai']/ul/li/a/@href").all();
 		//List<String> urlList=new ArrayList<String>();
 		urlList.add("http://www.xpgod.com/shouji/soft/sys.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/meiti.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/pic.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/net.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/txgl.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/sjdh.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/yuedu.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/bjsh.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/meihua.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/esoft.html");
 		urlList.add("http://www.xpgod.com/shouji/soft/enter.html");
 		page.addTargetRequests(urlList);
 		return null;
	}
	if(page.getHtml().links().regex("http://www\\.xpgod\\.com/shouji/.*").match())
 	{
		
 		List<String> urlList2=page.getHtml().xpath("//div[@class='sort_li_bt']/a/@href").all();
 		List<String> urlList3=page.getHtml().xpath("//ul[@class='fenye_ul']/li/a/@href").all();
 		urlList2.addAll(urlList3);
 		//urlList.addAll(urlList3);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urlList2);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		//if(page.getUrl().regex("http://www\\.shouyou520\\.com/game/\\w{4}/\\d{5}\\.html").match())
		if(page.getUrl().regex("http://www\\.xpgod\\.com/shouji/game/.*").match()||page.getUrl().regex("http://www\\.xpgod\\.com/shouji/soft/.*").match())
		//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
		{
			
			Apk apk = Xpgod_Detail.getApkDetail(page);
			
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
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
}
