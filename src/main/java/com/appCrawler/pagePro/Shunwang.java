package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Shunwang_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 渠道编号：334
 * 网站主页：http://mg.shunwang.com/
 * 搜索url形式：http://app.shunwang.com/?app=games&action=mg_list&type=all&c_first=all&wd=%E4%B9%9D%E9%98%B4
 *
 */
public class Shunwang implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://app\\.shunwang\\.com/\\?app=games&action=mg_list&type=all&c_first=all&wd=.*").match())
		{
			List<String> apkList=page.getHtml().xpath("//ul[@class='lib-ct-bd down']/li/a/@href").all();
			page.addTargetRequests(apkList);
			System.out.println(apkList);
		}
		if(page.getUrl().regex("http://mg\\.shunwang\\.com/zlk/.*").match())
		{
			return Shunwang_Detail.getApkDetail(page);
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
