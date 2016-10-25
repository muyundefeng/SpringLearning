package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Jz5u_Detail;
import com.appCrawler.pagePro.apkDetails.PageProYesky_Detail;
import com.appCrawler.pagePro.apkDetails.Xp510_Detail;
import com.appCrawler.pagePro.apkDetails.Yesky_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 天极手机软件下载  http://mydown.yesky.com/
 * Yesky #163
 * @author tianlei
 */


public class Yesky implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
        String pages = null;
        if("http://mydown.yesky.com/".equals(page.getUrl().toString())){
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000978.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17772.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000977.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000989.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000985.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17811.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000982.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000994.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17812.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17813.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000987.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000976.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17842.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17815.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_17817.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000981.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000980.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/113565_10000995.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21659.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21655.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21657.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21654.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21658.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21656.shtml");
        	cacheSet.add("http://mydown.yesky.com/c/572544_21660.shtml");
      	
        }else{
		List<String> urlList = page.getHtml().xpath("//p[@class='txt_o']/a/@href").all();
        pages = page.getHtml().xpath("//div[@class='flym']").toString();
        if(pages != null){
        	List<String> nextUrl = getNextUrl(pages);
            cacheSet.addAll(nextUrl);
        }
        cacheSet.addAll(urlList);
        }
		for(String url : cacheSet){

		   if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}

		//提取页面信息
		if(page.getUrl().regex("http://mydown.yesky.com/sjsoft/.*").match()){		
			Apk apk = Yesky_Detail.getApkDetail(page);		
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
	
	private static List<String> getNextUrl(String str){
        List<String> tmp = new ArrayList<String>();
		String regex="<a href=\"(.*)\" target=\"_self\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){      	
        	tmp.add(matcher.group(1).toString());        	
        }
    	return tmp;   	
    }
	
}
