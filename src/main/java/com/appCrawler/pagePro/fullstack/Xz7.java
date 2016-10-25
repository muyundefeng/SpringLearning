package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Xz7_Detail;
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
 * 激光下载站
 * 网站主页：http://www.xz7.com/
 * @id 491
 * @author lisheng
 */


public class Xz7 implements PageProcessor{

	Site site = Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.xz7.com/".equals(page.getUrl().toString()))
		{
			//page.putField("", field);
			page.addTargetRequest("http://www.xz7.com/zhuanti/312_1.htm");
			page.addTargetRequest("http://www.xz7.com/soft/s_530_1.html");
			page.putField("Referer", "http://www.xz7.com/zhuanti/312_1.htm");
			return null;
		}
	
		if(page.getUrl().regex("http://www\\.xz7\\.com/zhuanti/.*").match()
				||page.getUrl().regex("http://www\\.xz7\\.com/soft/.*").match())
		{
			//System.out.println(page.getHtml());
			String html=page.getHtml().toString();
			if(html.contains("<script>window.location.href="))
			{
				String url=html.toString().split("window.location.href='")[1].split("';</script>")[0];
				String html1=SinglePageDownloader.getHtml(url);
				Html html2=Html.create(html1);
				List<String> categoryList=html2.xpath("//ul[@class='catlst']/li/a/@href").all();
		 		//System.out.println(categoryList);
				List<String> apps=html2.links("//div[@id='list_content']").all();
		 		List<String> pages=html2.links("//div[@class='pg_pcl']").all();
		 		apps.addAll(categoryList);
		 		apps.addAll(pages);
		 		for(String str:apps)
		 		{
		 			if(!str.startsWith("http://"))
		 			{
		 				page.addTargetRequest("http://www.xz7.com"+str);
		 			}
		 		}
		 	}
			else{
				List<String> categoryList=page.getHtml().xpath("//ul[@class='catlst']/li/a/@href").all();
		 		System.out.println(categoryList);
				List<String> apps=page.getHtml().links("//div[@id='list_content']").all();
		 		List<String> pages=page.getHtml().links("//div[@class='pg_pcl']").all();
		 		apps.addAll(categoryList);
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
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.xz7\\.com/dir/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.xz7\\.com/soft/\\d+\\.html").match())
		{
			
			Apk apk = Xz7_Detail.getApkDetail(page);
			
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
