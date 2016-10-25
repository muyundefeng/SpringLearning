package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProWandoujia_Detail;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * * 豌豆荚官方[中国] app搜索抓取
 * url:http://www.wandoujia.com/search?key=MT&source=apps
 * 评论网址：
 * http://apps.wandoujia.com/api/v1/comments/primary?packageName=com.sesame.dwgame.xiyou.ky
 * 只需修改后面的包名即可。
 * @version 1.0.0
 */
public class PageProWandoujia implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProWandoujia.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

        // 获取搜索页面
        if (page.getUrl().regex("http://www\\.wandoujia\\.com/search\\?.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接,目前只助理了第一页的数据，分页数据麻烦暂时没处理
            List<String> urlList = page.getHtml().links("//ul[@id='j-search-list']/li/div[@class='app-desc']").all();
            //提取搜索关键字
            String str=page.getUrl().toString().split("=")[1];
            String key=str.split("&")[0];
            for(int i=0;i<5;i++)
            {
	            String url="http://apps.wandoujia.com/api/v1/search/"+key+"?max=12&hasAd=0&start="+i*12
	            		+ "&opt_fields=description,tags.*,likesCount,reason,ad,title,packageName,apks.size,icons.px68,apks.superior,installedCountStr,snippet,apks.versionCode,trusted,categories.*";
	            String json=SinglePageDownloader.getHtml(url);
	            if(json.contains("packageName"))
	            {
		            //获得json数据
		            try{
		            	ObjectMapper objectMapper=new ObjectMapper();
		            	Map<String, Object> map=objectMapper.readValue(json, Map.class);
		            	List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("appList");
		            	for(Map map1:list)
		            	{
		            		String pkgName=map1.get("packageName").toString();
		            		System.out.println(pkgName);
		            		page.addTargetRequest("http://www.wandoujia.com/apps/"+pkgName);
		            	}
		            }
		            catch(Exception e)
		            {
		            	e.printStackTrace();
		            }
	            }
            }
            
            //获取相关的app总得数量
           // String str=SinglePageDownloader.getHtml("")
           
            
            Iterator<String> iter = Sets.newHashSet(urlList).iterator();
            while (iter.hasNext()) {
                page.addTargetRequest(iter.next());
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.wandoujia\\.com/apps/.*").match()) {
        	return PageProWandoujia_Detail.getApkDetail(page);


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
