package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Yueapp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 悦应用:http://www.yueapp.com/index.php/homepage/getsoftorgame/1
 * 渠道编号:326
 * 网页app列表采用JQURY数据返回
 */


public class Yueapp implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		if(page.getUrl().toString().equals("http://www.yueapp.com/index.php/homepage/getsoftorgame/1"))
		{
			List<String> pageList=new ArrayList<String>();//添加页面的url
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=253&type=1&page=300");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=259&type=1&page=60");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=266&type=1&page=100");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=272&type=1&page=400");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=336&type=1&page=200");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=342&type=1&page=300");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=364&type=1&page=150");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=369&type=1&page=200");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=377&type=1&page=100");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=384&type=1&page=20");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=311&type=1&page=300");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=319&type=1&page=30");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=324&type=1&page=70");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=330&type=1&page=74");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=281&type=1&page=400");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=290&type=1&page=400");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=306&type=1&page=180");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=301&type=1&page=300");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=403&type=2&page=10");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=435&type=2&page=10");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=388&type=2&page=500");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=423&type=2&page=320");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=410&type=2&page=300");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=445&type=2&page=100");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=454&type=2&page=100");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=416&type=2&page=200");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=397&type=2&page=80");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=430&type=2&page=25");
			pageList.add("http://www.yueapp.com/index.php/homepage/getappbycategory?cid=438&type=2&page=50");
			page.addTargetRequests(pageList);
			return null;
		}
 		if(page.getUrl().regex("http://www\\.yueapp\\.com/index\\.php/homepage/getappbycategory\\?").match())
 		{
 			List<String> urlList=page.getHtml().xpath("//div[@class='app-lists']/ul/li/div/a/@href").all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.yueapp\\.com/index\\.php/app/detailnew/.*").match())
		{
			
			Apk apk = Yueapp_Detail.getApkDetail(page);
			
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
