package com.appCrawler.pagePro;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Phone580_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 渠道编号：336
 * 网站主页：http://open.phone580.com/
 * 伪造主页 获取关键字
 *
 */
public class Phone580 implements PageProcessor{
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
		List<Apk> apkList=new ArrayList<Apk>();
		String keyword=page.getUrl().toString().split("name=")[1];
		System.out.println(keyword);
		String url="http://www.phone580.com/fbsapi/api/app/n/hot?callback=jQuery19106748739806935191_1461115506169&clientVersionId=53&channel=0&region=0&offSet=0&pageSize=42&modelId=303&searchName="+keyword+"&notLikeSearch=&_=1461115506170";
		String json1=SinglePageDownloader.getHtml(url);
		json1=(json1.split("list")[1]);//.split("})")[0];
		json1="{\"list"+json1.substring(0, json1.length()-2);
		ObjectMapper objectMapper=new ObjectMapper();
		try{
			Map<String, Object> map=objectMapper.readValue(json1, Map.class);
			List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("list");
			for(Map<String, Object> map1:list)
			{
				String mid=map1.get("typeId").toString();
				String id=map1.get("id").toString();
				//String id=map1.get("id").toString();
				String appurl="http://phone580.com/fbsapi/api/app/n/intro?callback=jQuery19105666205617599189_1461048822549&id="+id+"&modelId="+303+"clientVersionId=53&channel=0&region=0&_=1461048822550";
				String appUrl="http://phone580.com/appinfo.html?id="+id+"&mid="+mid;
				//page.addTargetRequest(appUrl);
				String json2=SinglePageDownloader.getHtml(appurl);
				json2=json2.substring(41, json2.length()-2);
				Map<String, Object> map2=objectMapper.readValue(json2, Map.class);
				apkList.add(Phone580_Detail.getApkDetail(map2,appUrl));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return apkList;
	}

}
