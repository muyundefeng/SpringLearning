package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Yxzoo_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * Yxzoo游戏网站
 * http://www.yxzoo.com/game
 * @id 427
 * @author lisheng
 */


public class Yxzoo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Yxzoo.class);

	public Apk process(Page page) {
	
		if("http://www.yxzoo.com/game".equals(page.getUrl().toString()))
		{
			for(int i=1;;i++)
			{
				String jsonurl="http://www.yxzoo.com/plus/game_ajax.php?pt=android&fx=&lx=&q=&order=new&x=&page="+i+"&random=0.779631253099069";
				String html=SinglePageDownloader.getHtml(jsonurl);
				if(html.contains("id"))
				{
					try {
						ObjectMapper objectMapper=new ObjectMapper();
						String html1=html.split(",\"pagination\":\"")[0]+"}";
						Map<String, Object> map=objectMapper.readValue(html1, Map.class);
						List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("list");
						for(Map<String, Object> map1:list)
						{
							String appUrl=map1.get("arcurl").toString();
							page.addTargetRequest(appUrl);
						}
						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				else{
					break;
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.yxzoo\\.com/.*").match()
				&&!page.getUrl().toString().equals("http://www.yxzoo.com/game"))
		{
			System.out.println(page.getUrl());
			
			Apk apk = Yxzoo_Detail.getApkDetail(page);
			
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
