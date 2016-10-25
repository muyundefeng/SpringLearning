package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.appCrawler.pagePro.apkDetails.Www5577_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 我机网 http://www.5577.com
 * www5577 #97
 * (1)the app detail page有http://www.5577.com/s/[0-9]* 
 * 						  http://www.5577.com/youxi/[0-9]* 
 * 						  http://www.5577.com/azpj/[0-9]*
 * 	三种 ，其中后两种的页面布局相同
 * 
 * 修改为只从索引页爬取 2015年11月4日17:48:54
 * @author DMT
 */

public class Www5577 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Www5577.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if(page.getUrl().toString().equals("http://www.5577.com")){
			page.addTargetRequest("http://www.5577.com/a/r_1_1.html");//所有的应用索引
		}
		if(page.getUrl().toString().equals("http://www.5577.com/a/r_1_1.html")){
			List<String> url_category = page.getHtml().links("//div[@class='left']").all();//添加所有的游戏分类
			page.addTargetRequests(url_category);
			
		}
		if(page.getUrl().regex("http://www\\.5577\\.com/a/r_1_\\d+\\.html").match()  //应用翻页
				|| page.getUrl().regex("http://www\\.5577\\.com/games/s_11_1.html").match()) {//游戏翻页
			List<String> url_page = page.getHtml().links("//div[@class='tsp_nav']").all();
			
			List<String> url_detail = page.getHtml().links("//div[@class='right']").all() ;	 	
			url_detail.addAll(url_page);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_detail);
			for (String temp : cacheSet) {
				if(!temp.contains("http://www.5577.com/down.asp?id=") 
						&& PageProUrlFilter.isUrlReasonable(temp))				
					page.addTargetRequest(temp);
			}
		}
		
//		
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.5577\\.com/s/[0-9]*\\.html").match() 
				|| page.getUrl().regex("http://www\\.5577\\.com/youxi/[0-9]*\\.html").match()
				|| page.getUrl().regex("http://www\\.5577\\.com/az[a-z]+/[0-9]*\\.html").match()){
	
			
			Apk apk = Www5577_Detail.getApkDetail(page);
			
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
