package com.appCrawler.pagePro.fullstack;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Lewanduo_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.pagePro.apkDetails.You49_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 乐玩 http://www.lewanduo.cn//Home/gameCenter.html
 * 渠道编号：369
 * @author DMT
 * 所需要的详细信息在保存在json格式的文件。
 */


public class Lewanduo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	List<Apk> apks=new ArrayList<Apk>();
		if(page.getUrl().toString().equals("http://www.lewanduo.cn//Home/gameCenter.html"))
		{
		List<String> urlList=new ArrayList<String>();
		for(int i=1;i<=10;i++)
		{
			urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery183044690461084246635_1456313000043&p="+i+"&device=3&cid=136&_=1456313000614");
		}
		for(int i=1;i<=6;i++)
		{
			urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18306548638986423612_1456313252708&p="+i+"&device=3&cid=137&_=1456313252840");
		}
		for(int i=1;i<=3;i++)
		{
			urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18303786946004256606_1456313333259&p="+i+"&device=3&cid=138&_=1456313333418");
		}
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18307260284293442965_1456313373388&p=1&device=3&cid=139&_=1456313373591");
		urlList.add("http://api.sdk.49app.com/V7/Games/GetGame?callback=jQuery18307260284293442965_1456313373388&p=2&device=3&cid=139&_=1456313373591");
		Map<String, Object> urlMap = null;
		for(String str:urlList)
		{
			//下载页面
			String rawStr=SinglePageDownloader.getHtml(str);
			List<LinkedHashMap<String, Object>> list1=null;
			try {
				//System.out.println(rawStr);
				int start=rawStr.indexOf("game_id");
				System.out.println(start);
				System.out.println(rawStr.substring(start,rawStr.length()-4));
				rawStr="[{\""+rawStr.substring(start,rawStr.length()-4);
				rawStr=rawStr.replaceAll("\\],\\[",",");
				//rawStr="{\"total\":68482,\"result\":{\"code\":0,\"msg\":\"OK!\"},\"data\":"+rawStr+"}";
				ObjectMapper objectMapper=new ObjectMapper();
				System.out.println(rawStr);
				list1 = objectMapper.readValue(rawStr, List.class);
				//rawStr=rawStr.replaceAll("[]", "\"\"");
				//urlMap = new ObjectMapper().readValue(rawStr, Map.class);
				System.out.println(list1.size());
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list1!=null)
			{
				for (int j = 0; j < list1.size(); j++) {
					Map<String, Object> map = list1.get(j);
					apks.add(Lewanduo_Detail.getApkDetail(map));
				}
			}
		}
	//去重
//	HashSet h  =   new  HashSet(apks); 
//	apks.clear(); 
//	apks.addAll(h); 
	page.putField("apks", getNewList(apks));
	if(page.getResultItems().get("apks") == null){
		page.setSkip(true);
	}
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
	//去重
	public static List<Apk> getNewList(List<Apk> li){
        List<Apk> list = new ArrayList<Apk>();
        for(int i=0; i<li.size(); i++){
            Apk apk = li.get(i);  //获取传入集合对象的每一个元素
            if(!list.contains(apk)){   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(apk);
            }
        }
        return list;  //返回集合
    }
 
}
