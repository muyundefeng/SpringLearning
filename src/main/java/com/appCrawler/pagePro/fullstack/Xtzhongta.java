package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Xtzhongta_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓精品网 http://www.xtzhongda.com/
 * Xtzhongta #216
 * @author DMT
 * @modify author lisheng
 */


public class Xtzhongta implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//System.out.println(page.getHtml().toString());
		//List<String> urls =page.getHtml().links().regex("http://www\\.xtzhongda\\.com/soft.*").all() ;
		if("http://www.xtzhongda.com/".equals(page.getUrl().toString()))
		{
			List<String> categorylist=page.getHtml().xpath("//ul[@id='category']/li/b/a/@href").all();
			page.addTargetRequests(categorylist);
			System.out.println(categorylist);
			return null;
		}
		if(page.getUrl().regex("http://www\\.xtzhongda\\.com/soft/[a-zA-z]+/").match())
		{
			System.out.println("hello ");
		  List<String> apkList=page.getHtml().xpath("//div[@class='boxBody']/dl/table/tbody/tr/td/dt/a/@href").all();
		  List<String> pageList=page.getHtml().xpath("//div[@class='page']/a/@href").all();
		  apkList.addAll(pageList);
		  System.out.println(apkList);
		  for(String url:apkList)
		  {
			  if(PageProUrlFilter.isUrlReasonable(url))
			  {
				  page.addTargetRequest(url);
			  }
		  }
		}
//		String info=null;
//		String nexturl=null;
//		List<String> urls=page.getHtml().xpath("//div[@class='boxbody']/dl//tbody//tr/td/dt/a/@href").all();
//		info=page.getHtml().xpath("//div[@class='page']").toString();
//		if(info!=null){
//			nexturl=getNextUrl(info);
//		}	 		
//		Set<String> cacheSet = Sets.newHashSet();
//		if(nexturl!=null){
//			cacheSet.add(nexturl);
//		}	
//			cacheSet.addAll(urls);
//		if("http://www.xtzhongda.com/soft/yuleyouxi/".equals(page.getUrl().toString())){
//			cacheSet.add("http://www.xtzhongda.com/soft/richangyingyong/");
//			cacheSet.add("http://www.xtzhongda.com/soft/xitonggongju/");
//			cacheSet.add("http://www.xtzhongda.com/soft/network/");
//			cacheSet.add("http://www.xtzhongda.com/soft/yingyinmeiti/");
//			cacheSet.add("http://www.xtzhongda.com/soft/dituzidian/");
//			cacheSet.add("http://www.xtzhongda.com/soft/shoujimeihua/");
//			cacheSet.add("http://www.xtzhongda.com/soft/xitongshuaji/");
//			cacheSet.add("http://www.xtzhongda.com/soft/hanhuaruanjian/");
//		}
//		//cacheSet.addAll(urls);
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)){
//				page.addTargetRequest(url);
//			}
//		}
//		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.xtzhongda\\.com/soft/\\d+\\.html").match()){
	
			
			Apk apk = Xtzhongta_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
			}
		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
	
//	private static String getNextUrl(String str){
//    	String tmp=null;    	
//    	//href="/games/2.html" class="a1">下一页</a></div>
//		String regex="<a href=\"([^\"]+)\">下一页";
//		Pattern pattern = Pattern.compile(regex);  
//        Matcher matcher = pattern.matcher(str); 
//        if(matcher.find()){        	
//        	tmp=matcher.group(1).toString();      	
//        }
//    	return tmp;   	
//    }
}
