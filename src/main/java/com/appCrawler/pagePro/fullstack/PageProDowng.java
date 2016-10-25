package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 



import com.appCrawler.pagePro.apkDetails.PageProDowng_Detail; 
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * #150 绿色软件站 app搜索抓取
 * url:http://search.downg.com/search.asp?action=s&sType=ResName&catalog=&keyword=%CA%D6%BB%FAQQ&Submit=%CB%D1%CB%F7
 *
 * @author DMT
 */


public class PageProDowng implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if (page.getUrl().toString().equals("http://www.downg.com/default.html")) {
			page.addTargetRequest("http://www.downg.com/list/s_164_1.html");// 添加索引
		}
		
		
		if (page.getUrl().regex("http://www\\.downg\\.com/list/s_164_\\d+\\.html")
				.match()) {// 获取所有分类页
			//System.out.println(page.getHtml().toString());
			List<String> urlList = page.getHtml()
					.links("//div[@class='col2']")
					.regex("http://www\\.downg\\.com/soft/\\d+\\.html").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pages']")
					.regex("http://www\\.downg\\.com/list/s_164_\\d+\\.html")
					.all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.downg\\.com/soft/\\d+\\.html").match()){
	
			
			Apk apk = PageProDowng_Detail.getApkDetail(page);
			
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
