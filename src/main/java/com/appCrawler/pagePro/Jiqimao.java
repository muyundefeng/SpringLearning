package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Jiqimao_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #194 机器猫
 * http://www.jiqimao.com
 * @author Administrator
 *
 */
public class Jiqimao implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Jiqimao.class);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
	
		//index page		
		//http://www.jiqimao.com/search/index?a=soft&w=qq
		//http://www.jiqimao.com/search/index?a=game!%!%!%w=*#*#*#
		if(page.getUrl().regex("http://www\\.jiqimao\\.com/search/index\\?.*" ).match() ){
			//app的具体介绍页面											
			List<String> url1 = page.getHtml().links("//div[@class='applist']/ul").regex("http://www\\.jiqimao\\.com/.*").all();

			//把搜索软件的页面加入到链表中
			if(!page.getUrl().toString().contains("page=") && !page.getUrl().toString().contains("a=soft"))
				url1.add("http://www.jiqimao.com/search/index?a=soft&w="+getKeyword(page.getUrl().toString()));
			//添加下一页url(翻页)  
			List<String> url2 = page.getHtml().links("//div[@class='applist']/div").regex("http://www\\.jiqimao\\.com/search/index\\?.*").all();
			
			url1.addAll(url2);
				
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
				
			}

		}
		
		//the app detail page
		else if(page.getUrl().regex("http://www\\.jiqimao\\.com/soft-.*").match()
				|| page.getUrl().regex("http://www\\.jiqimao\\.com/game-.*").match()){

			return Jiqimao_Detail.getApkDetail(page);
		}
		
		
		return null;

	}
	
	public static String getKeyword(String url){
		if(url == null)
			return null;
		else 
			return url.substring(url.indexOf("w=")+2,url.length());
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
