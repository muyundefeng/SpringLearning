package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.appCrawler.pagePro.apkDetails.Chuizi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #197 锤子
 * Chuizi http://store.smartisan.com/
 * @author DMT
 *
 */
public class Chuizi implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Chuizi.class);

	public Apk process(Page page) {
			
		if(page.getUrl().toString().equals("http://store.smartisan.com/"))
		{
			page.addTargetRequest("http://static.smartisanos.cn/apps/js/template_9c98a22534.js");
			return null;
		}
		//System.out.println(page.getUrl().toString());
		//提取页面信息	
		List<Apk> apks = Chuizi_Detail.getApkDetail(page);
		page.putField("apks", apks);
		if(page.getResultItems().get("apks") == null){
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
