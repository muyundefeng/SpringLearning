package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 





import com.appCrawler.pagePro.apkDetails.Www9game_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 九游  http://www.9game.cn/
 * Www9game #174
 * 
 * 索引页面里的每一页有两部分内容，一部分是通过当前翻页的url加载的，另一部分是通过动态加载的
 * @author DMT 
 */


public class Www9game implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Www9game.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.9game.cn/")) {
			page.addTargetRequest("http://www.9game.cn/danji/2_0_0_0_1/");// 添加单击游戏索引
			page.addTargetRequest("http://www.9game.cn/category/2_0_0_0_0_1/");// 添加网游索引

		}
		if (page.getUrl().toString().equals("http://www.9game.cn/danji/2_0_0_0_1/") 
				|| page.getUrl().toString().equals("http://www.9game.cn/category/2_0_0_0_0_1/")) {
			List<String> categoryList = page.getHtml().links("//div[@class='classify last']//div[@class='type']").all();
			page.addTargetRequests(categoryList);
		}
//http://partner.eoemarket.com/qq/categories/index/category_id/1?&pageNum=4
		if (page.getUrl().regex("http://www\\.9game\\.cn/danji/[_0-9]+/?")
				.match()
				|| page.getUrl()
						.regex("http://www\\.9game\\.cn/category/[_0-9]+/?")
						.match()) {// 获取所有分类页	
			List<String> urlList = page.getHtml()
					.links("//ul[@class='game-poker']")
					.regex("http://www\\.9game\\.cn/.+/")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='page-change']").all();
			

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			cacheSet.addAll(getDynamicUrlDetailList(page.getUrl().toString(), 60));
			System.out.println("size:"+cacheSet.size());
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url) ) {
					page.addTargetRequest(url);
				}
			}

		}

		
	

		if(page.getUrl().regex("http://www\\.9game\\.cn/.+/").match()){			

			Apk apk = Www9game_Detail.getApkDetail(page);
			
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

	/*下面这个地址可以得到页面动态加载的应用信息
	 * 单击http://www.9game.cn/tpl/pc2/common/category_danji_ajax.html?beginIndex=161&pcount=20&categoryid=0&keyword=0&orderby=0&p_id=2&statsPage=danji_category
	 * 网游http://www.9game.cn/tpl/pc2/common/category_ajax.html?beginIndex=141&pcount=20&categoryid=0&keyword=0&optstatus_id=0&orderby=0&p_id=2&statsPage=ng_category
	 * 其中beginIndex=141表示从第141个开始获取(每一页最多显示60个，当前是第3页，页面直接加载了20，因此从第60*(3-1)+20+1=141开始)
	 * pcount=20表示这个url请求获取20个，页面是分两次加载的，这个参数可以修改为40，一次全部获取
	 * 
	 * url:翻页的url
	 * http://www.9game.cn/danji/2_0_0_0_1/
	 * http://www.9game.cn/category/2_0_0_0_0_1/
	 * 
	 * pageCapacity：表示一个目录页的最多apk数
	 * 
	 * */
	private static List<String> getDynamicUrlDetailList(String url,int pageCapacity){
		String urlpre = "";
		String urlsuffix = "";
		if(url.contains("/danji/")){//单击
			urlpre= "http://www.9game.cn/tpl/pc2/common/category_danji_ajax.html?";
			urlsuffix = "categoryid=0&keyword=0&orderby=0&p_id=2&statsPage=danji_category";
		}
		else if(url.contains("/category/")){//网游
			urlpre = "http://www.9game.cn/tpl/pc2/common/category_ajax.html?";
			urlsuffix = "categoryid=0&keyword=0&optstatus_id=0&orderby=0&p_id=2&statsPage=ng_category";
		}
		System.out.println("getting url = "+url);
		int pageNum = 0;
		if(!url.endsWith("/"))
			pageNum = Integer.parseInt(StringUtils.substringAfterLast(url, "_"));
		else pageNum = Integer.parseInt(url.substring(url.lastIndexOf("_")+1, url.length()-1));
		System.out.println("getting pageNum = "+pageNum);
		int beginIndex = (pageNum-1)*pageCapacity + 20 +1;
		String requestUrl = urlpre+"beginIndex="+beginIndex+"&pcount="+20+"&"+urlsuffix;
		System.out.println("getting "+requestUrl);
		Html html = Html.create(SinglePageDownloader.getHtml(requestUrl));
		return html.links().regex("http://www\\.9game\\.cn/.+/").all();	
	}
	
	
	public static void main(String[] args){
		System.out.println(getDynamicUrlDetailList("http://www.9game.cn/danji/2_0_0_0_1/", 60));
	}
}
