package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProCr173_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 西西软件园[中国] app搜索抓取(浏览器安全检查)
 * url:http://so.cr173.com/?keyword=mt&searchType=youxi
 * id:31
 * @version 1.0.0
 */
public class PageProCr173 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCr173.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

    
    static JSONObject  jasonObject = null;//帮助获取下载地址
//    /**
//     * 下载前缀地址
//     */
//    private Map<String, String> urlMap = null;

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    
    
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());
        page.addTargetRequest("http://www.cr173.com/inc/SoftLinkType.js");
        page.addTargetRequest("http://www.cr173.com/azyx/81027.html");
        try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        if(page.getUrl().toString().equals("http://www.cr173.com/inc/SoftLinkType.js")){
        	setJsonObject(page);
        }
        
        if(page.getUrl().toString().equals("http://www.cr173.com/")){
        	page.addTargetRequest("http://www.cr173.com/azyx/r_13_1.html");//游戏目录页
        	page.addTargetRequest("http://www.cr173.com/list/s_151_1.html");//软件目录页
        	page.setSkip(true);
        	
        }
        if(page.getUrl().regex("http://www\\.cr173\\.com/azyx/r_13_\\d+\\.html").match()){//游戏目录页
        	List<String> urlList = page.getHtml().links("//ul[@class='block']").all();
        	List<String> urlList2 = page.getHtml().links("//div[@class='tsp_nav']").all();
        	urlList.addAll(urlList2);
        	Set<String> cachSet = Sets.newHashSet();
        	cachSet.addAll(urlList);
        	for (String temp : cachSet) {
				if(PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
        	page.setSkip(true);
        
        }
        
        if(	page.getUrl().regex("http://www\\.cr173\\.com/list/s_151_\\d+\\.html").match()){//软件目录页
        	
        	List<String> urlList = page.getHtml().links("//div[@id='list_content']").all();
        	List<String> urlList2 = page.getHtml().links("//div[@class='tsp_nav']").all();
        	urlList.addAll(urlList2);
        	Set<String> cachSet = Sets.newHashSet();
        	cachSet.addAll(urlList);
        	for (String temp : cachSet) {
				if(PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
        	page.setSkip(true);
        }
      if (page.getUrl().regex("http://www\\.cr173\\.com/soft/\\d+\\.html").match() //匹配详情页
    		  || page.getUrl().regex("http://www\\.cr173\\.com/azyx/\\d+\\.html").match()) {
    
			Apk apk = PageProCr173_Detail.getApkDetail(jasonObject,page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
			}
		}else{
			page.setSkip(true);
		}
        
        return null;
        
        
        
        
//        // 加入下载地址
//        page.addTargetRequest("http://www.cr173.com/inc/SoftLinkType.js");
//
//        // 获取搜索页面
//        if (page.getUrl().regex("http://www\\.cr173\\.com/.*").match()) {
//            LOGGER.debug("match success, url:{}", page.getUrl());
//         //   System.out.println("page：" + page.getRawText());
//            // 获取详细链接，以及分页链接
//          //  List<String> urlList = page.getHtml().links("//ul[@id='r_lst']/li/h3").all();
//          //  urlList.addAll(page.getHtml().links("//div[@class='tsp_nav']").all());
//
//            List<String> urlList = page.getHtml().links().regex("http://www.cr173.com/.*||http://azyx.cr173.com/").all();
//
//            Set<String> cacheSet = Sets.newHashSet();
//    		cacheSet.addAll(urlList);
//
//    				for (String temp : cacheSet) {
//    					if(PageProUrlFilter.isUrlReasonable(temp)
//    						)
//    								page.addTargetRequest(temp);
//    				}
//
//            // 打印搜索结果url
//            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
//        }
//
//        // 获取下载地址信息
//        if (page.getUrl().regex("http://www\\.cr173\\.com/inc/SoftLinkType\\.js").match()) {
//            String html = StringUtils.substringAfter(page.getHtml().xpath("//body/text()").get(), "=");
//
//            try {
//                urlMap = new ObjectMapper().readValue(html, Map.class);
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 获取信息
//        if (page.getUrl().regex("http://www\\.cr173\\.com/soft/.*").match() || page.getUrl().regex("http://www\\.cr173\\.com/azyx/.*").match()) {
//         //   System.out.println(page.getUrl());
//			Apk apk = PageProCr173_Detail.getApkDetail(page, urlMap);
//			
//			page.putField("apk", apk);
//			if(page.getResultItems().get("apk") == null){
//				page.setSkip(true);
//			}
//		}else{
//			page.setSkip(true);
//		}
//        return null;
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
	private static void setJsonObject(Page page){
	
		String sourcefile=page.getHtml().toString();
	//	System.out.println(sourcefile);
		sourcefile = sourcefile.replace("&quot;", "\"");
    //	System.out.println(sourcefile);
		jasonObject = JSONObject.fromObject(sourcefile.substring(sourcefile.indexOf("{"), sourcefile.indexOf("}")+1));
			
    }
		
	
	

}