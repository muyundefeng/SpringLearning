package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Yruan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 亿软网 http://www.yruan.com/ Yruan #125 未完成
 * 
 * @author DMT
 */

public class Yruan implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {

		if (page.getUrl().toString().equals("http://www.yruan.com")) {
			page.addTargetRequest("http://www.yruan.com/category/1/page/1");// 加入可以找到索引页的页面
		}

		 if(page.getUrl().toString().equals("http://www.yruan.com/category/1/page/1")){
		 List<String>
		 urlList=page.getHtml().links("//ul[@class='list']").all();//加入所有索引页的分类
		 page.addTargetRequests(urlList);
		 }

		if (page.getUrl().regex("http://www\\.yruan\\.com/category/.+").match()) {
		
			List<String> urlList_detail = page.getHtml()
					.links("//ul[@id='list_softs']").all();
			List<String> urlList_page = page.getHtml()
					.links("//div[@class='Page']").all();
			urlList_detail.addAll(urlList_page);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList_detail);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp)
						&& !temp.contains("http://www.yruan.com/down.php"))
					page.addTargetRequest(temp);
			}
		}

		if (page.getUrl().regex("http://www\\.yruan\\.com/softdetail/\\d+/")
				.match()) {
			String downloadString = page.getHtml()
					.xpath("//div[@class='down_link']/a/@href").toString();
			page.addTargetRequest(downloadString);

		}
		// 提取页面信息 http://www.yruan.com/softdown.php?id=8443&phoneid=
		if (page.getUrl().regex("http://www\\.yruan\\.com/softdown.*").match()) {

			Apk apk = Yruan_Detail.getApkDetail(page);

			page.putField("apk", apk);
			if (page.getResultItems().get("apk") == null) {
				page.setSkip(true);
			}
		} else {
			page.setSkip(true);
		}
		return null;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	public Site getSite() {
		return site;
	}
}
