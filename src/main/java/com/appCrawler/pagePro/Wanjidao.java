package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.parser.Keywords;
import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Wanjidao_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 玩机岛
 * 网站主页：http://www.wanjidao.com/
 * Aawap #547
 * @author lisheng
 */
public class Wanjidao implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.wanjidao.com/search.*").match())
		{
			if(flag==1)
			{
				String keyword=page.getUrl().toString();
				keyword=keyword!=null?keyword.split("k=")[1]:null;
				String url="http://www.wanjidao.com/search?type=wangyou&k="+keyword;
				page.addTargetRequest(url);
				flag++;
			}
			
			
			List<String> apps=page.getHtml().links("//ul[@class='app-list clearfix']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='pager']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.wanjidao\\.com/wangyou/\\w+").match()
				||page.getUrl().regex("http://www\\.wanjidao\\.com/danji/.*").match())
			{
				return Wanjidao_Detail.getApkDetail(page);
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
