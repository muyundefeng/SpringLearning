package com.appCrawler.pagePro.fullstack;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.stylesheets.LinkStyle;

import com.appCrawler.pagePro.apkDetails.Huli_Detail;
import com.appCrawler.utils.PropertiesUtil;


import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 狐狸助手
 * 网站主页：http://app.huli.cn/android/
 * Aawap #542
 * @author lisheng
 */


public class Huli implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://app.huli.cn/android/".equals(page.getUrl().toString()))
		{
			//app列表catID的值
			String[] string={"14","15","41","147","135","63","48","16","57",
					"68","13","73","53","129","183","27","28","31","30","169","176","105","82"};
			//构造app列表url地址
			List<Apk> apks=new LinkedList<Apk>();
			for(String str:string)
			{
				for(int i=1;;i++)
				{
					String appListUrl="http://anzhuo.adfox.cn/index.php?storeversion=300&a=getapplistbycat&c=index&catid="+str+"&m=content&page="+i+"&pkgname=com.adfox.store&storeversionname=3.0.0";
					String html=SinglePageDownloader.getHtml(appListUrl);
					System.out.println(html);
					if(html.contains("catname"))
					{
						try{
							ObjectMapper objectMapper=new ObjectMapper();
							Map<String, Object> map2=objectMapper.readValue(html,Map.class);
							List<Map<String, Object>> list=(List<Map<String, Object>>)map2.get("data");
							for(Map<String, Object> map:list)
							{
								String id=map.get("id").toString();
								String downloadurl=map.get("downloadurl").toString();
								String downloadcount=map.get("downloadcount").toString();
								//构造详情页url地址
								String appUrl="http://anzhuo.adfox.cn/index.php?storeversion=300&a=getappinfo&c=index&m=content&id="+id+"&pkgname=com.adfox.store&storeversionname=3.0.0";
								Map<String, Object> map1=objectMapper.readValue(SinglePageDownloader.getHtml(appUrl), Map.class);
								apks.add(Huli_Detail.getApkDetail(downloadurl, downloadcount, map1));
								
							}
						}
						catch(Exception E){
							E.printStackTrace();
						}
						
					}
					else{
						break;
					}
				}
			}
			page.putField("apks", apks);
			if(page.getResultItems().get("apks") == null){
				page.setSkip(true);
				}
		}
		//提取页面信息
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
