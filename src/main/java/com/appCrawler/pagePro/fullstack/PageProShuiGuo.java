package com.appCrawler.pagePro.fullstack;


import com.alibaba.fastjson.JSON;
import com.appCrawler.pagePro.apkDetails.PageProShuiGuo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.deser.FromStringDeserializer;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *id:38
 * @version 1.0.0
 */
public class PageProShuiGuo implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProShuiGuo.class);

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

        // 获取搜索页面
//        if ((page.getUrl().regex("http://www.shuiguo.com/android/.*").match()
//                && page.getUrl().get().contains(".html")) ||
//                page.getUrl().regex("http://a\\.shuiguo\\.com").match()) {
//        if (page.getUrl().regex("http://www.shuiguo.com/android/.*").match()
//                || page.getUrl().regex("http://a\\.shuiguo\\.com.*").match()) {
//        	
//            LOGGER.debug("match success, url:{}", page.getUrl());
//
//            // 获取详细链接，以及分页链接
//            List<String> urlList = page.getHtml().links().regex("http://www\\.shuiguo\\.com/.*").all();
//
//            Iterator<String> iter = Sets.newHashSet(urlList).iterator();
//            while (iter.hasNext()) {
//                page.addTargetRequest(iter.next());
//            }
//
//            // 打印搜索结果url
//            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
//        }
        
        if(page.getUrl().toString().equals("http://a.shuiguo.com/")){
        	//一共有1-448个页面
        	for(int i=1;i<450;i++){//构造post请求获取结果，并从结果中提取需要的url
        		System.out.println("downloading page "+i);
        		Map<String, String> map = new HashMap<String, String>();  
        		map.put("gchannel", "all");
        		map.put("order", "update");  
        		map.put("page", String.valueOf(i));
        		map.put("plat", "1");
        		String temp = SinglePageDownloader.getHtml("http://www.shuiguo.com/app.php?action=gchannel","POST",map);
            	String[] urlList = temp.split("\"link\":\"");
            	for (String string : urlList) {
            		String urlString = StringUtils.substringBefore(string, "\",\"");
            		urlString = urlString.replace("\\", "");
            		if(urlString.startsWith("http"))
            			page.addTargetRequest(urlString);
            		//System.out.println(urlString);
				}
        		
        	}
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.shuiguo\\.com/android/.*").match()&& !page.getUrl().get().endsWith(".html")) {
           
			Apk apk;
			try {
				apk = PageProShuiGuo_Detail.getApkDetail(page);
				page.putField("apk", apk);
				if(page.getResultItems().get("apk") == null){
					page.setSkip(true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
}
