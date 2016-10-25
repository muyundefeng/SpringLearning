package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PagePro155_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 手游天下[中国] app搜索抓取
 * url:http://android.155.cn/search.php?kw=MT&index=soft
 * id 25
 * @version 1.0.0
 */
public class PagePro155 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro155.class);

    private static int flag=1;
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
        LOGGER.debug("crawler url: {}", page.getUrl());

        // 获取搜索页面
        if (page.getUrl().regex("http://android\\.155.cn/search\\.php\\?.*index=game").match() && !page.getUrl().get().contains("index=news") && !page.getUrl().get().contains("index=tags")) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> apps = page.getHtml().xpath("//div[@class='gmc-show']/div[2]/div/ul/li/a/@href").all();
            System.out.println(apps);
            for(String str:apps)
            {
            	if(!str.contains("help"))
            	{
            		page.addTargetRequest(str);
            	}
            }
            	
            List<String> pages=page.getHtml().xpath("//div[@class='page list_page']/span[@class='page_num']/a/@href").all();
            System.out.println(pages);
            if(flag==1)
            {
            	if(pages.size()>4)
            	{
            		for(int i=0;i<4;i++)
            		{
            			page.addTargetRequest(page.getUrl().toString()+"&page="+(i+2));
            		}
            	}
            	else{
            		for(int i=0;i<pages.size();i++)
            		{
            			page.addTargetRequest(page.getUrl().toString()+"&page="+(i+2));
            		}
            	}
            	flag++;
            }
            
//            urlList.addAll(page.getHtml().links("//div[@class='tab-show']/ul/li").all());
//            urlList.addAll(page.getHtml().links("//div[@class='box_page'][2]/div/span[@class='page_num']").all());
//
//            Iterator<String> iter = Sets.newHashSet(urlList).iterator();
//            while (iter.hasNext()) {
//                page.addTargetRequest(iter.next());
//            }
//
//            // 打印搜索结果url
//            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://android\\.155\\.cn/[soft|game]/*").match()) {
        	return PagePro155_Detail.getApkDetail(page);
    		
    
    	}

        return null;
    }

    /**
     * get the site settings
     *
     * @return site
     * @see us.codecraft.webmagic.Site
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
