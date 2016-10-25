package com.appCrawler.pagePro;


import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Mofang_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 魔方手游网
 * 编号：405
 * 网站主页：http://www.mofang.com/
 * @author lisheng
 */
public class Mofang implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	if(flag==1)
	{
		String temp[]=page.getUrl().toString().split("&");
		String Page=temp[temp.length-1];
		String extra=page.getUrl().toString().replace(Page,"");
		//System.out.println(extra);
		int count=0;
		try
		{
			String json=SinglePageDownloader.getHtml(page.getUrl().toString());
			ObjectMapper objectMapper1=new ObjectMapper();
			Map<String,Object> map=objectMapper1.readValue(json, Map.class);
			Map<String,Object> map1=(Map<String, Object>)map.get("data");
			String counts=map1.get("count").toString();
			count=Integer.parseInt(counts)/18;
			//List<Map<String,Object>> list=(List<Map<String,Object>>)map1.get("list");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		for(int i=0;i<count+1;i++)
		{
			//i++;
			String json=SinglePageDownloader.getHtml(extra+"page="+i);
			System.out.println(json);
			ObjectMapper objectMapper=new ObjectMapper();

			if(i>5)
			{
				System.out.println("flag");
				break;
			}
			try
			{
				if(json.contains("pg_id"))
				{
					Map<String,Object> map=objectMapper.readValue(json, Map.class);
					Map<String,Object> map1=(Map<String, Object>)map.get("data");
					//String counts=map1.get("count").toString();
					List<Map<String,Object>> list=(List<Map<String,Object>>)map1.get("list");
					
					
					//获取app的id值
					for(Map<String, Object> map2:list)
					{
						String appId=map2.get("pg_id").toString();
						//转化成url地址
						page.addTargetRequest("http://game.mofang.com/info/"+appId+".html");
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		flag++;
	}
			if(page.getUrl().regex("http://game\\.mofang\\.com/info/\\d+\\.html").match())
			{
				return Mofang_Detail.getApkDetail(page);
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
