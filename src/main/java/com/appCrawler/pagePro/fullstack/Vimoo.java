package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import com.appCrawler.pagePro.apkDetails.Vimoo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #178 vimoo
 * 
 * http://www.vimoo.cn/lists/l22_1/
 * @author DMT
 *
 */
public class Vimoo implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Vimoo.class);

	public Apk process(Page page) {
	
		if(page.getUrl().toString().equals("http://www.vimoo.cn/lists/l22_1/index.html")
				|| 
				page.getUrl().toString().equals("http://www.vimoo.cn/lists/l23_1/index.html")){
			page.addTargetRequest("http://www.vimoo.cn/lists/l23_1/index.html");
			List<String> urlList = page.getHtml()
					.links("//div[@class='picturelistbox']")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pagenav']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}
		}
		
		//System.out.println(page.getHtml().toString());
		List<String> urls =page.getHtml().links().regex("http://www\\.vimoo\\.cn/.*").all() ;
		
 		
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urls);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
		
		
		//提取页面信息http://www.vimoo.cn/contents/c35/index.html
		if(page.getUrl().regex("http://www\\.vimoo\\.cn/contents/.*").match()){
			
			Apk apk = Vimoo_Detail.getApkDetail(page);
			
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
