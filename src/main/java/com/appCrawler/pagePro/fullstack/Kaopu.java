package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.appCrawler.pagePro.apkDetails.Kaopu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 靠谱助手  http://www.kpzs.com/apps/applist
 * Kaopu #368
 * @author tianlei
 */


public class Kaopu implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		List<String> urlList=page.getHtml().xpath("//div[@class='appIntroBody']/h4/a/@href").all();
		List<String> nextPage = page.getHtml().xpath("//div[@class='pages pagesNew']/a/@href").all();
		cacheSet.addAll(urlList);
		cacheSet.addAll(nextPage);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}	
	
		//提取页面信息
		if(page.getUrl().regex("http://www.kpzs.com/apps/detail/.*").match()){		
			Apk apk = Kaopu_Detail.getApkDetail(page);		
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
