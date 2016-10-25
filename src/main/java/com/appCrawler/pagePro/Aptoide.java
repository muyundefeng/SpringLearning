package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Aptoide_Detail;

import us.codecraft.webmagic.utils.PostSubmit;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * Aptoide
 * 网站主页：https://www.aptoide.com/
 * 需要手机客户端才能可以获得相关信息
 * Aawap #654
 * @author lisheng
 */
public class Aptoide implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
		
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		List<Apk> apks = new ArrayList<Apk>();
		if(page.getUrl().regex("http://www.aptoide.com/search/view.*").match())
		{
			String keyWord = page.getUrl().toString().split("search=")[1];
			//构造真正的url连接
			 String searchParameter = "search="+keyWord+"&q=bwF4u2RrPTE5Jm1heFNjcmv1bj1ub3JtYWwmbwF4R2x1cz0yLjAmbx1DUFU8YXJtZWFiaS12N2EsYXJtZWFiaSZteUR1bnNpdHk9MZIWJm15QXBOPTQ4MA&options=%28limit%3d7%3Brepos%3Dapps%3Bmature%3Dtrue%3B%29&mode=json";
			 String url = "http://webservices.aptoide.com/webservices/3/listSearchApks";
			 String json = PostSubmit.postGetData(url, searchParameter);
			System.out.println(json);
			 ObjectMapper objectMapper = new ObjectMapper();
			 Map<String, Object> map=null;
			try {
				map = objectMapper.readValue(json, Map.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 Map<String, Object> map4 = (Map<String, Object>)map.get("results");
			 List<Map<String, Object>> list = (List<Map<String, Object>>)map4.get("apks");
			 for(Map<String, Object> map1:list){
				 String md5sum = map1.get("md5sum").toString();
				 String jsonapp = PostSubmit.SearchWrappRequest(md5sum);
				 apks.add(Aptoide_Detail.getApkDetail(jsonapp));
			 }
			
		}
	
		return apks;
	}

}
