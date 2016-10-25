package com.appCrawler.pagePro;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.PageProapkzu_Detail;
import com.appCrawler.utils.PostSubmit;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 安族网 http://www.apkzu.com/
 * PageProapkzu #185
 * 伪造搜索接口url地址，从中提取搜索关键字
 * @author DMT
 */
public class PageProapkzu implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(PageProapkzu.class);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//System.out.println(page.getHtml());
		if(page.getUrl().toString().contains("http://www.coolapk.com/search"))
		{
			String url=page.getUrl().toString();
			String keyWord=url.split("=")[1];
			System.out.println(keyWord);
			String realUrl="http://m.apkzu.com/e/search/index.php";
			String param="show=title%2Ckeyboard&tempid=5&classid=1&keyboard="+keyWord+"&search=";
			Html page1=Html.create(PostSubmit.postGetData(realUrl,param));
			List<String> apps=page1.xpath("//dd[@class='seagamebox']/div[1]/a/@href").all();
			System.out.println(apps);
			for(String str:apps)
			{
				page.addTargetRequest("http://m.apkzu.com"+str);
			}
			//return null;
		}
		
		//the app detail page
		else if(page.getUrl().regex("http://m\\.apkzu\\.com/.*").match()){

			return PageProapkzu_Detail.getApkDetail(page);
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
