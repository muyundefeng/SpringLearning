package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Anzhuome_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 安卓迷
 * 网站主页：http://www.anzhuo.me/
 * @id 531
 * @author lisheng
 *
 */
public class Anzhuome implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Anzhuome.class);

	public Apk process(Page page) {
	
		
		if("http://www.anzhuo.me/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.anzhuo.me/anzhuoruanjian/list_2_1.html");
			page.addTargetRequest("http://www.anzhuo.me/anzhuoyouxi/list_3_1.html");
			return null;
		}
		if(page.getUrl().regex("http://www\\.anzhuo\\.me/anzhuoyouxi/list_3_\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.anzhuo\\.me/anzhuoruanjian/list_2_\\d+\\.html").match())
		{
			List<String> apps =page.getHtml().xpath("//div[@class='post-list']/ul/li/h2/a/@href").all();
			List<String> pages=page.getHtml().links("//div[@class='pages']").all();
			apps.addAll(pages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.anzhuo\\.me/anzhuoruanjian/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.anzhuo\\.me/anzhuoyouxi/\\d+\\.html").match()){
			
			Apk apk = Anzhuome_Detail.getApkDetail(page);
			
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
