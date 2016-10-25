package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Sopopo_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PostSubmit;

/**
 * 搜泡泡
 * 网站主页：http://down.sopopo.com/
 * Aawap #569
 * @author lisheng
 */
public class Sopopo implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.shouji\\.com/search.*").match())
		{
			String keyword=page.getUrl().toString().split("keyword=")[1];
			String param="tbname=down&show=title%2Ckeyboard&tempid=1&keyboard="+keyword+"&Submit22=%E6%90%9C+%E7%B4%A2";
			String html=PostSubmit.postGetData("http://www.sopopo.com/e/search/index.php",param);
			System.out.println(html);
			Html html1=Html.create(html);
			List<String> apps=html1.links("//section[@class='results']").all();
			page.addTargetRequests(apps);
			System.out.println(apps);
			
		}
		if(page.getUrl().regex("http://down\\.sopopo\\.com/.*/\\d+\\.html").match())
			{
				System.out.println("hello");
				return Sopopo_Detail.getApkDetail(page);
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
