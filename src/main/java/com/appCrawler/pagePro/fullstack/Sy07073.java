package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Sy07073_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 07073http://sy.07073.com/2-0-0
 *  #207
 * @author DMT
 */

public class Sy07073 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		 //System.out.println(page.getHtml());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("http://www.07073.com/".equals(page.getUrl().toString()))
		{
			List<String> urlList=new ArrayList<String>();
			//获取app列表的总页数
			for(int i=1;;i++)
			{
				System.out.println(i);
				String url="http://sy.07073.com/2-0-0?reqs=1&page="+i;
				String raString=SinglePageDownloader.getHtml(url);
				if(!raString.contains("window.open"))
				{
					System.out.println(i);
					break;
				}
				else{
					
					urlList.addAll(getUrl(raString));
				}
			}
			page.addTargetRequests(urlList);
		}
		
//		
//		
//		
//		
//		if("http://www.07073.com/".equals(page.getUrl().toString()))
//		{
//			page.addTargetRequest("http://sy.07073.com/2-0-0");
//			return null;
//		}
//	    int currentpagenum=0;
//		//System.out.println(page.getHtml().toString());
//		List<String> urls =new ArrayList<String>();
//		String info=null;	
//		currentpagenum=getCurrentPage(page.getUrl().toString());
//        info=page.getHtml().toString(); 
//		urls=getUrl(info);
//		
//		Set<String> cacheSet = Sets.newHashSet();
//		if(currentpagenum!=0 && !info.contains("{&quot;status&quot;:0,&quot;nums&quot;:0,&quot;html&quot;:&quot;&quot;")){
//			cacheSet.add("http://sy.07073.com/2-0-0?reqs=1&page="+(currentpagenum+1));
//		}
//		//修改
//		if(currentpagenum==101){
//			cacheSet.add("http://sy.07073.com/2-0-0?reqs=1&page="+(currentpagenum+2));
//		}
//		cacheSet.addAll(urls);
//		if("http://sy.07073.com/2-0-0".equals(page.getUrl().toString())){
//			cacheSet.add("http://sy.07073.com/2-0-0?reqs=1&page=1");
//		}
//	
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url) &&
//			 !url.contains("http://sy.07073.com/1-")){
//				page.addTargetRequest(url);
//			}
//		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://sy\\.07073\\.com/game/\\d+").match()){
	
			
			Apk apk = Sy07073_Detail.getApkDetail(page);
			
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
//	private static int getCurrentPage(String str){
//    	int num=0;    	
//    	//href="/games/2.html" class="a1">下一页</a></div>
//		String regex="page=(.*)";
//		Pattern pattern = Pattern.compile(regex);  
//        Matcher matcher = pattern.matcher(str); 
//        if(matcher.find()){        	
//        	String s=null;
//        	s=matcher.group(1).toString();
//        	num=Integer.parseInt(s);
//        }
//    	return num;   	
//    }
	
	private static List<String> getUrl(String str){
    	List<String> tmp=new ArrayList<String>();    	
		String regex="([^\']+)','_blank\'";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){ 
        	//System.out.println(matcher.group(1).toString());
        	tmp.add(matcher.group(1).toString().replace("\\", ""));
        }
       // System.out.println(tmp);
    	return tmp;   	
    }
	
	
	
}
