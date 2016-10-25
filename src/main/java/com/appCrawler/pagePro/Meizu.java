package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Anzhi_Detail;
import com.appCrawler.pagePro.apkDetails.Meizu_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 魅族 http://app.meizu.com/
 * Meizu #175
 * 页面的搜索结果是通过异步获取的，
 * 获取的url为:http://app.meizu.com/apps/public/search/page?cat_id=1&keyword=qq&start=0&max=100
 * 搜索结果网站设定只能获取100个
 * 通过这个url获取的json数据提取出搜索结果的packagename字段，
 * 和固定的url组合后获取到正确的搜索结果网址
 * @author DMT
 */
public class Meizu implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Meizu.class);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(5).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		//index page		http://app.meizu.com/apps/public/search?keyword=qq		
		if(page.getUrl().regex("http://app\\.meizu\\.com/apps/public/search\\?keyword.*").match()){
			//应用
			String urlString = "http://app.meizu.com/apps/public/search/page?cat_id=1&keyword="
								+getKeyword(page.getUrl().toString())+"&start=0&max=100";
			System.out.println(urlString);
			//游戏
			String urlString2 = "http://app.meizu.com/apps/public/search/page?cat_id=2&keyword="
					+getKeyword(page.getUrl().toString())+"&start=0&max=100";
			System.out.println(urlString2);
			//获取网址返回的数据，保存在字符串里
			String resultString = SinglePageDownloader.getHtml(urlString);
			String resultString2 = SinglePageDownloader.getHtml(urlString2);
			
			//从字符串里提取出名字										
			List<String> packagename = getPackageNameList(resultString);
			List<String> packagename2 = getPackageNameList(resultString2);
			if(packagename == null){
				System.out.println("cannot get packagename");
				return null;
			}
			if(packagename2 == null){
				System.out.println("cannot get packagename");
				return null;
			}
			
			for (String name : packagename) {
				page.addTargetRequest("http://app.meizu.com/apps/public/detail?package_name="+name);
			}	
			for (String name : packagename2) {
				page.addTargetRequest("http://app.meizu.com/games/public/detail?package_name="+name);
			}	
		}
		
		//the app detail page	
		if(page.getUrl().regex("http://app\\.meizu\\.com/apps/public/detail\\?package_name.*").match()
				|| page.getUrl().regex("http://app\\.meizu\\.com/games/public/detail\\?package_name.*").match()){

			
			return Meizu_Detail.getApkDetail(page);
		}
	
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String getKeyword(String url){
		//http://app\\.meizu\\.com/apps/public/search\\?keyword.*
		if(url == null) return null;
		return url.substring(url.indexOf("keyword")+8,url.length());
	}
	
	
	
	public static List<String> getPackageNameList(String resultString){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			map = objectMapper.readValue(resultString, Map.class);
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
		Map<String, Object> map1 = (Map<String, Object>)map.get("value");
		List<Map<String, Object>> list = (List<Map<String, Object>>)map1.get("list");
		List<String> packageNameList = new ArrayList<String>();
		for(Map<String, Object> map2 :list){
			packageNameList.add(map2.get("package_name").toString());
		}
		return packageNameList;
		
	}
	
	

}
