package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import com.appCrawler.pagePro.apkDetails.Cn314_Detail;


import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 
 * 228  中国派   http://www.cn314.com/game/padgame/
 * 
 * @author lisheng
 *
 */
public class Cn314 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://zhannei\\.baidu\\.com/cse/site\\?.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@id='results']/div/h3/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			apps.addAll(pages);
			for(String str:apps)
			{
				if(PageProUrlFilter.isUrlReasonable(str))
				{
					page.addTargetRequest(str);
				}
			}
		}
		if((page.getUrl().regex("http://www\\.cn314\\.com/soft/.+").match()
				||page.getUrl().regex("http://www\\.cn314\\.com/game/.+").match())
				&&!page.getUrl().toString().contains("index")
				&&!page.getUrl().toString().contains("xinwen")
				&&!page.getUrl().toString().contains("pingche")
				&&!page.getUrl().toString().contains("xinpin"))
			{
				return Cn314_Detail.getApkDetail(page);
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
