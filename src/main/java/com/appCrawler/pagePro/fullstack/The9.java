package com.appCrawler.pagePro.fullstack;

import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.The9_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 九号下载
 * 网站主页:http://www.9ht.com/
 * 渠道编号:378
 */


public class The9 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	if(page.getUrl().toString().equals("http://www.9ht.com/"))
	{
		page.addTargetRequest("http://www.9ht.com/pc/13_1.html");
		page.addTargetRequest("http://www.9ht.com/pc/19_1.html");
		page.addTargetRequest("http://www.9ht.com/wangyou/p_21_1.html");
		return null;
	}
	if(page.getUrl().regex("http://www\\.9ht\\.com/pc/.*").match()
			||page.getUrl().regex("http://www\\.9ht\\.com/wangyou/.*").match())
	{
 		List<String> apkList=page.getHtml().xpath("//dd[@id='lcont']/ul/li/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@class='tsp_nav']/a/@href").all();
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
		if(page.getUrl().regex("http://www\\.9ht\\.com/xz/\\d*\\.html").match())
		{
			Apk apk = The9_Detail.getApkDetail(page);
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
