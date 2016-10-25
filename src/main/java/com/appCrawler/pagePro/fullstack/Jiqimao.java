package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import com.appCrawler.pagePro.apkDetails.Jiqimao_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #194 机器猫
 * http://www.jiqimao.com
 * @author Administrator
 *
 */
public class Jiqimao implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Jiqimao.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.jiqimao.com")) {
			page.addTargetRequest("http://www.jiqimao.com/game-7-3361/?page=1");// 添加游戏索引
			page.addTargetRequest("http://www.jiqimao.com/soft-8-3361/?page=1");// 添加应用索引

		}
		
		if (page.getUrl().toString().equals("http://www.jiqimao.com/game-7-3361/?page=1")// 添加所有的游戏分类
				|| page.getUrl().toString().equals("http://www.jiqimao.com/soft-8-3361/?page=1")) {// 添加所有的应用分类
			page.addTargetRequests(page.getHtml().links("//div[@class='select_sort']").regex("http://www\\.jiqimao\\.com/game-\\d+-3361/").all());
			page.addTargetRequests(page.getHtml().links("//div[@class='select_sort']").regex("http://www\\.jiqimao\\.com/soft-\\d+-3361/").all());

		}

		if (page.getUrl()
				.regex("http://www\\.jiqimao\\.com/game-\\d-3361/\\?page=\\d+")
				.match()
				|| page.getUrl()
						.regex("http://www\\.jiqimao\\.com/soft-\\d-3361/\\?page=\\d+")
						.match()
						|| page.getUrl()
						.regex("http://www\\.jiqimao\\.com/game-\\d+-3361/")
						.match()
						|| page.getUrl()
						.regex("http://www\\.jiqimao\\.com/soft-\\d+-3361/")
						.match()) {// 获取所有分类页
			List<String> urlList = page
					.getHtml()
					.links("//div[@class='applist']")
					.regex("http://www\\.jiqimao\\.com/[a-z]+-\\d+\\.html")
					.all();	
			List<String> urlList2 = page
					.getHtml()
					.links("//div[@class='applist']")
					.regex("http://www\\.jiqimao\\.com/[a-z]+-\\d+")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pages fixed']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(urlList2);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}
		
	
		//提取页面信息	http://www.jiqimao.com/game-48239/
		if(page.getUrl().regex("http://www\\.jiqimao\\.com/soft-\\d+\\.html").match()
				|| page.getUrl().regex("http://www\\.jiqimao\\.com/game-\\d+\\.html").match()
				|| page.getUrl().regex("http://www\\.jiqimao\\.com/soft-\\d+").match()
				|| page.getUrl().regex("http://www\\.jiqimao\\.com/game-\\d+").match()){
			
			Apk apk = Jiqimao_Detail.getApkDetail(page);
			
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
