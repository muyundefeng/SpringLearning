package com.appCrawler.pagePro;

import java.nio.channels.AcceptPendingException;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Wankr_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/*
 * 渠道编号:384
 * 网站主页:http://www.wankr.com.cn/gamecenter/3-0-0-0-0
 * 搜索接口无效
 */
public class Wankr implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://search\\.wankr\\.com\\.cn/cse/search.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@id='results']/div[@class='result f s0']/h3/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			apps.addAll(pages);
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://www\\.wankr\\.com\\.cn/gamecenter/.*").match())
		{
			return Wankr_Detail.getApkDetail(page);
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
