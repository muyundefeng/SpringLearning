package com.appCrawler.pagePro.fullstack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Vsoyou_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 威搜游 http://vsoyou.com/ Vsoyou #111 2015年11月9日10:14:43 翻页是post请求的，构造请求获取所有的翻页
 * 
 * @author DMT
 */

public class Vsoyou implements PageProcessor {
	public static boolean flag = true;
	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {

		if (page.getUrl().toString().equals("http://vsoyou.com/")) {
			Map<String, String> header = getHeaderMap();
			Map<String, String> formData = new HashMap<String, String>();
			formData.put("pageNum", "1");
			Html html = new Html(SinglePageDownloader.getHtml(
					"http://vsoyou.com/game/s0.htm", "POST", formData, header));
			String pageCount = getPageCount(html);
			int pageNumber = Integer.parseInt(pageCount.replace(" ", ""));
			System.out.println("pageCount=" + pageCount);
			// System.out.println(html.toString());
			for (int i = 2; i < pageNumber; i++) {//每次修改提交表单的pageNum，获取页面数据
				List<String> urls = html.links("//div[@id='gm_list_all']")//获取每页的apk的url，并添加到队列中
						.all();
				LOGGER.info("PageNumber "+(i-1)+" Get Page:"+"http://vsoyou.com/game/s0.htm");
				Set<String> cacheSet = Sets.newHashSet();
				cacheSet.addAll(urls);
				for (String temp : cacheSet) {
					if (PageProUrlFilter.isUrlReasonable(temp) && !temp.contains("/download/")){
						page.addTargetRequest("http://vsoyou.com"+temp);
						LOGGER.info("add url:"+"http://vsoyou.com"+temp);
					}
				}
				formData.put("pageNum", String.valueOf(i));
				html = new Html(SinglePageDownloader.getHtml(
						"http://vsoyou.com/game/s0.htm", "POST", formData, header));
			}
		}
		//
		// Set<String> cacheSet = Sets.newHashSet();
		// cacheSet.addAll(urls);
		// for (String temp : cacheSet) {
		// if(!temp.startsWith("http://vsoyou.com/download/") &&
		// PageProUrlFilter.isUrlReasonable(temp) )
		// page.addTargetRequest(temp);
		// }
		// if(flag){
		// for(int i=9000;i<15000;i++){
		// page.addTargetRequest("http://vsoyou.com/game/"+i+".htm");
		// }
		// page.addTargetRequest("http://vsoyou.com/game/93.htm");
		// flag = false;
		// }

		// 提取页面信息
		if (page.getUrl().regex("http://vsoyou\\.com/game.*").match()) {
			Apk apk = Vsoyou_Detail.getApkDetail(page);

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

	public String getPageCount(Html html) {
		String pageCount = html.xpath("//div[@class='list_all_Pager']")
				.toString();
		return StringUtils.substringBetween(pageCount, "条记录", "页");
	}

	public Map<String, String> getHeaderMap() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		header.put("Cache-Control", "max-age=0");
		header.put("Connection", "keep-alive");
		header.put("Content-Length", "9");
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put("DNT", "1");
		header.put("Host", "vsoyou.com");
		header.put("Origin", "http://vsoyou.com");
		header.put("Referer", "http://vsoyou.com/game/s0.htm");
		header.put("Upgrade-Insecure-Requests", "1");
		header.put(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		return header;
	}
}
