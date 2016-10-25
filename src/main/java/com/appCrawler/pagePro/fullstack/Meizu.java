package com.appCrawler.pagePro.fullstack;

import java.util.Iterator;
import java.util.List;



import java.util.Set;

import com.appCrawler.pagePro.apkDetails.Meizu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 魅族[中国]
 * problem:全是js生成的页面无法获取url
 * 尝试用WebClient截取ajax请求的地址
 * 
 * 2015年12月1日16:42:18 一次打开索引页获取单个分类下的所有详情页，一个分类下的页面300+，因此一次获取1000就可以获取到全部的
 * 详情页
 * @author Administrator
 * 
 *
 */
public class Meizu implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
	public Apk process(Page page) {
		//http://app.meizu.com/
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://app.meizu.com/")){//添加游戏和应用
			page.addTargetRequest("http://app.meizu.com/apps/public/category/103/all/feed/index/0/1000");
			page.addTargetRequest("http://app.meizu.com/games/public/category/1006/all/feed/index/0/1000");
		}
		
		if (page.getUrl()//获取到所有的分类页面
				.toString().equals("http://app.meizu.com/apps/public/category/103/all/feed/index/0/1000")			
				|| page.getUrl()
				.toString().equals("http://app.meizu.com/games/public/category/1006/all/feed/index/0/1000")			
				) {// 获取所有分类页
			//System.out.println(page.getHtml().toString());
			List<String> categoryId = page.getHtml()
					.xpath("//ul[@id='categoryList']/li/@data-param")
					.all();
			String urlStirng = page.getUrl().toString();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(categoryId);			
			for (String urlId : cacheSet) {
				page.addTargetRequest(urlStirng.replace("103", urlId).replace("1006", urlId));
			}
		}
		if (page.getUrl()//添加详情页
				.regex("http://app\\.meizu\\.com/apps/public/category/\\d+/all/feed/index/0/1000")
				.match()
				|| page.getUrl()
						.regex("http://app\\.meizu\\.com/games/public/category/\\d+/all/feed/index/0/1000")
						.match()) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//div[@class='app download_container']").all();//详情页
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}


	//			 http://app.meizu.com/apps/public/detail?package_name=com.ushaqi.zhuishushenqi												
		if(page.getUrl().regex("http://app\\.meizu\\.com/apps/public/detail\\?.*").match() || 
		   page.getUrl().regex("http://app\\.meizu\\.com/games/public/detail\\?.*").match()){
			//如果是apk详细的介绍页面的话，则从该页面获取想要的信息
			Apk apk = Meizu_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
		}else{
			page.setSkip(true);
		}
		return null;
	}

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
