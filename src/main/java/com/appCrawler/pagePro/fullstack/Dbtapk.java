package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Dbtapk_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓大不同http://www.dbtapk.com/
 * Dbtapk #213
 * @author DMT
 */


public class Dbtapk implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(500);

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(page.getHtml());
		if("http://www.dbtapk.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.dbtapk.com/html/kapai/index.html");
			page.addTargetRequest("http://www.dbtapk.com/html/huihe/index.html");
			page.addTargetRequest("http://www.dbtapk.com/html/wuxia/index.html");
			page.addTargetRequest("http://www.dbtapk.com/html/dongman/index.html");
			page.addTargetRequest("http://www.dbtapk.com/html/hengban/index.html");
			page.addTargetRequest("http://www.dbtapk.com/html/yangcheng/index.html");
			return null;
		}
		if(page.getUrl().regex("http://www\\.dbtapk\\.com/html/.*").match())
		{
			//System.out.println(page.getHtml().toString());
			List<String> apkList=page.getHtml().xpath("//ul[@class='gameList']/li/dl/a/@href").all();
			System.out.println(apkList);
			//List<String> pageList=page.getHtml().xpath("").all();
			//String pageNO=page.getHtml().xpath("//span[@id='totalpage']/text()").toString();
		    page.addTargetRequest("http://www.dbtapk.com/html/yangcheng/index_1.html");
		    page.addTargetRequest("http://www.dbtapk.com/html/jishi/index_2.html");
		    page.addTargetRequest("http://www.dbtapk.com/html/jishi/index_1.html");
			page.addTargetRequest("http://www.dbtapk.com/html/kapai/index_1.html");
		    page.addTargetRequest("http://www.dbtapk.com/html/yangcheng/index_1.html");
			for(String url:apkList)
			{
				if(!url.contains("downLoad"))
				{
					page.addTargetRequest(url);
				}
			}
			
		}
//		//System.out.println(page.getHtml().toString());
//		String info=null;
//		String nexturl=null;
//		List<String> urls=page.getHtml().xpath("//dl[@class='android_dl4']/a[1]/@href").all();
//		nexturl=page.getHtml().xpath("//div[@class='item-list2']/a[2]/@href").toString(); 		
//		Set<String> cacheSet = Sets.newHashSet();	   
//	    	cacheSet.addAll(urls);	 
//	    if(nexturl!=null){
//	    	cacheSet.add(nexturl);
//	    }
//	    if("http://www.dbtapk.com/html/jishi/".equals(page.getUrl().toString())){
//	    	cacheSet.add("http://www.dbtapk.com/html/kapai/");
//	    	cacheSet.add("http://www.dbtapk.com/html/huihe/");
//	    	cacheSet.add("http://www.dbtapk.com/html/wuxia/");
//	    	cacheSet.add("http://www.dbtapk.com/html/dongman/");
//	    	cacheSet.add("http://www.dbtapk.com/html/hengban/");
//	    	cacheSet.add("http://www.dbtapk.com/html/yangcheng/");
//	    	
//	    }
//	    
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)
//			 && !url.contains("http://www.dbtapk.com/item/downLoad.asp")){
//				page.addTargetRequest(url);
//			}
//		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.dbtapk\\.com/html/.*/[0-9]+\\.html").match()){
	
			
			Apk apk = Dbtapk_Detail.getApkDetail(page);
			
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
	
	private static String getNextUrl(String str){
    	String tmp=null;    	
    	//href="/games/2.html" class="a1">下一页</a></div>
		String regex="href=\"([^\"]+)\" class=\"a1\".*>下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
}
