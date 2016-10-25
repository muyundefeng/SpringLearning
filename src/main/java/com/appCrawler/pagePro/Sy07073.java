package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.pagePro.apkDetails.Sy07073_Detail;
import com.appCrawler.utils.MyNicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;

/**
 * 07073http://sy.07073.com/2-0-0
 *  #207
 * @author DMT
 */
public class Sy07073 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	
	@Override
	public Apk process(Page page) {
		//index page		http://aawap.net/aawap/search?searchName=%E6%96%97%E5%9C%B0%E4%B8%BB		
		
		if(page.getUrl().regex("http://sy\\.07073\\.com/.*search.*").match()){

			
			String info=page.getHtml().xpath("//div[@id='game_list']").toString();
				//	System.out.println(info);
			List<String> url1 = getUrl(info);
			HashSet<String> urlSet = new HashSet<String>(url1);
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				String url = it.next();
				//if(p1.matcher(url).matches()){
					page.addTargetRequest(url);
					//System.out.println("match:" + url);
				//}
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://sy\\.07073\\.com/game/.*").match()){//http://www\\.ruan8\\.com/soft.*
			
			
			return Sy07073_Detail.getApkDetail(page);
		}
		
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	private static List<String> getUrl(String str){
    	List<String> tmp=new ArrayList<String>();
    	//open('http://sy.07073.com/game/15227.html','_blank')
		String regex="open.\'([^\']+?)\'";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        //System.out.println(str);
        while(matcher.find()){
        
        	tmp.add(matcher.group(1).toString());
        	
        }
    	return tmp;   	
    }

}
