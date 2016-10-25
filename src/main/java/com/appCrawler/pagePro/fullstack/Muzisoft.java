package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Muzisoft_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 木子安卓http://www.muzisoft.com/
 * Muzisoft #217
 * @author DMT
 * @modify author lisheng
 */


public class Muzisoft implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(page.getHtml().toString());
		//List<String> urls =page.getHtml().links().regex("http://www\\.muzisoft\\.com/.*").all() ;
       if("http://www.muzisoft.com/".equals(page.getUrl().toString()))
       {
    	   page.addTargetRequest("http://www.muzisoft.com/soft/huwaiyundong/");
    	   page.addTargetRequest("http://www.muzisoft.com/game/xiuxianyizhi/");
    	   return null;
       }
       //page.getUrl().regex("http://www\\.muzisoft\\.com/game.*").match()||
	   if(page.getUrl().regex("http://www\\.muzisoft\\.com/game.*").match()||
			   page.getUrl().regex("http://www\\.muzisoft\\.com/soft.*").match())
       {
    	   List<String> categoryList=page.getHtml().xpath("//div[@class='slm']/a/@href").all();
    	   System.out.println(categoryList);
    	   List<String> apkList=page.getHtml().xpath("//div[@class='sl']/div/ul/a/@href").all();
    	   
    	   List<String> pageList=page.getHtml().xpath("//div[@id='pg']/ul/a/@href").all();
    	   List<String> pageList1=page.getHtml().xpath("//div[@id='page']/a/@href").all();
    	   System.out.println(pageList1);
    	   apkList.addAll(categoryList);
    	   apkList.addAll(pageList);
    	   apkList.addAll(pageList1);
    	   System.out.println(apkList);
    	   for(String str:apkList)
    	   {
    		   if(PageProUrlFilter.isUrlReasonable(str))
    		   {
    			   page.addTargetRequest(str);
    		   }
    	   }
    	   
       }
//		List<String> urls=page.getHtml().xpath("//div[@class='s-desc']/ul/a/@href").all();
//
//		Set<String> cacheSet = Sets.newHashSet();
//		if(urls!=null){
//			cacheSet.addAll(urls);
//		}	
//		
//		if(page.getUrl().toString().contains("game")){
//			
//		String info=null;
//		String nexturl=null;		
//		info=page.getHtml().xpath("//div[@id='pg']").toString();
//		if(info!=null){
//			nexturl=getNextUrl(info);
//			
//		}			
//		if(nexturl!=null){
//			cacheSet.add(nexturl);
//		}
//		}
//		if("http://www.muzisoft.com/soft/system/".equals(page.getUrl().toString())){
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/System/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/theme/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/lvyou/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/bangong/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/news/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/meihua/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/jiaoyou/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/jinrong/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/shenghuo/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/yingyin/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/map/"));
//			cacheSet.addAll(addPage("http://www.muzisoft.com/soft/shuaji/"));
//			cacheSet.add("http://www.muzisoft.com/game/xiuxianyizhi/");
//			cacheSet.add("http://www.muzisoft.com/game/pukeqipai/");	
//			cacheSet.add("http://www.muzisoft.com/game/tiyubisai/");	
//			cacheSet.add("http://www.muzisoft.com/game/wangluoyouxi/");
//			cacheSet.add("http://www.muzisoft.com/game/chuangguanmaoxian/");	
//			cacheSet.add("http://www.muzisoft.com/game/dongzuosheji/");
//			cacheSet.add("http://www.muzisoft.com/game/juesebanyan/");	
//			cacheSet.add("http://www.muzisoft.com/game/jingyingcelve/");	
//			cacheSet.add("http://www.muzisoft.com/game/paokujingsu/");			
//		}
//	
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)){
//				page.addTargetRequest(url);
//			}
//		}
		//提取页面信息
		if(page.getUrl().regex("http://www\\.muzisoft\\.com/soft/\\d+\\.html").match()){	
			Apk apk = Muzisoft_Detail.getApkDetail(page);
			
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
		String regex="href=\"([^\"]+)\".*>下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
	
	private List<String> addPage(String str){
		List<String> tmp=new ArrayList<String>();
		for(int i=1;i<=50;i++){
			tmp.add(str+"list_"+i+".html");
		}
		return tmp;		
	}
	
}
