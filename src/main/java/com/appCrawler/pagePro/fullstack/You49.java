package com.appCrawler.pagePro.fullstack;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.You49_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 49游 http://m.49you.com/daquan/
 * 渠道编号：324
 * @author DMT
 * 所需要的详细信息在保存在json格式的文件。
 */


public class You49 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	List<Apk> apks=new ArrayList<Apk>();
		if(page.getUrl().toString().equals("http://m.49you.com/daquan/"))
		{
		List<String> urlList=new ArrayList<String>();
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18309413405754603446_1453714202622&siteflag=&p=1&gamesign=1&device=3&cid=139&_=1453715515753");
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18309413405754603446_1453714202622&siteflag=&p=2&gamesign=1&device=3&cid=139&_=1453715515753");
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18309413405754603446_1453714202622&siteflag=&p=1&gamesign=1&device=3&cid=138&_=1453715444128");
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18309413405754603446_1453714202622&siteflag=&p=2&gamesign=1&device=3&cid=138&_=1453715444128");
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18309413405754603446_1453714202622&siteflag=&p=3&gamesign=1&device=3&cid=138&_=1453715444128");
		for(int i=1;i<=10;i++)
		{
			urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18306249950856436044_1453720131024&siteflag=&p="+i+"&gamesign=1&device=3&cid=136&_=1453720706723");
		}
		for(int i=1;i<=5;i++)
		{
			urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18306249950856436044_1453720131024&siteflag=&p="+i+"&gamesign=1&device=3&cid=137&_=1453720785715");
		}
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18306249950856436044_1453720131024&siteflag=&p=1&gamesign=1&device=3&cid=140&_=1453720844619");
		Map<String, Object> urlMap = null;
		for(String str:urlList)
		{
			//下载页面
			String rawStr=SinglePageDownloader.getHtml(str);
			List<LinkedHashMap<String, Object>> list1=null;
			try {
				//System.out.println(rawStr);
				int start=rawStr.indexOf("game_id");
				System.out.println(start);
				System.out.println(rawStr.substring(start,rawStr.length()-4));
				rawStr="[{\""+rawStr.substring(start,rawStr.length()-4);
				rawStr=rawStr.replaceAll("\\],\\[",",");
				//rawStr="{\"total\":68482,\"result\":{\"code\":0,\"msg\":\"OK!\"},\"data\":"+rawStr+"}";
				ObjectMapper objectMapper=new ObjectMapper();
				System.out.println(rawStr);
				list1 = objectMapper.readValue(rawStr, List.class);
				//rawStr=rawStr.replaceAll("[]", "\"\"");
				//urlMap = new ObjectMapper().readValue(rawStr, Map.class);
				System.out.println(list1.size());
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
			if(list1!=null)
			{
				for (int j = 0; j < list1.size(); j++) {
					Map<String, Object> map = list1.get(j);
					apks.add(You49_Detail.getApkDetail(map));
				}
			}
		}
	page.putField("apks", apks);
	if(page.getResultItems().get("apks") == null){
		page.setSkip(true);
	}
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
