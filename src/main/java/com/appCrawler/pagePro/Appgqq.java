package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Appgqq_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 手游宝
 * 网站主页：http://g.qq.com/allgame/hot.shtml
 * Aawap #671
 * @author lisheng
 */
public class Appgqq implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(flag==1)
		{
			String keyword = page.getUrl().toString().split("keyword=")[1];
			for(int i=1;i<=5;i++)
			{
				String param = "{\"gameData\":{\"module\":\"MiniGame.SearchLogicServer.SearchLogicServantObj\",\"method\":\"searchGame\",\"param\":{\"keyword\":\""+keyword+"\",\"pageNo\":"+i+",\"pageSize\":20,\"searchSource\":1}}}";
				String url = "http://mobfcg.qq.com/fcg-bin/mobile/android/base_common_interface?param="+param;
				String json = SinglePageDownloader.getHtml(url);
				System.out.println(json);
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map = null;
				try {
					map = objectMapper.readValue(json, Map.class);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Map<String, Object> map1 = (Map<String, Object>)map.get("rsp_obj");
				Map<String, Object> map2 = (Map<String, Object>)map1.get("gameData");
				Map<String, Object> map3 = (Map<String, Object>)map2.get("data");
				List<Map<String, Object>> list = (List<Map<String, Object>>)map3.get("docList");
				Iterator<Map<String, Object>> iterator = list.iterator();
				while(iterator.hasNext()){
					Map<String, Object> map4 = iterator.next();
					String gameId = map4.get("gameId").toString();
					String appUrl = "http://g.qq.com/game/"+gameId+"/detail.shtml";
					page.addTargetRequest(appUrl);
				}
	
	
			}
			flag++;
		}
		//提取页面信息
		if(page.getUrl().regex("http://g.qq.com/game/\\d+/detail.shtml").match())
		{
			
				return Appgqq_Detail.getApkDetail(page);
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
