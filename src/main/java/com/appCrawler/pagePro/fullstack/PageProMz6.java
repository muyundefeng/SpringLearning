package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProMz6_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 魅族溜[中国] app搜索抓取
 * 页面的链接需要手动提取
 * 目录页每次只能获取到下一页的网址，当目录页的某一页下载超时，会导致后续的目录页无法下载，考虑手动添加所有的索引页
 * @version 1.0.0
 */
public class PageProMz6 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProMz6.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
            setSleepTime(PropertiesUtil.getInterval());

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
    	try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
        LOGGER.debug("crawler url: {}", page.getUrl());
//        if(page.getUrl().toString().equals("http://m.mz6.net/")){
//        	page.addTargetRequest("http://m.mz6.net/index.php?m=wap&c=index&a=lists&typeid=1&order=new&page=1");//软件目录页
//        	page.addTargetRequest("http://m.mz6.net/index.php?m=wap&c=index&a=lists&typeid=12&order=new&page=1");//游戏目录页
//        }
//       // System.out.println(page.getHtml().toString());
//        // 获取搜索页面
//        if (page.getUrl().regex("http://m\\.mz6\\.net/index\\.php\\?m=wap&c=index&a=lists&typeid=1&order=new&page=\\d+").match() 
//        		|| page.getUrl().regex("http://m\\.mz6\\.net/index\\.php\\?m=wap&c=index&a=lists&typeid=12&order=new&page=\\d+").match()) {
//            LOGGER.debug("match success, url:{}", page.getUrl());
//
//            // 获取详细链接，以及分页链接                                     												
//            List<String> pageList = page.getHtml().links("//div[@id='pages']").all();
//            String listString=page.getHtml().xpath("//div[@class='appdetail-list']").toString();
//            List<String> urlList = getLinks(listString);      
//            urlList.addAll(pageList);    
//            Set<String> sets = Sets.newHashSet(urlList);
//            for (String url : sets) {
//                if (PageProUrlFilter.isUrlReasonable(url)) {
//                    page.addTargetRequest(url);
//                }
//            }
//
//            // 打印搜索结果url
//            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
//        }
        if(page.getUrl().toString().equals("http://m.mz6.net/")){
        	for(int i=1;i<137;i++)
        		page.addTargetRequest("http://m.mz6.net/index.php?m=wap&c=index&a=lists&typeid=1&order=new&page="+i);//添加所有软件目录页
        	for(int i=1;i<328;i++)
        		page.addTargetRequest("http://m.mz6.net/index.php?m=wap&c=index&a=lists&typeid=12&order=new&page="+i);//添加所有游戏目录页
        }
       // System.out.println(page.getHtml().toString());
        // 获取搜索页面
        if (page.getUrl().regex("http://m\\.mz6\\.net/index\\.php\\?m=wap&c=index&a=lists&typeid=1&order=new&page=\\d+").match() 
        		|| page.getUrl().regex("http://m\\.mz6\\.net/index\\.php\\?m=wap&c=index&a=lists&typeid=12&order=new&page=\\d+").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接                                     												
          //  List<String> pageList = page.getHtml().links("//div[@id='pages']").all();
            String listString=page.getHtml().xpath("//div[@class='appdetail-list']").toString();
            List<String> urlList = getLinks(listString);      
         //   urlList.addAll(pageList);    
            Set<String> sets = Sets.newHashSet(urlList);
            for (String url : sets) {
                if (PageProUrlFilter.isUrlReasonable(url)) {
                    page.addTargetRequest(url);
                }
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }


        // 获取信息
        if (page.getUrl().regex("http://m\\.mz6\\.net/detail/.*").match()) {

        	Apk apk = PageProMz6_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
			}
		}else{
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
	public static List<String> getLinks(String linkString){
		String[] tempString=linkString.split("href");
		List<String> resultList=new LinkedList<String>();
		for (String string : tempString) {
			if(string.contains("下载"))
				resultList.add(string.substring(2,string.indexOf("下载")-2));			
		}	
		return resultList;		
	}
}
