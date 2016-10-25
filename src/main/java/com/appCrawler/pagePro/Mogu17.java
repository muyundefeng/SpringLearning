package com.appCrawler.pagePro;


import java.net.URLEncoder;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Mogu17_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 蘑菇游戏
 * 网站主页：http://www.17mogu.com/youxi/
 *搜索接口有经过两次编码，伪造搜索接口，获取关键字
 * Aawap #410
 * @author lisheng
 */
public class Mogu17 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.xiaopi\\.com/search/.*").match())
		{
			//获取关键字
			String keyword=(page.getUrl().toString().split("=")[1]).split("&")[0];
			String url1="http://www.17mogu.com/youxi/?page=1&type=&name=";
			StringBuffer sb = new StringBuffer();  
	        sb.append(keyword);  
	        String str = "";  
	        String urlUTF8="";  
	        try {  
	        str = new String(sb.toString().getBytes("UTF-8"));  
	        urlUTF8 = URLEncoder.encode(str, "UTF-8");  
	        page.addTargetRequest(url1+urlUTF8);  
	        } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	        }
	        return null;
		}
		if(page.getUrl().regex("http://www\\.17mogu\\.com/youxi/\\?page.*").match())
		{
			//url编码
			List<String> apps=page.getHtml().xpath("//div[@class='task']/@onclick").all();
			for(String str:apps)
			{
				String url=str.replace("window.open('", "").replace("', '_blank')", "");
				System.out.println(url);
				page.addTargetRequest(url);
			}
		}
			if(page.getUrl().regex("http://www\\.17mogu\\.com/games/game_details_\\d+\\.html").match())
			{
				return Mogu17_Detail.getApkDetail(page);
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
