package com.appCrawler.pagePro;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Anzhuo_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *渠道编号：333
 *网站主页：http://oss.cmgame.com/android/?spm=www.pdindex.android.dh.2
 * @author DMT
 */
public class Cmgame implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(3).setSleepTime(0);
	@Override
	public Apk process(Page page) {
		
//		if(page.getUrl().xpath("http://oss\\.cmgame\\.com/search/game\\?q=.*").match())
//		{
//			List<String> apkList=page.getHtml().xpath("//div[@class='seabox']/div/div[1]/a/@href").all();
//			List<String> pageList=page.getHtml().xpath("//div[@class='']")
//		}
		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return null;
	}

}
