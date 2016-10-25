package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProapkzu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.List;
import java.util.Set;

/**
 *安族网 app搜索抓取
 * url:http://m.apkzu.com/
 *	翻页的url是通过提取信息构造的
 *	@author DMT
 * @version 1.0.0
 */
public class PageProapkzu implements PageProcessor {
    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProapkzu.class);

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
    	try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://m.apkzu.com/game/")) {
			page.addTargetRequest("http://m.apkzu.com/e/ikaimi/m/game.list.php?page=0&ph=1&classid=1&type=0&sign=0&platform=0&state=0&tempid=18&line=20");// 添加游戏索引
			page.addTargetRequest("http://m.apkzu.com/bwlb3/");//单击游戏只有这一个
			page.addTargetRequest("http://m.apkzu.com/wxyx/");//微信游戏只有这一个

		}

		if (page.getUrl()
				.regex("http://m\\.apkzu\\.com/e/ikaimi/m/game\\.list\\.php\\?page=\\d+&ph=1&classid=1&type=0&sign=0&platform=0&state=0&tempid=18&line=20")
				.match()
				) {
		//	System.out.println("in this:"+page.getUrl().toString());
			List<String> urlList = page.getHtml()
					.links()
					.regex("http://m\\.apkzu\\.com/\\w+/").all();
			if(urlList.size() >0){
			urlList.add("http://m.apkzu.com/e/ikaimi/m/game.list.php?page="
					+ getNextPageNum(page.getUrl().toString())
					+ "&ph=1&classid=1&type=0&sign=0&platform=0&state=0&tempid=18&line=20");
			}
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);			
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

        // 获取信息
        if (page.getUrl().regex("http://m\\.apkzu\\.com/\\w+/").match()) {
			Apk apk = PageProapkzu_Detail.getApkDetail(page);
			
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
	
	private int getNextPageNum(String urlString){
		//http://m.apkzu.com/e/ikaimi/m/game.list.php?page=0&ph=1&classid=1&type=0&sign=0&platform=0&state=0&tempid=18&line=20
		return Integer.valueOf(StringUtils.substringBetween(urlString, "page=","&ph=1"))+1;
	}
}
