package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Danji99_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.Json2Object;
/**
 * 玉米助手手机app
 * 渠道编号:381
 * 伪造主页:http://www.99danji.com/az/5090/
 */


public class Danji99 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	if(page.getUrl().toString().equals("http://www.99danji.com/az/5090/"))
	{
		page.addTargetRequest("http://android.ymzs.com/category/HomePageV4?type=1");
		return null;
	}
		List<Apk> apkList=new ArrayList<Apk>();
		for(int i=1;i<=2;i++)
		{
			String json=SinglePageDownloader.getHtml("http://android.ymzs.com/category/HomePageV4?type="+i);
			String temp[]=json.split("\"typeClass\":");
			String tString=temp[1].substring(0,temp[1].length()-2);
			List<String> appIds=Json2Object.ExtraInfo1(tString,"id");
			System.out.println(appIds);
			//构造apk列表url地址
			for(String appId:appIds)
			{
				for(int j=1;;j++)
				{
					String url="http://android.ymzs.com/category/DetailList?id="+appId+"&page="+j;
					//String url="http://android.ymzs.com/category/DetailList?id=10000002&page="+j;
					System.out.println("pageNO"+"*****"+j);
					//if()
					String json1=SinglePageDownloader.getHtml(url);
					if(json1.contains("pkgName"))
					{
						String temp1[]=json1.split("\"list\":");
						String str=temp1[1].substring(0,temp1[1].length()-2);
						System.out.println(str);
						List<String> packageNames=Json2Object.ExtraInfo1(str,"pkgName");
						for(String packageName:packageNames)
						{
							//构造app详情url地址
							if(packageName.contains("com"))
							{
								String json2=SinglePageDownloader.getHtml("http://android.ymzs.com/detail/"+packageName+"?userId=&imei=866329024603181");
								String temp2[]=json2.split("\"Data\":");
								String str2="["+temp2[1].substring(0,temp2[1].length()-1)+"]";
								//List<String> packageNames=Json2Object.ExtraInfo1(str2,"iconUrl");
								System.out.println(packageNames);
								apkList.add(Danji99_Detail.getApkDetail(str2));
							}
						}
					}
					else{
						System.out.println("hello*****");
						break;
					}
				}
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
}
