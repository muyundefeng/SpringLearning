package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.TuStoreApp_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 兔商店  
 * 伪造主页:http://app.woniu.com/snailstore/
 * 进行相关主页的替换
 * 渠道编号:382
 */


public class TuStoreApp implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if("http://app.woniu.com/snailstore/".equals(page.getUrl().toString()))
	{
		List<Apk> apkList=new ArrayList<Apk>();
		for(int i=1;i<=8;i++)
		{
			for(int j=1;;j++)
			{
				String url="http://api.app1.snail.com/store/platform/app/category/list?iCategoryId="+i+"&iPlatformId=36&currentPage="+j;
				//下载页面,页面不是json格式的信息,通过字符串进行处理
				String info=SinglePageDownloader.getHtml(url);
				if(info.contains("sAppName"))
				{
					System.out.println(info);
					String appRawInfos[]=info.split("\"]");
					for(int a=0;a<appRawInfos.length-1;a++)
					{
						apkList.add(TuStoreApp_Detail.getApkDetail(appRawInfos[a]));
					}
				}
				else {
						break;
					}
			}
		}
		for(int i=51;i<=59;i++)
		{
			for(int j=1;;j++)
			{
				String url="http://api.app1.snail.com/store/platform/app/category/list?iCategoryId="+i+"&iPlatformId=36&currentPage="+j;
				//下载页面,页面不是json格式的信息,通过字符串进行处理
				String info=SinglePageDownloader.getHtml(url);
				if(info.contains("sAppName"))
				{
					System.out.println(info);
					String appRawInfos[]=info.split("\"]");
					for(int a=0;a<appRawInfos.length-1;a++)
					{
						apkList.add(TuStoreApp_Detail.getApkDetail(appRawInfos[a]));
					}
				}
				else {
						break;
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
