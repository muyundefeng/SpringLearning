package com.appCrawler.pagePro;
/**
 * 酷派coolmarket http://www.coolmart.net.cn/
 * Coolmarket #209
 * @author DMT
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Coolmarket_Detail;
import com.appCrawler.utils.MyNicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;


public class Coolmarket implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	private static int count=0;
	@Override
	public Apk process(Page page) {
		//index page		http://aawap.net/aawap/search?searchName=%E6%96%97%E5%9C%B0%E4%B8%BB		
		if(page.getUrl().regex("http://www\\.coolmart\\.net\\.cn/#id=search.*").match()
				||page.getUrl().regex("http://api\\.aps\\.qq\\.com/wapapi/getSearch.*").match()){
			
			String key=null;			             
			String infourl=null;			             
			String htmlinfo =null;			             
			key =getKey(page.getUrl().toString());
			//System.out.println(key);
			//infourl ="http://api.aps.qq.com/wapapi/getSearch?keyword="+key+"&pageSize=20&pageNo=0&channel=77905&platform=touch&network_type=unknown&resolution=1366x768";
			//添加相关的搜索页面
			for(int i=1;i<5;i++)
			{
				String url="http://api.aps.qq.com/wapapi/getSearch?keyword="+key+"&pageSize=20&pageNo="+i+"&channel=77905&platform=touch&network_type=unknown&resolution=1366x768";
				//infourl=url;
				String pageStr=SinglePageDownloader.getHtml(url);
			    if(!pageStr.contains("apkId"))
			    {
			    	break;
			    }
			    else{
			    	page.addTargetRequest(url);
			    }
			}
			if(page.getUrl().regex("http://api\\.aps\\.qq\\.com/wapapi/getSearch.*").match())
			{
				htmlinfo=SinglePageDownloader.getHtml(page.getUrl().toString(),"get",null);
				//System.out.println(htmlinfo);
				List<String> id=getId(htmlinfo);
				System.out.println(id);
				for (int i=0;i<id.size();i++)
				{
					page.addTargetRequest("http://www.coolmart.net.cn/#id=detail&appid="+id.get(i));
				}
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://www\\.coolmart\\.net\\.cn/#id=detail&appid=.*").match()){
			count++;
			System.out.println(count);
			return Coolmarket_Detail.getApkDetail(page);
		}
		
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static Page getDynamicPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		MyNicelyResynchronizingAjaxController ajaxController = new MyNicelyResynchronizingAjaxController();
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		//HtmlUnitDriver
		//设置webClient的相关参数
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(ajaxController);
		webClient.getOptions().setTimeout(35000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		//webClient.setAlertHandler( new CollectingAlertHandler(alertHandler));//将JavaScript中alert标签产生的数据保存在一个链表中
		String targetUrl = url;
		//模拟浏览器打开一个目标网址
		HtmlPage rootPage= webClient.getPage(targetUrl);
		//System.out.println(rootPage.asXml());
		Page page = new Page();
		page.setRawText(rootPage.asXml());
		page.setUrl(new PlainText(url));
		page.setRequest(new Request(url));
		return page;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	private static String getKey(String str){
    	String tmp=null;
		String regex="key=(.*)";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){       	
        	tmp=matcher.group(1).toString();       	
        }
    	return tmp;   	
    }

	private static List<String> getId(String str){
    	 List<String> tmp=new ArrayList<String>();
    	 //   "id": 6633,
		String regex="\"id\": ([^,]*)";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str);
     
        while(matcher.find()){        	
       	tmp.add(matcher.group(1).toString());       	
        }
    	return tmp;   	
    }
}
