package com.appCrawler.pagePro.fullstack;
/**
 * vivo  http://zs.vivo.com.cn/app.php
 * Play #176
 * @author DMT
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.appCrawler.pagePro.apkDetails.Vivo_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class Vivo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Vivo.class);

	public Apk process(Page page) {	
		//只有主页这一个下载页面	
				//提取页面信息
				
					List<Apk> apks = Vivo_Detail.getApkDetail(page);
								
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
