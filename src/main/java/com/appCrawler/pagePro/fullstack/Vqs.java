package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Vqs_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 骑士助手
 * 网站主页：http://www.vqs.com/
 * 渠道编号:541
 * @author lisheng
 */


public class Vqs implements PageProcessor{

	Site site = Site.me().setCharset("gbk").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		
		if("http://www.vqs.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.vqs.com/game/1-0-0-0-view");
			page.addTargetRequest("http://www.vqs.com/soft/1-0-view");
			return null;
		}
		if(page.getUrl().regex("http://www\\.vqs\\.com/game/\\d+-0-0-0-view").match()||page.getUrl().regex("http://www\\.vqs\\.com/soft/\\d+-0-view").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='pic_box']/dl/dd/a/@href").all();
			List<String> pages=page.getHtml().links("//div[@class='pages txtCtr']").all();
			apps.addAll(pages);
			for(String str:apps)
			{
				if(str.contains(".apk"))
				{
					continue;
				}
				if(!str.contains("http://d2.vqs.com"))
				{
					page.addTargetRequest(str);
				}
			}
		}
		
		if(page.getUrl().regex("http://www\\.vqs\\.com/game/\\d+\\.html").match()||
				page.getUrl().regex("http://www\\.vqs\\.com/soft/\\d+\\.html").match())
		{
			
			Apk apk = Vqs_Detail.getApkDetail(page);
			
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
