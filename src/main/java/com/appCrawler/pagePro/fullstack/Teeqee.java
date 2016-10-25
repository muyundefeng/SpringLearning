package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Teeqee_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 *快玩  http://www.teeqee.com/android.html
 * Teeqee #307
 * @author tianlei
 */


public class Teeqee implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		//System.out.println(page.getHtml().toString());
		if("http://www.teeqee.com/android.html".equals(page.getUrl().toString())){
			cacheSet.add("http://www.teeqee.com/android/wangluo.html");
			cacheSet.add("http://www.teeqee.com/android/yizhi.html");
			cacheSet.add("http://www.teeqee.com/android/jiaose.html");
			cacheSet.add("http://www.teeqee.com/android/dongzuo.html");
			cacheSet.add("http://www.teeqee.com/android/jingsu.html");
			cacheSet.add("http://www.teeqee.com/android/gedou.html");
			cacheSet.add("http://www.teeqee.com/android/feixing.html");
			cacheSet.add("http://www.teeqee.com/android/maoxian.html");
			cacheSet.add("http://www.teeqee.com/android/tiyu.html");
			cacheSet.add("http://www.teeqee.com/android/yinyue.html");
			cacheSet.add("http://www.teeqee.com/android/celue.html");
			cacheSet.add("http://www.teeqee.com/android/sheji.html");
			cacheSet.add("http://www.teeqee.com/android/moni.html");
			cacheSet.add("http://www.teeqee.com/android/yangcheng.html");
			cacheSet.add("http://www.teeqee.com/android/qita.html");
		}
		else{
			List<String> urlList=page.getHtml().xpath("//li[@class='lazy_load_by_kw']/a[1]/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@class='category_page']//a/@href").all();
			cacheSet.addAll(urlList);
			if(nextPage.size() != 0){
				cacheSet.addAll(nextPage);
				}
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		//提取页面信息
		if(page.getUrl().regex("http://www.teeqee.com/.*from=web_category").match()){		
			Apk apk = Teeqee_Detail.getApkDetail(page);			
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
