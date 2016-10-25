package com.appCrawler.pagePro.apkDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import com.app.saveDB.Persistent.CrawlerToRedisBridge;
import com.app.saveDB.Persistent.svaeInfoToData;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.myApks;
import us.codecraft.webmagic.selector.Html;
/**
 * 360手机助手，获取apk详情工具
 * @author buildhappy
 *
 */
public class PagePro360_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro360_Detail.class);
	private static int count = 0;
    private static List<myApks> list = new ArrayList<myApks>();
	private static final int COUNT = 6000;
	public static Apk getApkDetail(Page page){
		Html html = page.getHtml();
        //System.out.println(html);
		String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//h2[@id='app-name']/span/text()").toString();
        System.out.println(appName);
        String appVersion = html.xpath("//div[@class='base-info']/table/tbody/tr[2]/td[1]/text()").get();
        String app[] = StringUtils.split(appDetailUrl, "/");
        String appId = app[app.length-1];
        System.out.println(appId);
        String appDownloadUrl = StringUtils.substringBetween(html.get(), "'downloadUrl': '", "'");
        System.out.println(appDownloadUrl);
        // String appDownloadUrl = StringUtils.substringBetween(html.get(), "'downloadUrl': '", "'");
       // appDownloadUrl = StringUtils.split(appDownloadUrl,"&url=")[1];
        String osPlatform = html.xpath("//div[@class='base-info']/table/tbody/tr[2]/td[2]/text()").get();
        String appSize = html.xpath("//div[@class='pf']/span[4]/text()").get();
        String appUpdateDate = html.xpath("//div[@class='base-info']/table/tbody/tr[1]/td[2]/text()").get();
        String appType = null;
        String appDownloadedTime= StringUtils.substringAfter(html.xpath("//div[@class='pf']/span[3]/text()").get(), "：");
        	appDownloadedTime = appDownloadedTime.replace("次", "").replace("万", "0000");
        String appDescription = html.xpath("//div[@class='breif']/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='overview']//img/@src").all();
        String appTag = html.xpath("//div[@class='app-tags']//a/text()").all().toString();
       
        //String isAd = html.xpath("//div[@class='infors-txt']").toString();
        //if(isAd.contains("无广告")){
        	if(count!=COUNT&&appName!=null&&appDownloadUrl!=null){
        		myApks apks = new myApks(appName, appDownloadUrl);
        		list.add(apks);
        		count++;
        		LOGGER.info("app number is"+count);
        	}
        	if(count==COUNT){
        		ObjectMapper objectMapper = new ObjectMapper();
        		try {
					String json = objectMapper.writeValueAsString(list);
					System.out.println(json);
					svaeInfoToData.writeToFile("/home/lisheng/data/info.txt", json);
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.exit(0);
        	}
        	//CrawlerToRedisBridge.receiveData(appName, appDownloadUrl);
        //}
//        if(isAd.contains("无广告")){
//        	CrawlerToRedisBridge.receiveData(appName, appDownloadUrl);
//        }
 

        // 评论url
        String commontUrl = "";
        LOGGER.info("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, commontUrl:{}, "
        		+ "downloadNum:{}, appDesc:{}", appName, appVersion, appDownloadUrl, appSize, 
        		appType, osPlatform, appUpdateDate, commontUrl, appDownloadedTime, appDescription);
       
    	
    	

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppDownloadTimes(appDownloadedTime);
            apk.setAppCommentUrl(commontUrl);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppTag(appTag);

            /*获取评论地址，在服务器上运行这部分代码时一直报错，暂时注释掉
            try {
                // 处理评论
                HttpClientLib clientLib = new HttpClientLib();
                commontUrl = String.format("http://intf.baike.360.cn/index.php?name=%s&c=message&a=getmessage&start=1&count=20", URLEncoder.encode(appName, "UTF-8"));
                HttpResponse response = clientLib.getUrlReponse(commontUrl);

                // 获取总条数
                JSONObject json = JSON.parseObject(EntityUtils.toString(response.getEntity()));
                if (!"-1".equals(json.getString("errno"))) {
                    int count = json.getJSONObject("data").getInteger("total");

                    // 处理分页评论url
                    List<String> commList = new ArrayList<String>();
                    int loop = count / 20 + 1;
                    for (int i = 1; i <= loop; i++) {

                        /// 需要再APK再加个List存放
                        commList.add(String.format("http://intf.baike.360.cn/index.php?name=%s&c=message&a=getmessage&start=%s&count=20", URLEncoder.encode(appName, "UTF-8"), i * 20 + 1));
                    }

                    apk.setAppCommentUrl(StringUtils.substringBetween(commList.toString(), "[", "]"));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
             */
        }
        
        return apk;
	}
}
