package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game9669_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 9669手游网
 * 网站主页：http://www.9669.com/update-0-0-52-0-1.html
 * 搜索接口：
 * http://zhannei.baidu.com/cse/search?q=%E8%B7%91%E9%85%B7&s=4074203811401978859&entry=1
 * 渠道编号：393
 * @author lisheng
 */
public class Game9669 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://zhannei\\.baidu\\.com/cse/search\\?q=.*").match()){
			List<String> apkList=page.getHtml().xpath("//div[@id='results']/div/h3/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			apkList.addAll(pageList);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apkList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		//提取页面信息
				if(page.getUrl().regex("http://www\\.9669\\.com/wy/.*").match())
				{
					
					return Game9669_Detail.getApkDetail(page);
					
					
					}
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
