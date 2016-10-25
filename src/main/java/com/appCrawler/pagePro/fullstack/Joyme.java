package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Joyme_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓迷
 * 网站主页：http://www.joyme.com/
 * @id 511
 *
 * @author lisheng
 */

public class Joyme implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Joyme.class);

	public Apk process(Page page) {
		if("http://www.joyme.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.joyme.com/collection/genre/p1-1_page:1");
			return null;
		}
		if(page.getUrl().regex("http://www\\.joyme\\.com/collection/genre/p1-1_page.*").match())
		{
			List<String> apps=page.getHtml().links("//dl[@class='fn-clear']").all();
			List<String> pages1=page.getHtml().xpath("//div[@class='page']/ul/li/@data-page").all();
			for(String pageNo: pages1)
			{
				page.addTargetRequest("http://www.joyme.com/collection/genre/p1-1_page:"+pageNo);
			}
			//			System.out.println(pages1);
//			List<String> pages=page.getHtml().links("//div[@class='page']/ul/li[5]/a/text()").all();
//			apps.addAll(pages);
			System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url) && !url.contains("http://apk.91.com/soft/Controller.ashx?")){
					page.addTargetRequest(url);
				}
			}
			
		}
		
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.joyme\\.com/collection/\\d+").match()){
			
			Apk apk = Joyme_Detail.getApkDetail(page);
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
