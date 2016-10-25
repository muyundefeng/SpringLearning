package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Z52_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #271
 * 飞翔下载
 * @author Administrator
 *
 */
public class Z52 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://www\\.52z\\.com/search\\?keyword=.*").match()){
			//app的具体介绍页面											
			List<String> detailUrl = page.getHtml().xpath("//div[@class='result_box']/dl/dt/a/@href").all();
	       // detailUrl.addAll(page.getHtml().links("//div[@class='pager']/div/a/@href").all());
	        List<String> url=page.getHtml().xpath("//div[@class='pager']/div/a/@href").all();
	        detailUrl.addAll(url);
	        //	        for(String str:detailUrl)
//	        {
//	        	if(！str.contains("专题"))
//	        	{
//	        		
//	        	}
//	        }
	        //	        System.out.println(url);
//			System.out.println("detailUrl size="+detailUrl.size());
			HashSet<String> urlSet = new HashSet<String>(detailUrl);
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		if(page.getUrl().regex("http://www\\.52z\\.com/soft/.*").match())
			{
				
				return Z52_Detail.getApkDetail(page);
				
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
