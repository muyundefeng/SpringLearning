package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Z52_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 飞翔下载：
 * Aawap #http://www.52z.com/SoftList/858_anzhuo_1.html
 * id：242
 * @author DMT
 */


public class Z52 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
	if(page.getUrl().toString().equals("http://www.52z.com/SoftList/876_anzhuo_1.html"))
	{
		List<String> urlList1=page.getHtml().xpath("//div[@class='syGcBox']/div[2]/div[2]/a/@href").all();
	//	System.out.println(urlList1+"****++++");
 		List<String> urlList=new ArrayList<String>();
 		//将游戏索引页全部加入list
 		for(int i=868;i<=874;i++)
 		{
 			urlList.add("http://www.52z.com/SoftList/"+i+"_anzhuo_1.html");
 		}
 		urlList.add("http://www.52z.com/SoftList/899_anzhuo_1.html");
 		urlList.add("http://www.52z.com/SoftList/900_anzhuo_1.html");
 		page.addTargetRequests(urlList);
 		page.addTargetRequests(urlList1);
 		return null;
	}
 	if(page.getHtml().links().regex("http://www\\.52z\\.com/.*").match())
 	{
 		
 		List<String> urlList2=page.getHtml().xpath("//div[@class='syListImg']/a/@href").all();
 	//	System.out.println(urlList2+"****");
 		List<String> urlList3=page.getHtml().xpath("//div[@class='wezPage']/a/@href").all();
 	//	System.out.println(urlList3+"+++");
 		List<String> urlList4=page.getHtml().xpath("//div[@class='syEgIn']/a/@href").all();
 	//	System.out.println(urlList4+"====");
 		urlList2.addAll(urlList3);
 		urlList2.addAll(urlList4);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urlList2);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		
		if(page.getUrl().regex("http://www\\.52z\\.com/soft/.*").match())
		{
			
			Apk apk= Z52_Detail.getApkDetail(page);
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
