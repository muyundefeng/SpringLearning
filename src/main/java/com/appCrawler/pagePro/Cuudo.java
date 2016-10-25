package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Cuudo_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 酷嘟网 http://game.cuudoo.com/games.html
 * 渠道编号:335
 * @author DMT
 * http://game.cuudoo.com/select-1.html?keyword=*#*#*#
 * 该渠道的搜索链接url地址存在两种形式，下面进行相关的统一
 */
public class Cuudo implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://game\\.cuudoo\\.com/select-.*\\.html\\?keyword=.*").match()||page.getUrl().regex("http://game\\.cuudoo\\.com/selectall.*").match()){
			//获得搜索关键字
			String URL=page.getUrl().toString();
			int index=URL.indexOf('=');
			String keyWord=URL.substring(index+1);
			System.out.println(keyWord);
			//构造应用类别的搜索url地址
			page.addTargetRequest("http://game.cuudoo.com/select-2.html?keyword="+keyWord);
			List<String> apkList=page.getHtml().xpath("//div[@class='cover-list33']/ul/li/div[2]/table/tbody/tr/td/a/@href").all();
			List<String> apkList1=page.getHtml().xpath("//div[@class='cover-list33']/ul/li/div[2]/a/table/tbody/tr/td/a/@href").all();
			apkList=apkList.size()==0?apkList1:apkList;
			List<String> pageList=page.getHtml().xpath("//div[@class='page']/dt/a/@href").all();
			System.out.println(apkList);
			System.out.println(pageList);
			page.addTargetRequests(apkList);
			page.addTargetRequests(pageList);
		}
		//提取页面信息
		if(page.getUrl().regex("http://game\\.cuudoo\\.com/games_info-\\d*\\.html").match()||page.getUrl().regex("http://game\\.cuudoo\\.com/app_info-\\d*\\.html").match())
			{
					
			return Cuudo_Detail.getApkDetail(page);
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
