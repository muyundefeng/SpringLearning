package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import com.appCrawler.pagePro.apkDetails.Yxjyapp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 此爬取渠道为手机app应用商店
 * 渠道编号:379
 * 本渠道伪造主页:http://www.9ht.com/
 * 然后进行相关主页的替换
 * @author DMT
 */


public class Yxjyapp implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if("http://www.9ht.com/".equals(page.getUrl().toString()))
	{
		//进行主页的替换,替换成真正的主页url地址
		page.addTargetRequest("http://yxjyapp.41yx.net:9998/app.do?act=getEvaluateTop&pageNo=1&loginUserId=44485");
		return null;
	}
	List<Apk> apkList=new ArrayList<Apk>();
	List<String> jsons=new ArrayList<String>();
	if("http://yxjyapp.41yx.net:9998/app.do?act=getEvaluateTop&pageNo=1&loginUserId=44485".equals(page.getUrl().toString()))
	{
		//进行页面的下载处理
		for(int i=1;;i++)
		{
			String str=SinglePageDownloader.getHtml("http://yxjyapp.41yx.net:9998/app.do?act=getEvaluateTop&pageNo="+i+"&loginUserId=44485");
			if(str.contains("content"))
			{
				jsons.add(str);
			}
			else{
				break;
			}
		}
	}
		Map<String, Object> urlMap = null;
		for(String str:jsons)
		{
			//json2Object(str,"id");
			List<String> appids=(List<String>)json2Object(str);
			//System.out.println(appids);
			//构造详情页url地址
			for(String appId:appids)
			{
				String url="http://yxjyapp.41yx.net:9998/app.do?act=getDetail&appId="+appId;
				String json=SinglePageDownloader.getHtml(url);
				System.out.println(json);
				json=json.replace("\"data\":\"{", "\"data\":\"[{");
				json=json.replace("}\",\"mark\":", "}]\",\"mark\":");
				System.out.println(json);
				try {
			        ObjectMapper objectMapper=new ObjectMapper();
			        urlMap = new ObjectMapper().readValue(json, Map.class);
			         if (null != urlMap) { 
			        	List<LinkedHashMap<String, Object>> list = objectMapper.readValue(urlMap.get("data").toString(), List.class);
			        	//System.out.println(list);
			        	for(int j=0;j<list.size();j++)
			             {
			            	 Map<String,Object> map=list.get(j);
			            	 //System.out.println(map.get("img"));
			            	 apkList.add(Yxjyapp_Detail.getApkDetail(map));
			             }
			         }
			    }  catch (Exception e) {
			        e.printStackTrace();
			    }
				
				
				
				
				//System.out.println(json2Object(json)+"*******");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//apkList.add(Yxjyapp_Detail.getApkDetail((Map)json2Object(json, "")));
			}
		}
		page.putField("apks", apkList);
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
	//JSON转化,获得appid
	private static List<String>  json2Object(String str)
	{
		Map<String, Object> urlMap = null;
		System.out.println(str);
		List<String> appids=new ArrayList<String>();
			try {
		        ObjectMapper objectMapper=new ObjectMapper();
		        urlMap = new ObjectMapper().readValue(str, Map.class);
		         if (null != urlMap) { 
		        	List<LinkedHashMap<String, Object>> list = objectMapper.readValue(urlMap.get("data").toString(), List.class);
		        	//System.out.println(list);
		        	for(int j=0;j<list.size();j++)
		             {
		            	 String id=list.get(j).get("id").toString();
		            	 appids.add(id);
		             }
		         }
		    }  catch (Exception e) {
		        e.printStackTrace();
		    }
			return appids;
		}

}
