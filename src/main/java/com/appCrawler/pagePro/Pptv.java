package com.appCrawler.pagePro;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Pptv_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * PPTV  http://game.g.pptv.com/game/
 * Pptv #304
 * @author tianlei
 */
public class Pptv implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	static{
        //System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("http.proxyPort", "8001");
        //System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	@Override
	public Apk process(Page page) {
		
		
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}


	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		String html=SinglePageDownloader.getHtml(page.getUrl().toString());
		String json=html.replace("(", "").replace(")", "").replace("[", "").replace("]","");
		json=json.replace("{\"list\":", "").replace(",\"total\":1,\"pages\":};", "");
		try
		{
			ObjectMapper objectMapper=new ObjectMapper();
			System.out.println(json);
			Map<String,Object> map=objectMapper.readValue(json, Map.class);
			//List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("list");
			//for(Map<String, Object> map1:list)
			//{
				String gameId=map.get("gid").toString();
				page.addTargetRequest("http://g.pptv.com/link/cms?gid="+gameId);
			//}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(page.getUrl().regex("http://g.pptv.com/link/cms.*").match())
		{
			List<Apk> apks=new ArrayList<Apk>();
			//return Pptv_Detail.getApkDetail(page);
			String appName=page.getHtml().xpath("//p[@class='game_name']/text()").toString();
			String appDownloadUrl=page.getHtml().xpath("//div[@class='dowmload_info pr']/a[2]/@href").toString();
			String appVendname=page.getHtml().xpath("//p[@class='develop']/i/text()").toString();
			String appSize=page.getHtml().xpath("//p[@class='size']/i/text()").toString();
			String appUpdateDate=page.getHtml().xpath("//p[@class='update']/i/text()").toString();
			String appVersion=page.getHtml().xpath("//p[@class='versions_num']/i/text()").toString();
			String appDes=page.getHtml().xpath("//div[@class='center_introduce_title pr']").toString();
			appDes=appDes!=null?usefulInfo(appDes):null;
			String appType=null;
			List<String> appPics=page.getHtml().xpath("//div[@class='banner_run_box pa']/ul/li/img/@src").all();
			if(appName != null && appDownloadUrl != null){
				Apk apk = new Apk(appName,page.getUrl().toString(),appDownloadUrl,null ,appVersion,appSize,appUpdateDate,appType,null);
//				Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
//						String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
				apk.setAppDownloadTimes(null);
				apk.setAppVenderName(appVendname);
				apk.setAppTag(null);
				apk.setAppScreenshot(appPics);
				apk.setAppDescription(appDes);
				apk.setAppCategory(null);
				apks.add(apk);			
			}
			return apks;
		}
		return null;
	}
	private static String usefulInfo(String allinfoString)
	{
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	

}
