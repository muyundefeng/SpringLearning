package com.appCrawler.pagePro.fullstack;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.omg.CORBA.StringHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yunos_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.MapConstraint;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 云os应用商店
 * 网站主页;http://apps.yunos.com/index.htm
 * app详细信息从客户端进行抓取
 * Aawap #646
 * @author lisheng
 */


public class Yunos implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		List<Apk> apks = new ArrayList<Apk>();
		if("http://apps.yunos.com/index.htm".equals(page.getUrl().toString()))
		{
			String categorys[] = {"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1013&topicId=1013&keyword=1013&selected=true&code=1013&applistId=1013&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1011&topicId=1011&keyword=1011&selected=true&code=1011&applistId=1011&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1010&topicId=1010&keyword=1010&selected=true&code=1010&applistId=1010&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1009&topicId=1009&keyword=1009&selected=true&code=1009&applistId=1009&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1008&topicId=1008&keyword=1008&selected=true&code=1008&applistId=1008&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1006&topicId=1006&keyword=1006&selected=true&code=1006&applistId=1006&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1005&topicId=1005&keyword=1005&selected=true&code=1005&applistId=1005&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1004&topicId=1004&keyword=1004&selected=true&code=1004&applistId=1004&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1003&topicId=1003&keyword=1003&selected=true&code=1003&applistId=1003&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1002&topicId=1002&keyword=1002&selected=true&code=1002&applistId=1002&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1001&topicId=1001&keyword=1001&selected=true&code=1001&applistId=1001&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1863&topicId=1863&keyword=1863&selected=true&code=1863&applistId=1863&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=app&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=listAppByCatId&catId=1864&topicId=1864&keyword=1864&selected=true&code=1864&applistId=1864&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1025&topicId=1025&keyword=1025&selected=true&code=1025&applistId=1025&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1024&topicId=1024&keyword=1024&selected=true&code=1024&applistId=1024&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1021&topicId=1021&keyword=1021&selected=true&code=1021&applistId=1021&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1026&topicId=1026&keyword=1026&selected=true&code=1026&applistId=1026&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1022&topicId=1022&keyword=1022&selected=true&code=1022&applistId=1022&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1221&topicId=1221&keyword=1221&selected=true&code=1221&applistId=1221&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1023&topicId=1023&keyword=1023&selected=true&code=1023&applistId=1023&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1018&topicId=1018&keyword=1018&selected=true&code=1018&applistId=1018&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1222&topicId=1222&keyword=1222&selected=true&code=1222&applistId=1222&page=1&pageSize=32",
					"http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=&phone_ver=&apilevel=19&method=listAppByCatId&catId=1906&topicId=1906&keyword=1906&selected=true&code=1906&applistId=1906&page=1&pageSize=32"};
			
			for(String str:categorys)
			{
				String json = SinglePageDownloader.getHtml(str);
				ObjectMapper objectMapper = new ObjectMapper();
				System.out.println(json);
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
					String url = str.replace("page=1", "page="+i);
					String json1 = SinglePageDownloader.getHtml(url);
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
			//page.addTargetRequest("http://apk.3310.com/apps/lttx/list_92_1.html");
			//page.addTargetRequest("http://apk.3310.com/game/jsmx/list_103_1.html");
		}
	
			page.putField("apks", apks);
			if(page.getResultItems().get("apks") == null){
				page.setSkip(true);
				}

		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
}
