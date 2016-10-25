package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.appCrawler.pagePro.apkDetails.Guopan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 果盘游戏  http://www.guopan.cn
 * Guopan #372
 * @author tianlei
 */


public class Guopan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		if("http://www.guopan.cn/".equals(page.getUrl().toString())){			
			cacheSet.add("http://www.guopan.cn/youxi/1_0_0_0_1_1/");		
		}else if(page.getUrl().regex("http://www.guopan.cn/youxi/1_0_0_0_1_。*").match()){
			List<String> urlList=page.getHtml().xpath("//div[@class='amusementPic']/a/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@id='pageSwitcher']/a/@href").all();		
			cacheSet.addAll(nextPage);
			cacheSet.addAll(urlList);
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}

		//提取页面信息
		if(!page.getUrl().regex("http://www.guopan.cn/youxi/1_0_0_0_1_。*").match()
			&& !page.getUrl().toString().equals("http://www.guopan.cn/")){
			Apk apk = Guopan_Detail.getApkDetail(page);	
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
