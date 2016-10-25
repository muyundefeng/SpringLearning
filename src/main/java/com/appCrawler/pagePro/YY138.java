package com.appCrawler.pagePro;
import java.util.List;
import java.util.Set;

import com.appCrawler.pagePro.apkDetails.YY138_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * http://www.yy138.com/youxi/zuixin/
 * YY138手机游戏中心
 * 搜索接口：http://www.yy138.com/search/app/?keyword=%E8%B7%91%E9%85%B7
 * Aawap #386
 * @author lisheng
 */
public class YY138 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://www\\.yy138\\.com/search/app/.*").match()){
			List<String> apkList=page.getHtml().xpath("//div[@class='applist']/ul/li/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@class='page clearfix']/ul/li/a/@href").all();
			apkList.addAll(pageList);
			System.out.println(pageList);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apkList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		//提取页面信息
				if(page.getUrl().regex("http://www\\.yy138\\.com/\\D+").match()
						&&!page.getUrl().toString().contains("zuixin"))
				{
					//System.out.println(page.getUrl().toString()+"****");
					return YY138_Detail.getApkDetail(page);
					
					}
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
