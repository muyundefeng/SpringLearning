package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.appCrawler.pagePro.apkDetails.Wwweoemarket_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 优亿市场 http://www.eoemarket.com
 * Wwweoemarket #193
 * 
 * 虽然有应用和游戏的全部索引，但是这里的没有包括小分类的所有应用
 * @author DMT
 */
public class Wwweoemarket implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Wwweoemarket.class);

	public Apk process(Page page) {
	
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.eoemarket.com")) {
			page.addTargetRequest("http://www.eoemarket.com/soft/1_hot_unofficial_hasad_1_1.html");// 添加应用索引
			page.addTargetRequest("http://www.eoemarket.com/game/2_hot_unofficial_hasad_2_1.html");// 添加游戏索引

		}
		if (page.getUrl().toString().equals("http://www.eoemarket.com/game/2_hot_unofficial_hasad_2_1.html") || 
				page.getUrl().toString().equals("http://www.eoemarket.com/soft/1_hot_unofficial_hasad_1_1.html")) {
			List<String> allCategaryList = page.getHtml().links("//div[@class='classfy']").all();
			page.addTargetRequests(allCategaryList);// 添加所有分类			
		}


		if (page.getUrl()
				.regex("http://www\\.eoemarket\\.com/soft/\\d+[a-z_]+[0-9_]+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.eoemarket\\.com/game/\\d+[a-z_]+[0-9_]+\\.html")
						.match()) {// 获取所有分类页
		
			List<String> urlList = page
					.getHtml()
					.links("//div[@class='app_classf_listc']")
					.regex("http://www\\.eoemarket\\.com/[a-z]+/\\d+\\.html")
					.all();
			System.out.println(urlList.size());
			List<String> pageList = page.getHtml()
					.links("//div[@class='page_c_eoe']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}
	
		//提取页面信息	http://www.eoemarket.com/soft/62579.html	http://www.eoemarket.com/game/749959.html
		if(page.getUrl().regex("http://www\\.eoemarket\\.com/soft/\\d+\\.html").match() 
				|| page.getUrl().regex("http://www\\.eoemarket\\.com/game/\\d+\\.html").match()){
			//System.out.println("in this");
			Apk apk = Wwweoemarket_Detail.getApkDetail(page);
			
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
