package com.appCrawler.pagePro.fullstack;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Zhuoyi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 卓易市场  http://www.zhuoyi.com/
 * Zhuoyi #311
 * @author tianlei
 */
public class Zhuoyi implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		//System.out.println(page.getHtml().toString());
		if("http://www.zhuoyi.com/".equals(page.getUrl().toString())){
			List<String> list =getNextUrl();
			cacheSet.addAll(list);			
		}
	   for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		//提取页面信息
		if(page.getUrl().regex("http://www.zhuoyi.com/appList.*").match()){	
			List<Apk> apk = Zhuoyi_Detail.getApkDetail(page);			
			page.putField("apks", apk);
			if(page.getResultItems().get("apks") == null){
				page.setSkip(true);
			}
		}else{
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
	
	public List<String> getNextUrl(){
		List<String> list = new ArrayList<String>();
		for (int i =1;i<=100;i++){
			list.add("http://www.zhuoyi.com/appList-3-1-24-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-26-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-29-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-30-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-107-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-109-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-112-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-113-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-114-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-115-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-116-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-3-1-117-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-17-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-18-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-19-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-20-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-22-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-23-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-104-"+i+".html");
			list.add("http://www.zhuoyi.com/appList-4-2-105-"+i+".html");	
		}
		return list;
		}

}

