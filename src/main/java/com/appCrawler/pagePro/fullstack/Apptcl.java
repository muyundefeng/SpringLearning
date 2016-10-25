package com.appCrawler.pagePro.fullstack;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Apptcl_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * tcl电视应用市场
 * Aawap #646
 * @author lisheng
 */


public class Apptcl implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apptcl.class);

	public Apk process(Page page) {
	
		if("http://fans.tcl.com/app/".equals(page.getUrl().toString()))
		{
			String string="http://fans.tcl.com/portal.php?mod=download&page=1&catid=18";
			for(int i=1;;i++)
			{
				String url = string.replace("page=1", "page="+i);
				String html = SinglePageDownloader.getHtml(string);
				if(html.contains("aid"))
				{
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map1 = null;
					try {
						map1 = objectMapper.readValue(html,Map.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<Map<String, Object>> list = (List<Map<String,Object>>)map1.get("list");
					for(Map<String, Object> map:list)
					{
						String id = map.get("id").toString();
						String appUrl = "http://fans.tcl.com/forum.php?mod=viewthread&tid="+id;
						page.addTargetRequest(appUrl);
					}
				}
				else
					break;
			}
		
		}

		//提取页面信息
		if(page.getUrl().regex("http://fans.tcl.com/forum.php\\?mod=viewthread&tid=\\d+").match())
		{
			
			Apk apk = Apptcl_Detail.getApkDetail(page);
			
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
