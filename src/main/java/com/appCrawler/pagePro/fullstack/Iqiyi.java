package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Iqiyi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 爱奇艺应用商店
 * 网站主页：http://store.iqiyi.com/
 * Aawap #631
 * @author lisheng
 */


public class Iqiyi implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Iqiyi.class);

	public Apk process(Page page) {
	
		if("http://store.iqiyi.com/".equals(page.getUrl().toString()))
		{
			List<String> categoryList = page.getHtml().links("//qchunk[@id='block-game_cate_list']").all();
			for(String str:categoryList)
			{
				
				if(str.contains("act=default"))
				{
					String url=str.replace("&_size=16&type=3&act=default", "&_size=35&type=1");
					for(int i=1;;i++)
					{
						String string = SinglePageDownloader.getHtml(url.replace("page=1", "page="+i));
						if(!string.contains("暂无您想找的游戏或者应用"))
						{
							page.addTargetRequest(url);
						}
						else {
							break;
						}
					}
				}
				
					//int total = html.xpath("//div[@class='i-appstore-content']/input[]")
				}
			List<String> categoryList1 = page.getHtml().links("//qchunk[@id='block-cate_list']").all();
			for(String str:categoryList)
			{
				
				if(str.contains("act=default"))
				{
					String url=str.replace("&_size=16&type=3&act=default", "&_size=35&type=1");
					for(int i=1;;i++)
					{
						String string = SinglePageDownloader.getHtml(url.replace("page=1", "page="+i));
						if(!string.contains("暂无您想找的游戏或者应用"))
						{
							page.addTargetRequest(url);
						}
						else {
							break;
						}
					}
				}
					//int total = html.xpath("//div[@class='i-appstore-content']/input[]")
				}
			
			return null;
		}

	
		if(page.getHtml().links().regex("http://store.iqiyi.com/category/.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@id='asg_app']").all();
	 		//apps.addAll(pages);
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
		if(page.getUrl().regex("http://store.iqiyi.com/app/\\d+\\?app_ver_code=\\d+&type=\\d+").match())
		{
			
			Apk apk = Iqiyi_Detail.getApkDetail(page);
			
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
