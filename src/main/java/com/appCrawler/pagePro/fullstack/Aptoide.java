package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.StringValueExp;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Aptoide_Detail;

import us.codecraft.webmagic.utils.PostSubmit;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Aptoide
 * 网站主页：https://www.aptoide.com/
 * 需要手机客户端才能可以获得相关信息
 * Aawap #654
 * @author lisheng
 */


public class Aptoide implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Aptoide.class);

	public Apk process(Page page) {
	
		List<Apk> apks = new ArrayList<Apk>();
		if("https://www.aptoide.com/".equals(page.getUrl().toString()))
		{
			try{
				String []jsonUrl = {"http://ws75.aptoide.com/api/7/listApps/store_id/15/group/1/sort/trending30d",
						"http://ws75.aptoide.com/api/7/listApps/group/0%26CN/sort/downloads7d"};
				for(String str:jsonUrl){
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map = objectMapper.readValue(SinglePageDownloader.getHtml(str), Map.class);
					Map<String, Object> map1 = (Map<String, Object>)map.get("datalist");
					int total = Integer.parseInt(map1.get("total").toString());
					int i=0;
					while(i*25<=total){
						String url = str+"?offset="+i*25;
						Map<String, Object> map2 = objectMapper.readValue(SinglePageDownloader.getHtml(url), Map.class);
						Map<String, Object> map3 = (Map<String, Object>)map2.get("datalist");
						List<Map<String, Object>> list = (List<Map<String, Object>>)map3.get("list");
						for(Map<String,Object> map4:list){
							String appId = map4.get("id").toString();
							String pkg = map4.get("package").toString();
							String appJson = PostSubmit.WrappRequest(pkg, appId);
							System.out.println(appJson);
							apks.add(Aptoide_Detail.getApkDetail(appJson));
						}
						i++;
					}
					
				}
			}
			catch(Exception e){e.printStackTrace();}
		}
			
			page.putField("apks", apks);
			if(page.getResultItems().get("apks") == null){
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
