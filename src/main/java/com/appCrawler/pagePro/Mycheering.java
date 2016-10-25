package com.appCrawler.pagePro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Mycheering_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader2;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 青橙游戏助手 网站搜索接口:http://api.apps.mycheering.com/api.aspx?m=k&v=%E9%9D%92%E6%9F%A0%E6%B5%8F%E8%A7%88%E5%99%A8&pi=1&ps=20
 * 渠道编号:365 
 * 相关的app信息保存在json格式的文件之中
 * 
 */
public class Mycheering implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		return null;
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		if(page.getUrl().regex("http://apps\\.mycheering\\.com/WebPage/search_result\\.html\\?sword=.*").match()){
			//提取搜索关键字
			String keyWord=page.getUrl().toString();
			int index=keyWord.indexOf('=');
			String key=page.getUrl().toString().substring(index+1);
			//构造json url地址
			String url="http://api.apps.mycheering.com/api.aspx?m=k&v="+key+"&pi=1&ps=20";
			List<String> apkList=new ArrayList<String>();
			for(int i=1;i<=13;i++)
			{
				apkList.add("http://api.apps.mycheering.com/api.aspx?m=k&v="+key+"&pi="+i+"&ps=20");
			}
			List<Apk> apkList1 = new ArrayList<Apk>();
			List<String> apks = new ArrayList<String>();
			for (String url1 : apkList) {
				String appDetail = SinglePageDownloader.getHtml(url1);
				List<Integer> appIds = ExtraJson(appDetail, "a7");
				for (Integer str : appIds) {
					apks.add("http://api.apps.mycheering.com/api.aspx?iszip=1&m=s&id=" + str.intValue());
				}
			}
			for (int i = 0; i < apks.size(); i++) {
				try {
					Map<String, Object> urlMap = null;
					urlMap = new ObjectMapper().readValue(SinglePageDownloader2.getHtml(apks.get(i)), Map.class);
					if (null != urlMap) {
						List<Map<String, Object>> infos = (List<Map<String, Object>>) urlMap.get("b");
						for (int j = 0; j < infos.size(); j++) {
							apkList1.add(Mycheering_Detail.getApkDetail(infos.get(j)));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return apkList1;
//			page.putField("apks", apkList);
//			if (page.getResultItems().get("apks") == null) {
//				page.setSkip(true);
//			}
		}
		return null;
	}
	// 提取相关的json里面的信息
		private static List<Integer> ExtraJson(String str, String key) {
			List<Integer> typeId = new ArrayList<Integer>();
			Map<String, Object> urlMap = null;
			try {
				urlMap = new ObjectMapper().readValue(str, Map.class);
				if (null != urlMap) {
					List<Map<String, Object>> infos = (List<Map<String, Object>>) urlMap.get("b");
					for (int j = 0; j < infos.size(); j++) {
						typeId.add((Integer) infos.get(j).get(key));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return typeId;
		}
}
