package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.StreamingHttpOutputMessage;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yxk_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 游戏库
 * 网站主页：http://www.xiaopi.com/list_0_1_0_0_0_1.html
 * @id 407
 * @author lisheng
 */


public class Yxk implements PageProcessor{

	Site site=Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		
		
		if(page.getUrl().regex("http://sj\\.xiaopi\\.com/yxk.*").match()||page.getUrl().regex("http://www\\.xiaopi\\.com/list_0_1_0_0_0_\\d+.html").match()
				||page.getUrl().regex("http://www\\.xiaopi\\.com/soft/list_1_0_1_\\d+\\.html").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//div[@class='kk']").all();
	 		page.addTargetRequest("http://www.xiaopi.com/soft/list_1_0_1_1.html");
	 		page.addTargetRequest("http://www.xiaopi.com/list_0_1_0_1_0_1.html");
	 		List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
	 		//apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://sj\\.xiaopi\\.com/.*").match()
				||page.getUrl().regex("http://www\\.xiaopi\\.com/game|soft/\\d+\\.html").match()
				&&!page.getUrl().regex("http://sj\\.xiaopi\\.com/yxk.*").match())
		{
			
			Apk apk = Yxk_Detail.getApkDetail(page);
			
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
	public static String charset(String url)
	{
		String html=SinglePageDownloader.getHtml(url);
		String temp[]=html.split("charset");
		String charset1=temp[1];
		String temp1=charset1.split("><title>")[0];
		//System.out.println(temp1);
//		int i;
//		for(i=0;;i++)
//		{
//			if(charset1.indexOf(i)=='t')
//			{
//				break;
//			}
//		}
//		String string=charset1.substring(0, i);
		if(temp1.contains("meta"))
		{
			String temp3=temp1.split("meta")[0];
			//System.out.println(temp3);
			return temp3.replace("\" /><", "").replace("=", "");
		}
		else
		{
			String temp2=(temp1.replace("=", "")).replace("\"", "");
			return temp2;
		}
	}
}
