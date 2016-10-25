package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.pagePro.apkDetails.Lzvw_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #222
 * 龙珠游乐园
 * 可以通过伪造下载链接来构造下载链接
 * http://android.173sy.com/download/downloadapk.php?id=13425&s=1
 * 将id后的参数修改成相应的apk的id就好
 * @author Administrator
 *
 */
public class Lzvw implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		//if(page.getUrl().regex("http://android\\.173sy\\.com/games/search\\.php\\?key=*").match()){
		if(page.getUrl().regex("http://www\\.lzvw\\.com/list\\.asp\\?.*").match()){
			//app的具体介绍页面	
			String url=page.getUrl().toString();
			List<String> detailUrl = page.getHtml().xpath("//div[@class='listPart_info_cnName']/a/@href").all();
			
			//System.out.println( page.getHtml().xpath("//div[@class='list_device']/div[@class='listpart']/div[@class='listPart_img']/a/@href"));
			//System.out.println(detailUrl);
//			/List<String> pageUrl = page.getHtml().links("//div[@class='pages']").all();
			
			//添加下一页url(翻页)
			int length=url.length();
			int start=length-1;
			String replaceString="";
			if(url.contains("&"))
			{
				System.out.println(url);
				for(int j=start;j>0;j--)
				{
					if(url.charAt(j)==38)
					{
						break;
					}
				}
				replaceString=url.substring(0,start);
			}
			else
			{
				replaceString=url;
			}
			System.out.println(replaceString);
			List<String> pageUrl = new ArrayList<String>();
			int numberPage=Integer.parseInt(page.getHtml().xpath("//div[@class='listFoot']/form/span[3]/text()").toString());
			//String replace="Page"
			System.out.println(numberPage);
			for(int i=1;i<=numberPage;i++)
			{
				pageUrl.add(replaceString+"&Page="+i);
			}
			
			
			List<String> pageString=new ArrayList<String>();
			for(int i=0;i<pageUrl.size();i++)
			{
				
			}
			detailUrl.addAll(pageUrl);
			System.out.println("detailUrl size="+pageUrl);
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(detailUrl);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		if(page.getUrl().regex("http://www\\.lzvw\\.com/game/.*").match())
			//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
			{
				
				Apk apk = Lzvw_Detail.getApkDetail(page);
				
				page.putField("apk", apk);
				if(page.getResultItems().get("apk") == null){
					page.setSkip(true);
					}
				}
			else{
				page.setSkip(true);
				}
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
