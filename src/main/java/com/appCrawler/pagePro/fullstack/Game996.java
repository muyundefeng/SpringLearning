package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game996_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 996手机游戏
 * 网站主页：http://www.996.com/
 * @id 423
 * @author lisheng
 */


public class Game996 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.996.com/".equals(page.getUrl().toString()))
		{
			//app详情页的url地址被保存在json数据格式
			for(int i=1;;i++)
			{
				String jsonurl="http://api.996.com/index.php?c=gamelib&m=gamehouse&page="+i+"&type_id=0&medals=0&subType=3&gmold=0&orderby=1&_=1460340143214";
				String html=SinglePageDownloader.getHtml(jsonurl);
				if(html.contains("id"))
				{
					try{
						String json=html.replace("(", "").replace(")", "");
						ObjectMapper objectMapper=new ObjectMapper();
						Map<String, Object> map=objectMapper.readValue(json, Map.class);
						List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("data");
						for(Map<String, Object> map1:list)
						{
							String appurl=map1.get("art_url").toString();
							System.out.println(appurl);
							page.addTargetRequest(appurl);
						}
					}
					catch(Exception e)
					{
						
					}
				}
				else{
					break;
				}
			}
		}
		//提取页面信息
		if(page.getUrl().regex("http://www\\.996\\.com/game/.*").match())
		{
			
			Apk apk = Game996_Detail.getApkDetail(page);
			
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
