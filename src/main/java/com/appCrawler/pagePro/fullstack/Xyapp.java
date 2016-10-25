package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.pagePro.apkDetails.Xyapp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.Json2Object;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * xy手游
 * 本渠道为手机客户端app,返回数据格式为json数据格式
 * 渠道编号:380
 * url地址为:
 * http://apk.interface.xyzs.com/apk/1.7.0/category/gameInfo?code=1001&page=2&page_size=20&deviceimei=a65cb6375b2559ab32ace5c879409e37&clientversion=18
 */
public class Xyapp implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
	if(page.getUrl().toString().equals("http://www.9ht.com/"))
	{
//		page.addTargetRequest("http://apk.interface.xyzs.com/apk/1.7.0/category/gameInfo?code=1001&page=1&page_size=20&deviceimei=a65cb6375b2559ab32ace5c879409e37&clientversion=18");
//		return null;
//	}
		List<Apk> apkList=new ArrayList<Apk>();
		for(int i=1001;i<=1011;i++)
		{
			if(i==1010)
			{
				continue;
			}
			else{
				for(int j=1;j<500;j++)
				{
					String url="http://apk.interface.xyzs.com/apk/1.7.0/category/gameInfo?code="+i+"&page="+j+"&page_size=20&deviceimei=a65cb6375b2559ab32ace5c879409e37&clientversion=18";
					String json=SinglePageDownloader.getHtml(url);
					System.out.println(json);
					if(json.contains("f_packagename"))
					{
						//jsons.add(json);
						String json2[]=json.split("apps");
						//System.out.println(json2[1]);
						String json3[]=json2[1].split("\"page_cnt");
						String string=json3[0].substring(2,json3[0].length()-1);
						List<String> packageNames=Json2Object.ExtraInfo1(string,"f_packagename");
						//构造详情页的url链接
						for(String packageName:packageNames)
						{
							String appDetailUrl="http://apk.interface.xyzs.com/apk/1.7.0/app?packagename="+packageName+"&deviceimei=a65cb6375b2559ab32ace5c879409e37&clientversion=18";
							String json6=SinglePageDownloader.getHtml(appDetailUrl);
							String json4[]=json6.split("apps");
							//System.out.println(json4[1]);
							String json5[]=json4[1].split("gifts");
							//System.out.println(json3[0]);
							String JSON="["+json5[0].substring(2,json5[0].length()-2)+"]";
							apkList.add(Xyapp_Detail.getApkDetail(JSON));
						}
					}
					else{
						break;
					}
				}
			}
		}
		page.putField("apks", apkList);
		if(page.getResultItems().get("apks") == null){
			page.setSkip(true);
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
