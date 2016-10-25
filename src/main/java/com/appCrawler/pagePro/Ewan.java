package com.appCrawler.pagePro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ewan_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 上海益玩  http://www.ewan.cn/
 * Ewan #303
 * @author tianlei
 */
public class Ewan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
            List<String>urlList = new ArrayList<String>();
			String pages = SinglePageDownloader.getHtml(page.getUrl().toString());
            urlList = getId(pages);                   
            for (int i = 0 ;i <urlList.size();i++){
            	urlList.set(i, "http://www.ewan.cn/game-id-"+urlList.get(i)+".html");
            }
        
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}
			
		//the app detail page
		if(page.getUrl().regex("http://www.ewan.cn/game-id-.*").match()){
			return Ewan_Detail.getApkDetail(page);
		}

		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private  List<String> getId(String str){
    	List<String> tmp = new ArrayList<String>();    		
		String regex="\"aid\":\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){        	
        	tmp.add(matcher.group(1).toString());      	
        }
    	return tmp;   	
    }

}
