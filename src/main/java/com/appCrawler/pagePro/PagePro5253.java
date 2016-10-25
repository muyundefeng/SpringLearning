package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PagePro5253_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Maps;
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
 * 5253手游网[中国] app搜索抓取
 * url:http://www.5253.com/search.html?searchKey=qq
 *
 * @version 1.0.0
 */
public class PagePro5253 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro5253.class);

    private static int flag=1;
    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
            setSleepTime(PropertiesUtil.getInterval());

    /**
     * 缓存对象
     */
    private Map<String, Apk> apkMapCache = Maps.newConcurrentHashMap();

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

        // 获取搜索页面
        if (page.getUrl().regex("http://www\\.5253\\.com/search\\.html\\?searchKey=.*").match()
        		||page.getUrl().regex("http://www\\.5253\\.com/.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> urlList = page.getHtml().xpath("//ul[@id='games_list_ul']/li/a[@class='pic']/@href").all();
            //urlList.addAll(page.getHtml().links("//div[@class='right assign-page2']").all());
            //构造url连接
            //if()
            
            if(flag==1)
            {
            	String keyWord=page.getUrl().toString().split("=")[1];
                System.out.println(keyWord);
	            for(int i=1;i<=5;i++)
	            {
	            	String url="http://www.5253.com/search/ajax-search?page="+i+"&searchKey="+keyWord+"&queryType=1&gameOnly=true&deviceType=";
	            	String json=SinglePageDownloader.getHtml(url);
	            	if(json.contains("gameId"))
	            	{
		        		try{
		        			ObjectMapper objectMapper=new ObjectMapper();
		        			Map<String, Object> map=objectMapper.readValue(json, Map.class);
		        			List<Map<String,Object>> gameList=(List<Map<String,Object>>)map.get("gametypes");
		        			for(Map map1:gameList)
		        			{
		        				Map<String,Object> game=(Map<String,Object>)map1.get("game");
		        				String idString=game.get("gameId").toString();
		        				System.out.println(idString);
		        				//构造app的url地址
		        				String appurl="http://www.5253.com/game/"+idString+".html";
		        				page.addTargetRequest(appurl);
		        			}
		        		}
		        		catch(Exception e)
		        		{e.printStackTrace();}
	            	}
	            	else{
	            		break;
	            	}
	            	
	            }
	            flag++;
            }
        	Iterator<String> iter = Sets.newHashSet(urlList).iterator();
            while (iter.hasNext()) {
                page.addTargetRequest(iter.next());
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.5253\\.com/game/\\d+\\.html").match()) {
        	System.out.println("****"+page.getUrl().toString());
        	return PagePro5253_Detail.getApkDetail(page);

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
