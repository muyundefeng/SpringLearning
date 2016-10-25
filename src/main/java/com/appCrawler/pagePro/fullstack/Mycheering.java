package com.appCrawler.pagePro.fullstack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mycheering_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader2;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 青橙游戏助手 网站主页:http://apps.mycheering.com/ 
 * 渠道编号:365 
 * 相关的app信息保存在json格式的文件之中
 */

public class Mycheering implements PageProcessor {

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {

		if (page.getUrl().toString().equals("http://apps.mycheering.com/")) {
			// http://api.apps.mycheering.com/api.aspx?&iszip=1&m=l&id="+sid+"&pi="+pageindex+"&ps=20
			String json = SinglePageDownloader
					.getHtml("http://api.apps.mycheering.com/api.aspx?&iszip=1&m=c&id=28&pi=1&ps=20");
			String json1 = SinglePageDownloader
					.getHtml("http://api.apps.mycheering.com/api.aspx?&iszip=1&m=c&id=27&pi=1&ps=20");
			List<String> apkList = new ArrayList<String>();
			List<Integer> typeId = ExtraJson(json, "c");
			List<Integer> typeId1 = ExtraJson(json1, "c");
			for (Integer str : typeId) {
				for (int i = 1; i <= 13; i++) {
					apkList.add(
							"http://api.apps.mycheering.com/api.aspx?&iszip=1&m=l&id=" + str.intValue() + "&pi=" + i + "&ps=20");
				}
			}
			for (Integer str : typeId1) {
				for (int i = 1; i <= 13; i++) {
					apkList.add(
							"http://api.apps.mycheering.com/api.aspx?&iszip=1&m=l&id=" + str.intValue() + "&pi=" + i + "&ps=20");
				}
			}
			List<Apk> apkList1 = new ArrayList<Apk>();
			List<String> apks = new ArrayList<String>();
			for (String url : apkList) {
				String appDetail = SinglePageDownloader.getHtml(url);
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

			page.putField("apks", apkList1);
			if (page.getResultItems().get("apks") == null) {
				page.setSkip(true);
			}
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

	// 提取相关的json里面的信息
	private static List<Integer> ExtraJson(String str, String key) {
		List<Integer> typeId = new ArrayList<Integer>();
		Map<String, Object> urlMap = null;
		try {
			urlMap = new ObjectMapper().readValue(str, Map.class);
			if (null != urlMap) {
				List<Map<String, Object>> infos = (List<Map<String, Object>>) urlMap.get("b");
				for (int j = 0; j < infos.size(); j++) {
					typeId.add((Integer)infos.get(j).get(key));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeId;
	}
}
