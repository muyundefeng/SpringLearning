package com.appCrawler.pagePro;

import java.util.List;


import com.appCrawler.pagePro.apkDetails.Down7x_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 7喜  http://www.7xdown.com/downlist/r_9_1.html
 * Down7x #212
 * @author tianlei
 * @modify author lisheng
 */
public class Down7x implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	
	@Override
	public Apk process(Page page) {
    try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		if(page.getHtml().regex("http://www\\.7xdown\\.com/search\\.asp?.*").match())
        {
        	List<String> apkList=page.getHtml().xpath("//div[@class='wp']/div[@class='con-search']/h3/a/@href").all();
        	List<String> pageList=page.getHtml().xpath("//div[@class='wp']/table/tbody/tr/td[2]/a/@href").all();
        	apkList.addAll(pageList);
        	for(String url:apkList)
        	{
        		if(PageProUrlFilter.isUrlReasonable(url))
        		{
        			if(url.contains("../"))
        			{
        				url=url.replace("../", "");
        			}
        			page.addTargetRequest(url);
        		}
        	}
        }
		if(page.getUrl().regex("http://www\\.7xdown\\.com/downinfo/.*").match())
		{
			return Down7x_Detail.getApkDetail(page);
		}
        
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

//	public static Page getDynamicPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
//		MyNicelyResynchronizingAjaxController ajaxController = new MyNicelyResynchronizingAjaxController();
//		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
//		//HtmlUnitDriver
//		//设置webClient的相关参数
//		webClient.getOptions().setJavaScriptEnabled(true);
//		webClient.getOptions().setCssEnabled(false);
//		webClient.setAjaxController(ajaxController);
//		webClient.getOptions().setTimeout(35000);
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		//webClient.setAlertHandler( new CollectingAlertHandler(alertHandler));//将JavaScript中alert标签产生的数据保存在一个链表中
//		String targetUrl = url;
//		//模拟浏览器打开一个目标网址
//		HtmlPage rootPage= webClient.getPage(targetUrl);
//		//System.out.println(rootPage.asXml());
//		Page page = new Page();
//		page.setRawText(rootPage.asXml());
//		page.setUrl(new PlainText(url));
//		page.setRequest(new Request(url));
//		return page;
//	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
