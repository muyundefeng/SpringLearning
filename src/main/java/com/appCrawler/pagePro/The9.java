package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.The9_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 九号下载
 * 网站主页:http://www.9ht.com/
 * 渠道编号:378
 * 搜索接口无效
 */
public class The9 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://s.9ht.com/cse/search\\?q=.*&p=\\d{1}.*").match())
		{
			List<String> apks=page.getHtml().xpath("//div[@class='result-item result-game-item']/div/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			apks.addAll(pages);
			page.addTargetRequests(apks);
		}
		if(page.getUrl().regex("http://www\\.9ht\\.com/xz/\\d*\\.html").match())
		{
			return The9_Detail.getApkDetail(page);
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
