package com.appCrawler.pagePro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.appCrawler.pagePro.apkDetails.PagePro1Mobile_Detail;
import com.appCrawler.utils.PropertiesUtil;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 第一手机 app搜索抓取
 * url:http://www.1mobile.tw/index.php?c=search.json&keywords=MT
 *
 * @version 1.0.0
 */
public class PagePro1Mobile implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro1Mobile.class);

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
      //   System.out.println("pagehtml="+page.getHtml().toString());
        // 获取搜索页面
        if (page.getUrl().regex("http://www\\.1mobile\\.tw/index\\.php\\?c=search\\.json&keywords=.*").match()) {
        //	System.out.println("sssssss"+page.toString());
            LOGGER.debug("match success, url:{}", page.getUrl());

            JSONObject json = JSON.parseObject(page.getRawText());
            JSONArray appList = json.getJSONArray("appList");

            for (Object obj : appList) {
                JSONObject app = (JSONObject)obj;
                page.addTargetRequest(app.getString("appLink"));
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.1mobile\\.tw/.*").match()) {
            return PagePro1Mobile_Detail.getApkDetail(page);
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