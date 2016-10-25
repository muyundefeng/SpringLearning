package com.appCrawler.pagePro.fullstack;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Ijiatv_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 爱家市场
 * 网站主页：http://app.ijiatv.com/
 * Aawap #643
 * 数据存放在json中，从中提取app url链接
 * @author lisheng
 */


public class Ijiatv implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Ijiatv.class);

	public Apk process(Page page) {
	
		if("http://app.ijiatv.com/".equals(page.getUrl().toString()))
		{
			//List<String> appUrl = new ArrayList<String>();
			String jsonUrl[]={"http://app.ijiatv.com/?act=Index.AppList&appTypeId=64&pageNum=1&pageSize=15",
					"http://app.ijiatv.com/?act=Index.AppList&appTypeId=45&pageNum=1&pageSize=15",
					"http://app.ijiatv.com/?act=Index.AppList&appTypeId=49&pageNum=1&pageSize=15",
					"http://app.ijiatv.com/?act=Index.AppList&appTypeId=57&pageNum=1&pageSize=15"};
			
			for(String str:jsonUrl)
			{
				for(int i=1;;i++)
				{
					String html = SinglePageDownloader.getHtml(str.replace("pageNum=1", "pageNum="+i));
					System.out.println(html);
					if(html.contains("appId"))
					{
						ObjectMapper objectMapper = new ObjectMapper();
						Map<String, Object> map2 = null;
						try {
							map2 = objectMapper.readValue(html, Map.class);
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
						Map<String, Object> map = (Map<String, Object>)map2.get("apkList");
						List<Map<String, Object>> list1 = (List<Map<String, Object>>)map.get("info");
						for(Map<String, Object> map3:list1)
						{
							String url = map3.get("url").toString();
							String appUrl= "http://app.ijiatv.com/"+url;
							System.out.println(appUrl);
							page.addTargetRequest(appUrl);
						}
					}
					else
						break;
				}
			}
		}
	
	
		//提取页面信息
		if(page.getUrl().regex("http://app.ijiatv.com/.+").match())
		{
			
			Apk apk = Ijiatv_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
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
}
