package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.pagePro.apkDetails.Uuu9_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 游久网
 * 网站主页：http://www.yoyojie.com/
 * Aawap #225
 * @author lisheng
 */


public class Uuu9 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.yoyojie.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://sjdb.uuu9.com/list.htm?pageIndexStr=1&findGoType=fd_pt&selectVal=android");
			return null;
		}
		if(page.getHtml().links().regex("http://sjdb\\.uuu9\\.com/list\\.htm\\?pageIndexStr=\\d+.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='box']/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@class='pageturing m-10']/a/@href").all();
			apps.addAll(pages);
			System.out.println(apps);
			//urlList.addAll(urlList2);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				//System.out.println(url);
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
 		
		
		
	
		//提取页面信息
		if(page.getUrl().regex("http://sjdb\\.uuu9\\.com/[a-zA-Z]+/").match()
				||page.getUrl().regex("http://sjdb\\.uuu9\\.com/\\d+/").match())
		{
			Apk apk = Uuu9_Detail.getApkDetail(page);
			
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
