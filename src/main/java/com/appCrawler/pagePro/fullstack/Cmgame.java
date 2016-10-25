package com.appCrawler.pagePro.fullstack;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Cmgame_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *渠道编号：333
 *网站主页：http://oss.cmgame.com/android/?spm=www.pdindex.android.dh.2
 * @author DMT
 */
public class Cmgame implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Cmgame.class);

	public Apk process(Page page) {
		if(page.getUrl().toString().equals("http://oss.cmgame.com/android/?spm=www.pdindex.android.dh.2"))
		{
			//将相关的url地址添加到page中
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=17774&orderby=newstime&cur=wczq");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dPrice=0&orderby=newstime&cur=dfmf");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=10&myorder=&orderby=newstime");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=1&orderby=newstime&cur=dType1");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=8&myorder=&orderby=newstime");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=7&myorder=&orderby=newstime");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=4&myorder=&orderby=newstime");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=3&myorder=&orderby=newstime");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=6,7&orderby=newstime&cur=tyjjgm");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10392&dType=2&myorder=&orderby=newstime");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=10582&orderby=newstime&cur=dfwygm");
			page.addTargetRequest("http://oss.cmgame.com/game/list/?line=36&classid=16819&orderby=newstime&cur=wimogm");
			return null;
		}
		
		if(page.getUrl().regex("http://oss\\.cmgame\\.com/game/list/.*").match())
		{
			List<String> apkList=page.getHtml().xpath("//div[@class='gm_list']/ul/li/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@class='pageprne mgrT20']/span/a/@href").all();
			page.addTargetRequests(apkList);
			page.addTargetRequests(pageList);
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://oss\\.cmgame\\.com/game/\\d*\\?spm=www\\.gamelist\\.getclassid\\.azjxyx.*").match()){
			
			Apk apk = Cmgame_Detail.getApkDetail(page);
			
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
