package com.appCrawler.pagePro.apkDetails;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.appCrawler.utils.MyNicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 当乐安致 http://www.d.cn/
 * 有三种不同的页面分类，分别是网游、应用和游戏
 * @author DMT
 */
public class PageProNgDCn_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProNgDCn_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        if(page.getUrl().regex("http://android\\.d\\.cn/game.*html").match()
                || page.getUrl().regex("http://android\\.d\\.cn/software.*").match()){
            Apk apk = null;
            String appName = null;              //app名字
            String appDetailUrl = null;         //具体页面url
            String appDownloadUrl = null;       //app下载地址
            String osPlatform = null ;          //运行平台
            String appVersion = null;           //app版本
            String appSize = null;              //app大小
            String appUpdateDate = null;        //更新日期
            String appType = null;              //下载的文件类型 apk？zip？rar？ipa?
            String appvender = null;            //app开发者  APK这个类中尚未添加
            String appDownloadedTime=null;      //app的下载次数
            String appDescription =null;        //app的详细介绍

            appName =page.getHtml().xpath("//div[@class='de-app-des']/h1/text()").toString();
            if(appName == null )
                appName =page.getHtml().xpath("//div[@class='de-head-l']/h1/text()").toString();
            appDetailUrl = page.getUrl().toString();

            if(page.getHtml().xpath("//ul[@class='de-down']/li[1]/a/text()").toString() != null)
            // 获取下下载链接，模拟浏览器请求
            appDownloadUrl = getDownloadUrl2(page.getUrl().get());

            appVersion = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[2]/span[2]/text()").toString();

            appSize = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[4]/text()").toString();

            appUpdateDate = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[3]/text()").toString();

            String typeString = page.getHtml().xpath("//ul[@class='de-app-tip clearfix']/li[4]/text()").toString();
            appType =typeString;
            String tempString = page.getHtml().xpath("//ul[@class='de-game-info clearfix']").toString();
            if(StringUtils.isNotEmpty(tempString) && tempString.contains("热度")){
                osPlatform = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[9]/text()").toString();
                appvender = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[10]/a/text()").toString();
                if(null == appvender)
                    appvender = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[10]/text()").toString();
            }
            else{
                osPlatform = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[8]/text()").toString();
                appvender = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[9]/a/text()").toString();
                if(null == appvender)
                    appvender = page.getHtml().xpath("//ul[@class='de-game-info clearfix']/li[9]/text()").toString();

            }

            String descriptionString = page.getHtml().xpath("//div[@class='de-intro-inner']/text()").toString();
            appDescription = descriptionString;
            List<String> appScreenshot = html.xpath("//ul[@class='shot-list pr clearfix fl']/li/img/@src").all();
            String appCategory=html.xpath("//li[@class='de-game-firm']/a/text()").toString();
//            System.out.println("appName="+appName);
//            System.out.println("appDetailUrl="+appDetailUrl);
//            System.out.println("appDownloadUrl="+appDownloadUrl);
//            System.out.println("osPlatform="+osPlatform);
//            System.out.println("appVersion="+appVersion);
//            System.out.println("appSize="+appSize);
//            System.out.println("appUpdateDate="+appUpdateDate);
//            System.out.println("appType="+appType);
//            System.out.println("appvender="+appvender);
//            System.out.println("appDownloadedTime="+appDownloadedTime);
//            System.out.println("appDescription="+appDescription);

            if(appName != null && appDownloadUrl != null){
                apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
                apk.setAppScreenshot(appScreenshot);
                apk.setAppDescription(appDescription);
                apk.setAppCategory(appCategory);
                apk.setAppDownloadTimes(appDownloadedTime);
            }
            LOGGER.info("return from D.process()");
            return apk;
        }
        else if(page.getUrl().regex("http://ng\\.d\\.cn/.*").match()){
            Apk apk = null;
            String appName = null;              //app名字
            String appDetailUrl = null;         //具体页面url
            String appDownloadUrl = null;       //app下载地址
            String osPlatform = null ;          //运行平台
            String appVersion = null;           //app版本
            String appSize = null;              //app大小
            String appUpdateDate = null;        //更新日期
            String appType = null;              //下载的文件类型 apk？zip？rar？ipa?
            String appvender = null;            //app开发者  APK这个类中尚未添加
            String appDownloadedTime=null;      //app的下载次数
            String appDescription =null;        //app的详细介绍

            appName =page.getHtml().xpath("//h1[@class='gameNameTitle']/a/text()").toString();

            appDetailUrl = page.getUrl().toString();

            String allinfoString = page.getHtml().xpath("//div[@class='rigame fl']").toString();
            if(allinfoString == null)
            {
                LOGGER.info("return from D.process()");
                return null;
            }
            while(allinfoString.contains("<"))
                if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
                else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
                else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());

            appDownloadUrl = page.getHtml().xpath("//div[@class='downAnd mb10']/a/@href").toString();
            System.out.println("allinfoString"+allinfoString);
            appVersion = allinfoString.substring(allinfoString.indexOf("版本号")+4,allinfoString.indexOf("更新")-1).replace("\n", "");


            appUpdateDate = allinfoString.substring(allinfoString.indexOf("更新")+5,allinfoString.length()).replace("\n", "");
            String appCategory=allinfoString.substring(allinfoString.indexOf("类型")+3,allinfoString.length()).replace("\n", "");

            String descriptionString = page.getHtml().xpath("//div[@class='zgamejs']/p/text()").toString();
            appDescription = descriptionString;

            List<String> appScreenshot = html.xpath("//div[@class='snapShotCont']/div/img/@src").all();


            System.out.println("appName="+appName);
            System.out.println("appDetailUrl="+appDetailUrl);
            System.out.println("appDownloadUrl="+appDownloadUrl);
            System.out.println("osPlatform="+osPlatform);
            System.out.println("appVersion="+appVersion);
            System.out.println("appSize="+appSize);
            System.out.println("appUpdateDate="+appUpdateDate);
            System.out.println("appType="+appType);
            System.out.println("appvender="+appvender);
            System.out.println("appDownloadedTime="+appDownloadedTime);
            System.out.println("appDescription="+appDescription);

            if(appName != null && appDownloadUrl != null){
                apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
                apk.setAppScreenshot(appScreenshot);
                apk.setAppDescription(appDescription);
                apk.setAppCategory(appCategory);
            }
            LOGGER.info("return from D.process()");
            return apk;
        }

        return null;
    }
