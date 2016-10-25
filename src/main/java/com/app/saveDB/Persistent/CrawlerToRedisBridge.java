package com.app.saveDB.Persistent;

/**
 * @author lisheng
 *爬虫与Redis之间的链接桥梁
 */
public class CrawlerToRedisBridge {

	static {
		SaveToRedis.init();
	}
	public static SaveToRedis saveToRedis = new SaveToRedis();
	public static void receiveData(String appName,String appDownloadUrl){
		saveToRedis.saveToQueue(appName, appDownloadUrl);
	}
}
