package com.appCrawler.pagePro.fullstack;


import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.appCrawler.pagePro.apkDetails.Xiazaiba_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #198 下载吧
 * Xiazaiba http://a.xiazaiba.com/
 * @author DMT
 *
 */
public class Xiazaiba implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Xiazaiba.class);

	public Apk process(Page page) {

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try{
			 Calendar now = Calendar.getInstance();  			
		     int hour = now.get(Calendar.HOUR_OF_DAY);
		     int minute = now.get(Calendar.MINUTE);
		     if(hour == 23 && minute ==59){
		    	 LOGGER.info("Sleeping");
		    	 Thread.sleep(1000*60*10);//休眠10分钟
		    	 LOGGER.info("Wake up");
		     }
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		if (page.getUrl().toString().equals("http://a.xiazaiba.com/")) {
			page.addTargetRequest("http://a.xiazaiba.com/ruanjian/index.html");// 添加应用索引
			page.addTargetRequest("http://a.xiazaiba.com/youxi/index.html");// 添加游戏索引
			page.addTargetRequest("http://a.xiazaiba.com/padsoft/index.html");// 添加平板应用索引
			page.addTargetRequest("http://a.xiazaiba.com/padgame/index.html");// 添加平板游戏索引

		}

		if (page.getUrl()
				.regex("http://a\\.xiazaiba\\.com/[a-z]+/index\\.html")
				.match()
				|| page.getUrl()
						.regex("http://a\\.xiazaiba\\.com/[a-z]+/index_\\d+\\.html")
						.match()) {// 获取所有分类页
			List<String> urlList = page
					.getHtml()
					.links("//ul[@class='down-list']")
					.regex("http://a\\.xiazaiba\\.com/[a-z]+/\\d+\\.html")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='ylmf-page']").all();

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
		if(page.getUrl().regex("http://a\\.xiazaiba\\.com/game/\\d+\\.html").match()
				|| page.getUrl().regex("http://a\\.xiazaiba\\.com/app/\\d+\\.html").match()){
			
			Apk apk = Xiazaiba_Detail.getApkDetail(page);
			
			
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
