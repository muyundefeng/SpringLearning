package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.pagePro.apkDetails.Byfen_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #169
 * 手游世界http://www.shouyou520.com/
 * 可以通过伪造下载链接来构造下载链接
 * http://android.173sy.com/download/downloadapk.php?id=13425&s=1
 * 将id后的参数修改成相应的apk的id就好
 * @author Administrator
 *
 */
public class Byfen implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		//if(page.getUrl().regex("http://android\\.173sy\\.com/games/search\\.php\\?key=*").match()){
		if(page.getUrl().regex("http://android\\.byfen\\.com/search\\.html\\?keywords=.*").match()){
			//app的具体介绍页面											
			List<String> detailUrl = page.getHtml().xpath("//ul[@class='mask-124']/li/dl/dt/a/@href").all();
			System.out.println(detailUrl);
//			/List<String> pageUrl = page.getHtml().links("//div[@class='pages']").all();
			
			//添加下一页url(翻页)
			List<String> pageUrl = page.getHtml().xpath("//li[@class='next_page']/a/@href").all();
			
			detailUrl.addAll(pageUrl);
			System.out.println("detailUrl size="+detailUrl.size());
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(detailUrl);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		if(page.getUrl().regex("http://android\\.byfen\\.com/app.*").match())
			//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
			{
				
				Apk apk = Byfen_Detail.getApkDetail(page);
				
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

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
