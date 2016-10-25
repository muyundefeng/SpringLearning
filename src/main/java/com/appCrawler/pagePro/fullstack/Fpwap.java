package com.appCrawler.pagePro.fullstack;
/*http://www.fpwap.com/hengxingmxw/ 网游类的例子
 * 2015年10月26日10:47:45 单机类的索引页翻页有问题，尚未加入 http://www.fpwap.com/game/
 * */

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Fpwap_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;


public class Fpwap implements PageProcessor{
	//Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(0);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Fpwap.class);

	public Apk process(Page page) {		

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.fpwap.com/")){
			page.addTargetRequest("http://www.fpwap.com/gamelist/2-0-0-0-0-0-1.html");//网游类
		}
		if(page.getUrl().regex("http://www\\.fpwap\\.com/gamelist/2-0-0-0-0-0-\\d+\\.html").match()){
			List<String> pageList = page.getHtml().links("//div[@class='page-change']").all();
			List<String> detaiList = page.getHtml().links("//ul[@class='game-poker']").all();
			
			Set<String> cacheSet = Sets.newHashSet();
			pageList.addAll(detaiList);
			cacheSet.addAll(pageList);
			for (String temp : cacheSet) {				
				if(PageProUrlFilter.isUrlReasonable(temp))					
					page.addTargetRequest(temp);
			}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.fpwap\\.com/.+/").match() 
				&& !page.getUrl().regex("http://www\\.fpwap\\.com/gamelist/2-0-0-0-0-0-\\d+\\.html").match() ){
			
			Apk apk = Fpwap_Detail.getApkDetail(page);
			
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
