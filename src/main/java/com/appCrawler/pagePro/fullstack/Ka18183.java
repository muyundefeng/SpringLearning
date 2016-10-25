package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ka18183_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 18183发号平台  http://ka.18183.com/index.shtml
 * 渠道编号:386
 * @author lisheng
 */



public class Ka18183 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	if(page.getUrl().regex("http://ku\\.18183\\.com/list-3-\\d+-0-0-0-\\d+\\.html.*").match()
			||"http://ku.18183.com/list-3-0-0-0-0.html".equals(page.getUrl().toString()))
	{
 		List<String> apkList=page.getHtml().xpath("//div[@class='games_recommended']/ul/li/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@class='fy']/ul/li/a/@href").all();
 		//apkList.addAll(categoryList);
 		apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://ku\\.18183\\.com/.*").match()
				&&!page.getUrl().regex("http://ku\\.18183\\.com/list-3-\\d*-0-0-0-\\d+\\.html.*").match()
				&&!"http://ku.18183.com/list-3-0-0-0-0.html".equals(page.getUrl().toString()))
		{
			Apk apk = Ka18183_Detail.getApkDetail(page);
			
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
