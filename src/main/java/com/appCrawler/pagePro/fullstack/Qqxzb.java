package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Qqxzb_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 清清下载吧
 * 网站主页：http://www.qqxzb.com/
 * @id 534
 * @author lisheng
 */


public class Qqxzb implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if("http://www.qqxzb.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.qqxzb.com/azsoft/1/");
			page.addTargetRequest("http://www.qqxzb.com/azgame/1/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.qqxzb\\.com/azgame/\\d+/").match()
				||page.getHtml().links().regex("http://www\\.qqxzb\\.com/azsoft/\\d+/").match())
		{
			List<String> apps=page.getHtml().links("//div[@id='listCon']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pager']").all();
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("DiYiFangPoJieXianZhiBan_92754")&&!url.contains("QiQiKanPian")){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.qqxzb\\.com/azs/.*").match()
				||page.getUrl().regex("http://www\\.qqxzb\\.com/azg/.*").match())
		{
			
			Apk apk = Qqxzb_Detail.getApkDetail(page);
			
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
