package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Anzhuoyuan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓园
 * 网址：http://www.anzhuoyuan.com/
 * Aawap #219
 * @author lisheng
 */


public class Anzhuoyuan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.anzhuoyuan.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.anzhuoyuan.com/soft/applist/cid/4.html");
			page.addTargetRequest("http://www.anzhuoyuan.com/game/applist/cid/3.html");
			return null;
		}
		if(page.getUrl().regex("http://www\\.anzhuoyuan\\.com/game/applist/cid/3.*").match()
				||page.getUrl().regex("http://www\\.anzhuoyuan\\.com/soft/applist/cid/4.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='single-app']").all();
			List<String> pages=page.getHtml().xpath("//div[@class='pages']/a/@href").all();
//			System.out.println(pages+"***");
//			//可能出现读取下一页失败的情况，增加几页
//			for(int i=1;i<pages.size();i++)
//			{
//				System.out.println(pages.get(i));
//				if(!pages.get(1).equals(""))
//				{
//					if(pages.get(i).contains("html"))
//					{
//						//分离出页码
//						String pageNo1[]=pages.get(i).split("page/");
//						String pageNo=pageNo1[1];
//						System.out.println(pageNo);
//						String No=pageNo.replace(".html", "");
//						for(int j=Integer.parseInt(No);j<Integer.parseInt(No)+5;j++)
//						{
//							System.out.println(pageNo1[0]+"page/"+j);
//							page.addTargetRequest(pageNo1[0]+"page/"+j);
//						}
//					}
//					else{
//						String pageNo[]=pages.get(1).split("page/");
//						//String No=pageNo.split(".")[0];
//						for(int j=Integer.parseInt(pageNo[1]);j<Integer.parseInt(pageNo[1])+5;j++)
//						{
//							System.out.println(pageNo[0]+"page/"+j);
//							page.addTargetRequest(pageNo[0]+"page/"+j);
//						}
//					}
//				}
//			}
			apps.addAll(pages);
			System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)
						&&!url.contains("download")){
					System.out.println(url);
					page.addTargetRequest(url);
				}
			}
		}
		
		//提取页面信息
		if(page.getUrl().regex("http://www\\.anzhuoyuan\\.com/app/info/appid/.*").match())
		{
			
			Apk apk = Anzhuoyuan_Detail.getApkDetail(page);
			
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
