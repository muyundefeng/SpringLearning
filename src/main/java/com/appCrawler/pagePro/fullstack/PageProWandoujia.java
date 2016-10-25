package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProWandoujia_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * * 豌豆荚官方[中国] app搜索抓取
 * url:http://www.wandoujia.com/search?key=MT&source=apps
 * 评论网址：
 * http://apps.wandoujia.com/api/v1/comments/primary?packageName=com.sesame.dwgame.xiyou.ky
 * 只需修改后面的包名即可。
 * 40
 * 
 * 翻页是动态加载，而不是固定的url，需要手动构造
 * @version 1.0.0
 */
public class PageProWandoujia implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProWandoujia.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
    	    setSleepTime(PropertiesUtil.getInterval());

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());
        if(page.getUrl().toString().equals("http://www.wandoujia.com/apps")){//添加应用和游戏的所有分类
        	List<String> appCategoryList = page.getHtml().xpath("//ul[@class='app-popup tag-popup clearfix']/li/a/@href").all();
        	List<String> gameCategoryList = page.getHtml().xpath("//ul[@class='game-popup tag-popup clearfix']/li/a/@href").all();
        	gameCategoryList.addAll(appCategoryList);       	
        	page.addTargetRequests(gameCategoryList);
        	LOGGER.info("Add "+gameCategoryList.size()+" urls");
        }
        
       
        if (page.getUrl().regex("http://www.wandoujia.com/tag/.*").match()) {//添加每一个分类下的第一页 例子: http://www.wandoujia.com/tag/%E4%BC%91%E9%97%B2%E6%97%B6%E9%97%B4
            LOGGER.debug("match success, url:{}", page.getUrl());
            
            List<String> urlList = page.getHtml().links("//ul[@id='j-tag-list']").regex("http://www\\.wandoujia\\.com/apps/.+").all();
            Set<String> sets = Sets.newHashSet(urlList);
            LOGGER.info("Add "+urlList.size()+" urls");
			for (String url : sets) {
				if (PageProUrlFilter.isUrlReasonable(url) && !url.contains("/binding")) {
					page.addTargetRequest(url);
				}
			}
			//构造获取一个分类下的所有结果
			List<String> allDetailList = getAllDetailList(page.getUrl().toString());
			LOGGER.info("Add "+allDetailList.size()+" urls");
			for (String temp : allDetailList) {
				page.addTargetRequest("http://www.wandoujia.com/apps/"+temp);
			}
			          
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.wandoujia\\.com/apps/.*").match()) {
            Apk apk = PageProWandoujia_Detail.getApkDetail(page);

            page.putField("apk", apk);
            if (page.getResultItems().get("apk") == null) {
                page.setSkip(true);
            }
        }
        else {
            page.setSkip(true);
        }

        return null;
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return site;
    }

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * url的例子 http://apps.wandoujia.com/api/v1/apps?ads_count=0&tag=%E4%BC%91%E9%97%B2%E6%97%B6%E9%97%B4&max=12&start=24&opt_fields=apps.packageName
	 * 页面点击获取时，每次只获取12个应用，页面首次打开获取了24个，因此start从24开始，max=12		
	 * start的值最大只能到1000，超过1000以后就会报错
	 * @param url
	 * @return
	 */
	private List<String> getAllDetailList(String url){
		List<String> allDetailList = new LinkedList<String>();
		String category = StringUtils.substringAfter(url, "/tag/");
		int start = 24;
		int max = 12;
		while(true){
			String allPackageNameString = SinglePageDownloader.getHtml("http://apps.wandoujia.com/api/v1/apps?ads_count=0"
					+ "&tag="+category
					+ "&max="+max
					+ "&start="+start
					+ "&opt_fields=apps.packageName","GET",null);
			
			List<String> packageNameList = getPackageNameList(allPackageNameString);			
			allDetailList.addAll(packageNameList);
			if(packageNameList == null || packageNameList.size() < max)
				break;
			if(start == 996){
				max = 50;
				start = 1000;
			}
			else 	
			start+=max;
			
//			System.out.println("add "+packageNameList.size()+" start="+start);
		}		
		return allDetailList;
		
	}
	
	private List<String> getPackageNameList(String html){
		String[] packageName = html.split("packageName");
		List<String> packageNameList = new LinkedList<String>();		
		for (int i=1;i<packageName.length;i++) {
			//":"com.ustwo.monumentvalleyzz"},{"
			packageNameList.add(StringUtils.substringBetween(packageName[i], "\":\"","\"}"));
//			System.out.println(StringUtils.substringBetween(packageName[i], "\":\"","\"}"));
		}
		
		return packageNameList;
	}
	
	public static void main(String[] args){
		PageProWandoujia example = new PageProWandoujia();
		List<String> allDetailList=example.getAllDetailList("http://www.wandoujia.com/tag/%E4%BC%91%E9%97%B2%E6%97%B6%E9%97%B4");
		for (String string : allDetailList) {
			System.out.println(string);
		}
	}
}
