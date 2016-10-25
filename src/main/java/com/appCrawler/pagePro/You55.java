package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #370
 * 55游·http://www.55you.com/tafang/
 * 搜索接口无效
 * @author Administrator
 *
 */
public class You55 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
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
