package com.appCrawler.pagePro.fullstack;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.appCrawler.pagePro.apkDetails.Pptv_Detail;
import com.appCrawler.utils.PropertiesUtil;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * PPTV  http://game.g.pptv.com/game/
 * Pptv #304
 * @author tianlei
 */


public class Pptv implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {

		if("http://game.g.pptv.com/game/".equals(page.getUrl().toString())){
			 page.addTargetRequest("http://static.g.pptv.com/game/res/js/game/mobpiclist.js?2015090716");
		}
		
		//提取页面信息
		if(page.getUrl().regex("http://static.g.pptv.*").match()){	
		    List<Apk> apkList = Pptv_Detail.getApkDetail(page);	
		    System.out.println(apkList);
		    page.putField("apks",apkList );			
			if(page.getResultItems().get("apks") == null){
				page.setSkip(true);
				}
		}else{
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
