package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Mofang_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 魔方手游网
 * 编号：405
 * 网站主页：http://www.mofang.com/
 * @author lisheng
 */


public class Mofang implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.mofang.com/".equals(page.getUrl().toString()))
		{
			int count=0;
			try
			{
				String json=SinglePageDownloader.getHtml("http://game.mofang.com/product/game/ajaxlist?tag=0&platform=2&sort=0&keyword=&size=18&page=1");
				ObjectMapper objectMapper1=new ObjectMapper();
				Map<String,Object> map=objectMapper1.readValue(json, Map.class);
				Map<String,Object> map1=(Map<String, Object>)map.get("data");
				String counts=map1.get("count").toString();
				count=Integer.parseInt(counts)/18;
				//List<Map<String,Object>> list=(List<Map<String,Object>>)map1.get("list");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			//System.out.println(page.getHtml());
			//int i=0;
			for(int i=0;i<count+1;i++)
			{
				//i++;
				String json=SinglePageDownloader.getHtml("http://game.mofang.com/product/game/ajaxlist?tag=0&platform=2&sort=0&keyword=&size=18&page="+i);
				ObjectMapper objectMapper=new ObjectMapper();
				try
				{
					Map<String,Object> map=objectMapper.readValue(json, Map.class);
					Map<String,Object> map1=(Map<String, Object>)map.get("data");
					String counts=map1.get("count").toString();
					List<Map<String,Object>> list=(List<Map<String,Object>>)map1.get("list");
					
					for(Map<String, Object> map2:list)
					{
						String appId=map2.get("pg_id").toString();
						//System.out.println(appId);
						//转化成url地址
						page.addTargetRequest("http://game.mofang.com/info/"+appId+".html");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://game\\.mofang\\.com/info/\\d+\\.html").match())
		{
			
			Apk apk = Mofang_Detail.getApkDetail(page);
			
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
