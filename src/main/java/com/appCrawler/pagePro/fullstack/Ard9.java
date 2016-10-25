package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ard9_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * ard9  http://www.ard9.com/soft/
 * Ard9 #215
 * @author DMT
 * @modify author lisheng
 */


public class Ard9 implements PageProcessor{

//	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
//			setSleepTime(PropertiesUtil.getInterval());
	Site site = Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if("http://www.ard9.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.ard9.com/soft/list_2_1.html");
			page.addTargetRequest("http://www.ard9.com/game/list_3_1.html");
			return null;
		}
		if(page.getUrl().regex("http://www\\.ard9\\.com/soft/list_2_\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.ard9\\.com/game/list_3_\\d+\\.html").match())
		{
			List<String> apkList=page.getHtml().xpath("//div[@class='game_soft']/div[@class='gamesoft_info index_gamesoftinfo']/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//ul[@class='pagelist']/li/a/@href").all();
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
//		List<String> urls =page.getHtml().xpath("//div[@class='gamesoft_info index_gamesoftinfo']/a/@href").all();
//		info=page.getHtml().xpath("//ul[@class='pagelist']").toString();
//		if(info!=null){
//			nexturl=getLastUrl(info);
//		}
//		System.out.println(nexturl);
//		Set<String> cacheSet = Sets.newHashSet();
//		if(nexturl!=null){
//			cacheSet.add(nexturl);
//		}		
//		cacheSet.addAll(urls);
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)){
//				page.addTargetRequest(url);
//			}
//		}
//		
	
		//提取页面信息
//		if(page.getUrl().regex("http://www\\.ard9\\.com/szsc/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/xtgj/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/xxzq/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/dzts/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/bgxx/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/wlll/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/sjlt/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/yysp/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/shzs/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/xiaq/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/ztmh/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/yzyx/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/scjj/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/tyundong/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/dzsj/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/qpyx/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/jsby/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/mnjy/。*").match()
//		   || 	page.getUrl().regex("http://www\\.ard9\\.com/other/。*").match()   
			if(page.getUrl().regex("http://www\\.ard9\\.com/.*/[0-9]+\\.html").match()
					&&!page.getUrl().regex("http://www\\.ard9\\.com/soft/list_2_\\d+\\.html").match()
					&&!page.getUrl().regex("http://www\\.ard9\\.com/game/list_3_\\d+\\.html").match()){			
				Apk apk = Ard9_Detail.getApkDetail(page);			
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
	
	
	private static String getLastUrl(String str){
    	String tmp=null;    	
    	//<a href="/soft/?tid=2-82305-3.html">下一页</a>
		String regex="href=\"([^\"]+)\">下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
}
