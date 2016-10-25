package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yxjyapp_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 此爬取渠道为手机app应用商店
 * 渠道编号:379
 * 搜索接口:
 * http://yxjyapp.41yx.net:9998/search.do?act=appSearch!%!%!%pageNo=*#*#*#!%!%!%searchKey=*#*#*#!%!%!%loginUserId=44485
 * 然后进行相关主页的替换
 * @author DMT
 */
public class Yxjyapp implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
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
		if(page.getUrl().regex("http://yxjyapp\\.41yx\\.net:9998/search\\.do\\?act=appSearch.*").match()){										
			//获得app的url链接
			String url=page.getUrl().toString();
			String temp[]=url.split("&");
			//进行页面的下载处理
			List<Apk> apkList=new ArrayList<Apk>();
			List<String> jsons=new ArrayList<String>();
			for(int i=1;;i++)
			{
				String str=SinglePageDownloader.getHtml(url.replace(temp[1], "pageNo="+i));
				if(str.contains("content"))
				{
					jsons.add(str);
				}
				else{
					break;
				}
			}
			Map<String, Object> urlMap = null;
			for(String str:jsons)
			{
				//json2Object(str,"id");
				System.out.println(str);
				List<String> appids=(List<String>)json2Object(str);
				//System.out.println(appids);
				//构造详情页url地址
				for(String appId:appids)
				{
					String url1="http://yxjyapp.41yx.net:9998/app.do?act=getDetail&appId="+appId;
					String json=SinglePageDownloader.getHtml(url1);
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
				}
			}
			return apkList;
		}
		return null;
	}
	//JSON转化,获得appid
		private static List<String>  json2Object(String str)
		{
			Map<String, Object> urlMap = null;
			String temp[]=str.split("\"data\":");
			String str1=temp[1]=(temp[1].replace(",\"mark\":1", "")).replace("]}", "]");
			List<String> returnList=new ArrayList<String>();
			 ObjectMapper objectMapper=new ObjectMapper();
			 try
			 {
				List<LinkedHashMap<String, Object>> list = objectMapper.readValue(str1, List.class);
		    	//System.out.println(list);
		    	for(int j=0;j<list.size();j++)
		         {
		        	 Map<String,Object> map=list.get(j);
		        	 System.out.println(map.get("id").toString());
		        	 returnList.add(map.get("id").toString());
		         }
			 }
		    	catch (Exception e) {
					// TODO: handle exception
				}
			 return returnList;
			}
}
