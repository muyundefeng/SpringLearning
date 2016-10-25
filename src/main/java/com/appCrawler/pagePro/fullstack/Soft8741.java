package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Soft8741_Detail2;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓地带  http://www.8471.com/ruanjian
 * Soft8471 #210
 * @author DMT
 */


public class Soft8741 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		
		if("http://www.8471.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.8471.com/ruanjian");
			page.addTargetRequest("http://www.8471.com/youxi");
			return null;
		}
		if(page.getUrl().regex("http://www\\.8471\\.com/ruanjian.*").match()
				||page.getUrl().regex("http://www\\.8471\\.com/youxi.*").match())
		{
			List<String> apkList=page.getHtml().xpath("//dl[@id='lsr']/dd/p/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@id='pg']/a/@href").all();
			apkList.addAll(pageList);
			for(String url:apkList)
			{
				if(PageProUrlFilter.isUrlReasonable(url))
				{
					page.addTargetRequest(url);
				}
			}
		}
//		List<String> urls=new ArrayList<String>();
//		String info=null;
//		String nexturl=null;
//		info=page.getHtml().xpath("//dl[@id='lsr']/dd").toString();	 
//		if(info!=null){
//		  urls=gettUrls(info);
//		 }
//		info=page.getHtml().xpath("//div[@id='pg']").toString();
//		if(info!=null)
//	    nexturl=getLastUrl(info);	
//		Set<String> cacheSet = Sets.newHashSet();
//		if(page.getUrl().toString().equals("http://www.8471.com/ruanjian"))
//			cacheSet.add("http://www.8471.com/youxi");
//		if(nexturl!=null){
//			cacheSet.add(nexturl);
//		}
//	
//		cacheSet.addAll(urls);
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)){
//				page.addTargetRequest(url);
//			}
//		
//		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.8471\\.com/\\d+").match()){

			
			Apk apk = Soft8741_Detail2.getApkDetail(page);
			
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
	
	private static List<String> gettUrls(String str){
    	List<String> tmp=new ArrayList<String>();    	
    	//href="/games/2.html" class="a1">下一页</a></div>
		String regex="<a href=\"([^\"]+)\" j=";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){        	
        	tmp.add(matcher.group(1).toString());      	
        }
    	return tmp;   	
    }
	
	private static String getLastUrl(String str){
    	String tmp=null;    	
    	//<a href="/ruanjian_2">下一页</a>
		String regex="<a href=\"([^\"]+)\">下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
}
