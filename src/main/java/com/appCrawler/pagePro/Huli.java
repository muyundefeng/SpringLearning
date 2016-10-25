package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Huli_Detail;

import net.sourceforge.htmlunit.corejs.javascript.IdFunctionCall;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 狐狸助手
 * 网站主页：http://app.huli.cn/android/
 * Aawap #542
 * @author lisheng
 */
public class Huli implements PageProcessor{
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
		if(page.getUrl().regex("http://anzhuo\\.adfox\\.cn/index\\.php\\?storeversion=300&a=search_suggest&.*").match())
		{
			List<String> urList=new ArrayList<String>();
			List<Apk> apks=new LinkedList<Apk>();
			String url=page.getUrl().toString();
			for(int i=1;i<=5;i++)
			{
				urList.add(url.replace("page=1", "page="+i));
			}
			for(String url1:urList)
			{
				String json=SinglePageDownloader.getHtml(url1);
				if(json.contains("appname"))
				{
					try{
						ObjectMapper objectMapper=new ObjectMapper();
						Map<String, Object> map2=objectMapper.readValue(json,Map.class);
						List<Map<String, Object>> list=(List<Map<String, Object>>)map2.get("data");
						for(Map<String, Object> map:list)
						{
							String id=map.get("id").toString();
							String downloadurl=map.get("downloadurl").toString();
							String downloadcount=map.get("downloadcount").toString();
							//构造详情页url地址
							String appUrl="http://anzhuo.adfox.cn/index.php?storeversion=300&a=getappinfo&c=index&m=content&id="+id+"&pkgname=com.adfox.store&storeversionname=3.0.0";
							Map<String, Object> map1=objectMapper.readValue(SinglePageDownloader.getHtml(appUrl), Map.class);
							apks.add(Huli_Detail.getApkDetail(downloadurl, downloadcount, map1));
						}
					}
					catch(Exception E){
						E.printStackTrace();
					}
					
				}
				else{
					break;
				}
			}
			return apks;
	}
		return null;
	}
}
