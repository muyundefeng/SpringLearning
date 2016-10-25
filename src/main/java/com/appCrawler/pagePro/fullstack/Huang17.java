package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


import org.codehaus.jackson.map.ObjectMapper;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Huang17_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 一起晃手游网  http://www.17huang.com/yx/
 * 渠道编号:366
 * @author DMT
 */


public class Huang17 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
	if(page.getUrl().toString().equals("http://www.17huang.com/yx/"))
	{
		//获得json数据
		String rawStr=SinglePageDownloader.getHtml("http://www.17huang.com/show_game.php?pingtai=2");
		List<String> apkList=new ArrayList<String>();
	    try {
	    	ObjectMapper objectMapper=new ObjectMapper();
	        List<LinkedHashMap<String, Object>> list = objectMapper.readValue(rawStr.substring(1,rawStr.length()-1), List.class);
	        System.out.println(list.size());
	        for (int i = 0; i < list.size(); i++) {
	            Map<String, Object> map = list.get(i);
	            Set<String> set = map.keySet();
	            for (Iterator<String> it = set.iterator();it.hasNext();) {
	                String key = it.next();
	                //System.out.println(key + ":" + map.get(key));
	                if(key.equals("arcurl"))
	                {
	                	apkList.add("http://www.17huang.com"+((String)map.get(key)).replace("\\", ""));
	                }
	            }
	        }
	    }  catch (Exception e) {
	        e.printStackTrace();
	    }
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.17huang\\.com/yx/.*").match()
				&&!page.getUrl().toString().equals("http://www.17huang.com/yx/"))
		{
			
			Apk apk =Huang17_Detail.getApkDetail(page);
			
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
}
