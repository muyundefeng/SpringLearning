package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PagePro360_Detail;
import com.appCrawler.pagePro.apkDetails.PageProBaidu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 百度[中国] app搜索抓取
 * url:http://shouji.baidu.com/s?wd=QQ&data_type=app&f=header_all%40input
 *id:
 * @version 1.0.0
 */
public class PageProBaidu implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProBaidu.class);

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


        // 获取搜索页面
        if (page.getUrl().regex("http://shouji.baidu.com/.*").match()) {

            // 获取详细链接
            List<String> urlList = page.getHtml().links().regex("http://shouji.baidu.com.*").all();
            for(String url : urlList){
        		if(url.contains("#header")){
        			 continue;
        		}      	
        		if(url.contains("item?docid")){
        			url = url.replace("item?docid=","").split("&")[0]+".html";
        		}	
        		String regex = "http://shouji.baidu.com((?!passport).)*";
        		Pattern pattern = Pattern.compile(regex);
        		Matcher matcher = pattern.matcher(url);
        		if(matcher.matches()){
        			page.addTargetRequest(url);
        		}		
            }
                
        }

        // 获取信息
    	if(page.getUrl().toString().startsWith("http://shouji.baidu.com/software")  || page.getUrl().toString().startsWith("http://shouji.baidu.com/game")){    	
            Html html = page.getHtml();
			Apk apk = PageProBaidu_Detail.getApkDetail(page);
			String name = apk.getAppName();
			if(name.length()>1){
				String searchurl = "http://shouji.baidu.com/s?wd="+name.substring(0, 2).replaceAll("\\s", "")+"&data_type=&f=header_%40input";
				page.addTargetRequest(searchurl);
			}
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
}
