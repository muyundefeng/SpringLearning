package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 酷快市场 http://www.aawap.net/
 * Aawap #117
 * @author Lisheng
 */


public class Aawap implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		
		if(page.getUrl().toString().equals("http://www.aawap.net/"))
		{
			List<String> urlList=page.getHtml().xpath("//ul[@id='app_ul']/li/a/@href").all();
			List<String> urlList1=page.getHtml().xpath("//div[@class='more']/a/@href").all();
			List<String> appList=new ArrayList<String>();
			for(int i=2;i<168;i++)
			{	
				String temp="http://market.kknet.com.cn:8080/app/getAppByRecNewest/d68e84140e6956552ce2dd8edc2dd627/2/"+i+"/10/?callback=jQuery164021931103919632733_1446797545466&_=1446797808438";
				String info=SinglePageDownloader.getHtml(temp);
				System.out.println(info);
				//String temp1="";
				String id="";
				int length=36;//appId长度
				while(info.contains("appId"))
					{
						int start=info.indexOf("appId");
						id=info.substring(start+8, start+8+length);
						//System.out.println(info);
						//System.out.println(id);
						info=info.replaceFirst("appId", "");
						appList.add("http://aawap.net/aawap/detail/"+id);
					}
				
			}
			urlList.addAll(urlList1);
			urlList.addAll(appList);
			
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}
		}
 		
		
		
	
		//提取页面信息
		if(page.getUrl().regex("http://aawap\\.net/aawap/detail/.*").match()){
	
			
			Apk apk = Aawap_Detail.getApkDetail(page);
			
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
