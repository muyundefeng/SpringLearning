package com.appCrawler.pagePro.fullstack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.PagePro360_Detail;
import com.appCrawler.pagePro.apkDetails.PageProBaidu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class PagePro360 implements PageProcessor{
	//Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(0);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(PagePro360.class);

	public Apk process(Page page) {
		if("http://zhushou.360.cn/list/index/cid/1".equals(page.getUrl().toString())){
			List<String> categorys = page.getHtml().links("//ul[@class='select']").all();
			//List<String> pages = new ArrayList<String>();
			for(String str:categorys){
				for(int i=1;i<=50;i++){
					page.addTargetRequest(str+"?page="+i);
				}
			}
			//page.addTargetRequests(categorys);
			
		}
		if(page.getUrl().regex("http://zhushou.360.cn/list/index/cid.*").match()){
			List<String> apps = page.getHtml().xpath("//ul[@class='iconList']/li/a[1]/@href").all();
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://zhushou.360.cn/detail/index/soft_id/\\d+").match())
		{
			Apk apk = PagePro360_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
			}
		}else{
			page.setSkip(true);
		}
		return null;
	}

	public static void main(String[] args){
		String url = "http://zhushou.360.cn/list/index/cid/1?page=24#expand";
//		if(url.endsWith("#expand||#next||#prev||#comment||#nogo||#guess-like||#btn-install-now-log")){
		if(url.endsWith("#expand || #next")){
			System.out.println("true");
		}
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
