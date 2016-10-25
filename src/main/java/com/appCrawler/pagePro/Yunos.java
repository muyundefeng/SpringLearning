package com.appCrawler.pagePro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Yunos_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 云os应用商店
 * 搜索接口：http://apps.yunos.com/search.htm?keyWord=*#*#*#
 * app详细信息从客户端进行抓取
 * Aawap #646
 * @author lisheng
 */
public class Yunos implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
		return null;
		
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		List<Apk> apks = new ArrayList<Apk>();
		if(page.getUrl().regex("http://apps.yunos.com/search.htm.*").match())
		{
			String keyWord = page.getUrl().toString().split("keyWord")[1];
			String url = "http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=0&screen_height=0&phone_type=&phone_ver=&apilevel=&method=search&catId="+keyWord+"&topicId="+keyWord+"&keyword="+keyWord+"&selected=true&code="+keyWord+"&applistId="+keyWord+"&page=1&pageSize=32";
			

			String json = SinglePageDownloader.getHtml(url);
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = null;
			try {
				map = objectMapper.readValue(json, Map.class);
			} catch (JsonParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, Object> map1 =(Map<String, Object>) map.get("result");
			String total = map1.get("total").toString();
			int total1 = Integer.parseInt(total);
			for(int i=1;i<(total1/32)+1;i++)
			{
				String url1 = url.replace("page=1", "page="+i);
				String json1 = SinglePageDownloader.getHtml(url1);
				//ObjectMapper objectMapper1 = new ObjectMapper();
				Map<String, Object> map2 = null;
				try {
					map2 = objectMapper.readValue(json1, Map.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Map<String, Object> map3 =(Map<String, Object>) map2.get("result");
				List<Map<String, Object>> list = (List<Map<String, Object>>)map3.get("apps");
				for(Map<String, Object> map4:list){
					String pkg = map4.get("pkg").toString();
					//构造详情页app url地址
					apks.add(Yunos_Detail.getApkDetail(pkg));
					
				}
				
				//String total = map1.get("total").toString();
			}
		
		}
		return apks;
	}

}
