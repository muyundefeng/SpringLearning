package com.appCrawler.pagePro;


import java.util.List;
import java.util.Set;

import com.appCrawler.pagePro.apkDetails.Ka18183_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 18183发号平台  http://ka.18183.com/index.shtml
 * 渠道编号:386
 * 搜索接口:http://ku.18183.com/list-0-0-0.html?q=*#*#*#
 * @author lisheng
 */
public class Ka18183 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://ku\\.18183\\.com/list-0-0-\\d+\\.html\\?q=.*").match()){						
			List<String> apkList=page.getHtml().xpath("//div[@class='games_recommended']/ul/li/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@class='fy']/ul/li/a/@href").all();
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
				if(page.getUrl().regex("http://ku\\.18183\\.com/.*").match()
						&&!page.getUrl().toString().contains("list-0-0-0"))
				{
					return Ka18183_Detail.getApkDetail(page);
					
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
