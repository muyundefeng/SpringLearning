package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 太平洋[中国] app搜索抓取 url:http://ks.pconline.com.cn/download.shtml?q=qq
 *
 * @version 1.0.0
 */
public class PageProPconline_Detail {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProPconline_Detail.class);

	public static Apk getApkDetail(Page page) {
		// 获取dom对象
		Html html = page.getHtml();

		// 找出对应需要信息，下载需要TOKEN需要处理，使用htmlunit模式浏览器请求
		String appDetailUrl = page.getUrl().toString();
		String appName = html.xpath("//div[@class='thead']/span/h1/text()")
				.toString();
		if (appName == null)
			return null;
		String appVersion = null;
		String appDownloadUrl = null;
		try {
			String hashCode = getHash(appDetailUrl);
			if (null != hashCode) {
				String tempUrl = html.xpath(
						"//a[@class='btn sbDownload']/@tempurl").toString();
				if (null != tempUrl) {
					String[] ss = tempUrl.split("/");
					StringBuffer strBuf = new StringBuffer();
					strBuf.append("http://");
					for (int i = 2; i < ss.length - 1; i++) {
						strBuf.append(ss[i] + "/");
					}
					strBuf.append(hashCode + "/");
					strBuf.append(ss[ss.length - 1]);
					// System.out.println(strBuf.toString());

					appDownloadUrl = strBuf.toString();
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String osPlatform = html.xpath(
				"//span[@itemprop='operatingSystem']/text()").toString();
		String appSize = html.xpath(
				"//ul[@class='megList']/li[1]/i[2]/span[2]/text()").get();
		String appUpdateDate = html.xpath(
				"//ul[@class='megList']/li[2]/i[2]/span[3]/text()").get();
		String appType = null;

		String appDescription = html.xpath(
				"//span[@itemprop='description']/p[1]/text()").get();
		List<String> appScreenshot = html.xpath(
				"//div[@id='Jpmjt']/div/div/ul/li/img/@src").all();
		String appTag = null;
		String appCategory = html.xpath("//i[@class='fl']/a[5]/text()").get();
		String appCommentUrl = null;
		String appComment = null;
		String dowloadNum = null;

		Apk apk = null;
		if (appDownloadUrl == null)
			appDownloadUrl = "adfasfdas";
		if (null != appName && null != appDownloadUrl) {
			apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform,
					appVersion, appSize, appUpdateDate,
					null != appType ? appType : "APK");
			apk.setAppDescription(appDescription);
			apk.setAppScreenshot(appScreenshot);
			apk.setAppCommentUrl(appCommentUrl);
			apk.setAppComment(appComment);
			apk.setAppDownloadTimes(dowloadNum);
			apk.setAppCategory(appCategory);
			apk.setAppTag(appTag);
			System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			//System.out.println("appVenderName="+appVenderName);
			//System.out.println("appDownloadedTime="+appDownloadedTime);
			System.out.println("appDescription="+appDescription);
			System.out.println("appTag="+appTag);
			System.out.println("appScrenshot="+appScreenshot);
			System.out.println("appCategory="+appCategory);
			
		}

		LOGGER.debug(
				"name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}"
						+ ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}",
				appName, appVersion, appDownloadUrl, appSize, appType,
				osPlatform, appUpdateDate, dowloadNum, appTag, appCategory,
				appScreenshot, appCommentUrl, appComment, appDescription);

		return apk;
	}

	// send get request to http://dlc2.pconline.com.cn/dltoken/61068_genLink.js
	public static String getHash(String metaUrl)
			throws ClientProtocolException, IOException {
		// http://dl.pconline.com.cn/download/78212.html
		String apkId = getApkId(metaUrl);
		StringBuffer jsUrl = new StringBuffer();
		jsUrl.append("http://dlc2.pconline.com.cn/dltoken/");
		jsUrl.append(apkId);
		jsUrl.append("_genLink.js");
		Map<String, String> requestHeader = new HashMap<String, String>();
		requestHeader.put("Cache-Control", "max-age=0");
		requestHeader.put("Connection", "keep-alive");
		requestHeader.put("Host", "dlc2.pconline.com.cn");
		requestHeader.put("Referer", metaUrl);
		requestHeader
				.put("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.76 Safari/537.36");
		String content = SinglePageDownloader.getHtml(jsUrl.toString(), "GET",
				null, requestHeader);

		String hashCode = null;
		// System.out.println("content=" + content);
		// genLink('wArMGj7a')
		String[] ss = content.split("genLink\\('");
		if (ss.length > 1) {
			hashCode = ss[1].split("'\\)")[0];
		}

		return hashCode;
	}

	// get apkId from meta url
	// such as:http://dl.pconline.com.cn/download/61068.html
	// return:61068
	public static String getApkId(String url) {
		// String s = "http://dl.pconline.com.cn/download/61068.html";
		String[] ss = url.split("http://dl.pconline.com.cn/download/");
		if (ss.length > 1) {
			return ss[1].split(".html")[0];
		} else {
			return null;
		}

	}

}
