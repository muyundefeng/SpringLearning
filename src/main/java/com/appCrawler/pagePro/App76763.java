package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 76763游戏
 * 网站主页：http://www.7663.com/game/0_0_0_0_1/
 * Aawap #550
 * @author lisheng
 */
public class App76763 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
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