//
//    /**
//     * 模拟浏览器请求一次，获取下载链接
//     * @param url
//     * @return
//     */
//    private static String getDownloadUrl(String url) {
//        MyNicelyResynchronizingAjaxController ajaxController = new MyNicelyResynchronizingAjaxController();
//        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
//        //HtmlUnitDriver
//        //设置webClient的相关参数
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.setAjaxController(ajaxController);
//        webClient.getOptions().setTimeout(35000);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//    
//        try {
//            //模拟浏览器打开一个目标网址
//            UnexpectedPage page = webClient.getPage("http://android.d.cn/rm/red/1/" + StringUtils.substringBefore(StringUtils.substringAfterLast(url, "/"), "."));
//            JSONObject json = JSON.parseObject(page.getWebResponse().getContentAsString());
//            JSONArray objects = json.getJSONArray("pkgs");
//
//            if (objects.size() > 0) {
//                return objects.getJSONObject(0).getString("pkgUrl");
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }
    
    private static String getDownloadUrl2(String pageUrl){
    	String sourcefile="";
    	String lines="";
    	String pageid=pageUrl.substring(pageUrl.lastIndexOf("/")+1,pageUrl.lastIndexOf(".html"));    			
    	String downloadUrlString = "http://android.d.cn/rm/red/1/"+pageid;
    	try {
			//打开一个网址，获取源文件
			
			URL url=new URL(downloadUrlString);		
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			 while ((lines = reader.readLine()) != null){
				 	sourcefile=sourcefile+lines;
				 	
				}				
		} catch (Exception e) {
		}
    	//{"isSet":false,"pkgs":[{"base64":"aHR0cDovL2FuZHJvaWQuZC5jbi9ybS9wYz9ydD0xJmlkPTUxMjI1JnBpbnlpbj0mcGtnSWQ9NTczMjE0JnBrZ1VybD1odHRwOi8vdS5hbmRyb2lkZ2FtZS1zdG9yZS5jb20vbmV3L2dhbWUxLzU0LzkxNjU0L25iYTJrMTZfMTQ0NDg4MDY5NjU0Mi5kcGs/Zj13ZWJfMnxodHRwOi8vdXVzLWltZzcuYW5kcm9pZC5kLmNuL2NvbnRlbnRfcGljLzIwMTUxMC9iZWhwaWMvaWNvbi8yMjUvMS01MTIyNS9pY29uLTE0NDQ4NzE0NzU1NzAucG5nfE5CQTJLMTYo5ZCr5pWw5o2u5YyFKXxjb20udDJrc3BvcnRzLm5iYTJrMTZhbmRyb2lkfDAuMC4yMQ==",
    	//"pkgUrl":"http://down.androidgame-store.com/201510161139/50E0A174FD81BF2A49B23FB03D3BF006/new/game1/54/91654/nba2k16_1444880696542.dpk?f=web_1","qrcode":"http://android.d.cn/qrcode/-/rm/1/51225/573214/?http://down.androidgame-store.com/201510161139/50E0A174FD81BF2A49B23FB03D3BF006/new/game1/54/91654/nba2k16_1444880696542.dpk?f=web_1","name":"通用版","isDPK":"1","baiduUrl":"http://pan.baidu.com/s/1qWzhDyW","pi":573214}],"msg":""}

    	return StringUtils.substringBetween(sourcefile, "pkgUrl\":\"", "\",\"qrcode");
    }
 
//    public static void main(String[] args){
//    	System.out.println(getDownloadUrl2("http://android.d.cn/rm/red/1/51225.html"));
//    }
//    
}
