package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.U360_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 360游戏大厅
 * 网站主页：http://ku.u.360.cn/
 * Aawap #514
 * @author lisheng
 */


public class U360 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://ku.u.360.cn/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://ku.u.360.cn/single.php?s=web&tag=%E8%A7%92%E8%89%B2%E6%89%AE%E6%BC%94");
			return null;
		}
	
		if(page.getHtml().links().regex("http://ku\\.u\\.360\\.cn/single\\.php.*").match())
		{
			List<String> categoryList=page.getHtml().links("//div[@class='t_r_d_r']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='good_recom_ul good_recom_ul_fs']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='fs_fenye']").all();
	 		apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&
						!url.contains("http://api.np.mobilem.360.cn")){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://ku\\.u\\.360\\.cn/detail\\.php.*").match())
		{
			
			Apk apk = U360_Detail.getApkDetail(page);
			
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
