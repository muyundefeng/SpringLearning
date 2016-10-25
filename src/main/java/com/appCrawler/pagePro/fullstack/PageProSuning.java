package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProSuning_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.List;
import java.util.Set;

/**
 * 苏宁开放平台 app搜索抓取
 * url:http://app.suning.com/android
 * id:188
 * @version 1.0.0
 */
public class PageProSuning implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProSuning.class);

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
		if (page.getUrl().toString().equals("http://app.suning.com/android")) {
			for(int i=1;i<101;i++){
			page.addTargetRequest("http://app.suning.com/android/app?gid=4&cid=0&sort=5&star=0&os=&icon=0&down=0&page="+i);// 添加游戏索引
			page.addTargetRequest("http://app.suning.com/android/app?gid=1&cid=0&sort=5&star=0&os=&icon=0&down=0&page="+i);// 添加应用索引
			}
		}

		if (page.getUrl()
				.regex("http://app\\.suning\\.com/android/app\\?gid=4&cid=0&sort=5&star=0&os=&icon=0&down=0&page=\\d+")
				.match()
				|| page.getUrl()
						.regex("http://app\\.suning\\.com/android/app\\?gid=1&cid=0&sort=5&star=0&os=&icon=0&down=0&page=\\d+")
						.match()) {// 获取所有分类页
			//System.out.println(page.getHtml().toString());
			List<String> urlList = page.getHtml()
					.links("//div[@class='app-result']/ul")
					.regex("http://app\\.suning\\.com/android/app/page\\?pack=.+").all();
			LOGGER.info("add "+urlList.size()+" pages");
//			List<String> pageList = page.getHtml()
//					.links("//div[@class='app-pages']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
//			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

        // 获取信息	http://app.suning.com/android/app/page?pack=com.suning.gamemarket
        if (page.getUrl().regex("http://app\\.suning\\.com/android/app/page\\?pack=.+").match()) {
        	//System.out.println(page.getHtml().toString());
			Apk apk = PageProSuning_Detail.getApkDetail(page);
			
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
