package com.appCrawler.pagePro;


import java.util.List;
import java.util.Set;


import com.appCrawler.pagePro.apkDetails.Wishdown_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 心愿下载
 * 网站主页：http://www.wishdown.com/search.asp?m=2&word=qq
 * 渠道编号：544
 * @author lisheng
 */
public class Wishdown implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.wishdown\\.com/search.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='down_list_start']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='page']").all();
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!page.getUrl().toString().contains("#softdown")){
					page.addTargetRequest(url);
				}
			}
		}
		if(page.getUrl().regex("http://www\\.wishdown\\.com/soft/\\d+\\.html").match())
			{
				return Wishdown_Detail.getApkDetail(page);
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
