package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Phone580_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 渠道编号：336
 * 网站主页：http://open.phone580.com/
 *@author lisheng
 */


public class Phone580 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getUrl().toString().equals("http://phone580.com/"))
	{
		String url[]={"http://www.phone580.com/fbsapi/api/app/n/hot?callback=jQuery19109997581182979047_1461049854316&modelId=303&isInterfere=true&board=default&pageSize=36&appTemplateType=2&offSet=72&channel=0&region=0&clientVersionId=53&_=1461049854339",
				"http://phone580.com/fbsapi/api/app/n/hot?callback=jQuery191021498932945542037_1461038801346&modelId=303&isInterfere=true&board=default&pageSize=36&appTemplateType=9&offSet=72&channel=0&region=0&clientVersionId=53&_=1461038801652"};
			//List<String> list=ReadFileUtil.readTxtFile();
		for(String str:url)
		{
			List<Apk> apkList=new ArrayList<Apk>();
			String json=SinglePageDownloader.getHtml(str);
			System.out.println(json);
			json=(json.split("\"userId\":-1,")[1]);//.split("})")[0];
			json="{"+json.substring(0, json.length()-2);
			//json="{"+json+"}";
			System.out.println(json);
			int count=0;
			try
			{
				ObjectMapper objectMapper=new ObjectMapper();
				Map<String, Object> map=objectMapper.readValue(json, Map.class);
				count=Integer.parseInt(map.get("dataSize").toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			for(int i=0;i*72<=count;i++ )
			{
				String json1=SinglePageDownloader.getHtml("http://phone580.com/fbsapi/api/app/n/hot?callback=jQuery191021498932945542037_1461038801346&modelId=303&isInterfere=true&board=default&pageSize=36&appTemplateType=9&offSet="+i*72+"&channel=0&region=0&clientVersionId=53&_=1461038801652");
				json1=(json1.split("\"userId\":-1,")[1]);//.split("})")[0];
				json1="{"+json1.substring(0, json1.length()-2);
				ObjectMapper objectMapper=new ObjectMapper();
				try{
					Map<String, Object> map=objectMapper.readValue(json1, Map.class);
					List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("list");
					for(Map<String, Object> map1:list)
					{
						String mid=map1.get("typeId").toString();
						String id=map1.get("id").toString();
						//String id=map1.get("id").toString();
						String appurl="http://phone580.com/fbsapi/api/app/n/intro?callback=jQuery19105666205617599189_1461048822549&id="+id+"&modelId="+mid+"clientVersionId=53&channel=0&region=0&_=1461048822550";
						String appUrl="http://phone580.com/appinfo.html?id="+id+"&mid="+mid;
						//page.addTargetRequest(appUrl);
						String json2=SinglePageDownloader.getHtml(appurl);
						//json2=(json2.split("{\"dlTime\":")[1]);
						json2=json2.substring(41, json2.length()-2);
						System.out.println("jsonUrl="+appurl);
						System.out.println("json2="+json2);
						Map<String, Object> map2=objectMapper.readValue(json2, Map.class);
						apkList.add(Phone580_Detail.getApkDetail(map2,appUrl));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
				
				page.putField("apks", apkList);
				if(page.getResultItems().get("apks") == null){
					page.setSkip(true);
					}
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
