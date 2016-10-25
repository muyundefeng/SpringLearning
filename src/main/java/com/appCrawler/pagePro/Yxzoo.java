package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yxzoo_Detail;
import com.gargoylesoftware.htmlunit.html.HtmlLabel;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * Yxzoo游戏网站
 * http://www.yxzoo.com/game
 * 伪造主页 分离关键字
 * @id 427
 * @author lisheng
 */
public class Yxzoo implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.996\\.com/search/index\\.html\\?keyword.*").match())
		{
			String keyWord=page.getUrl().toString().split("=")[1];
			
			try {
				keyWord=URLDecoder.decode(keyWord,"UTF-8");
				System.out.println(keyWord);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int i=1;;i++)
			{
				String jsonurl="http://www.yxzoo.com/plus/game_ajax.php?pt=android&fx=&lx=&q="+keyWord+"&order=new&x=&page="+i+"&random=0.20082855341024697";
				String html=SinglePageDownloader.getHtml(jsonurl);
				//System.out.println(html);
				if(html.contains("list"))
				{
					//System.out.println("hel");
					try {
						ObjectMapper objectMapper=new ObjectMapper();
						String html1=html.split(",\"pagination\":\"")[0]+"}";
						//System.out.println(html1);
						Map<String, Object> map=objectMapper.readValue(html1, Map.class);
						//System.out.println(map);
						List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("list");
						//System.out.println(list);
						for(Map<String, Object> map1:list)
						{
							String appUrl=map1.get("arcurl").toString();
							//System.out.println(appUrl);
							page.addTargetRequest(appUrl);
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				else{
					break;
				}
		}
	}
		if(page.getUrl().regex("http://www\\.yxzoo\\.com/.*").match()
				&&!page.getUrl().toString().equals("http://www.yxzoo.com/game"))
			{
				return Yxzoo_Detail.getApkDetail(page);
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
