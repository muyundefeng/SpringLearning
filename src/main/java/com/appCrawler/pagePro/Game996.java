package com.appCrawler.pagePro;


import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Game996_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 996手机游戏
 * 网站主页：http://www.996.com/search/index.html?keyword=qq
 * @id 423
 * @author lisheng
 */

public class Game996 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.996\\.com/search/index\\.html\\?keyword=.*").match())
		{
			//提取关键字
			String keyWord=page.getUrl().toString().split("=")[1];
			for(int i=1;;i++)
			{
				String jsonurl="http://api.996.com/index.php?c=search&m=engine&keyword="+keyWord+"&page="+i+"&type=1&_=1460341362006";
				String html=SinglePageDownloader.getHtml(jsonurl);
				if(html.contains("id"))
				{
					try{
						String json=html.replace("(", "").replace(")", "");
						ObjectMapper objectMapper=new ObjectMapper();
						Map<String, Object> map=objectMapper.readValue(json, Map.class);
						Map<String, Object> map2=(Map<String, Object>)map.get("game");
						List<Map<String, Object>> list=(List<Map<String, Object>>)map2.get("data");
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
		if(page.getUrl().regex("http://www\\.996\\.com/game/.*").match())
			{
				return Game996_Detail.getApkDetail(page);
			}
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
