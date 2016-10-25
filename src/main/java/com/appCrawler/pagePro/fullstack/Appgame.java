package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Appgame_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PostSubmit;
/**
 * 任玩堂
 * 网站主页：http://www.appgame.com/
 * 相关的android应用的获取是通过post提交请求实现的
 * 返回的数据格式为json格式的数据
 * @lisheng
 */


public class Appgame implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml());
	if("http://www.appgame.com/".equals(page.getUrl().toString()))
	{
		List<String> appList=new ArrayList<String>();
		//构造json url链接地址
		String jsonUrl="http://app.appgame.com/appgame/index.php?m=api&c=search&a=get_category";
		for(int i=1;;i++)
		{
			//调用post请求参数
			String params="device=android&inter=single&order=createTime&offset="+(1+24*i-24);
			String json=PostSubmit.postGetData(jsonUrl, params);
			Map<String, Object> urlMap = null;
			if(json.contains("game_id"))
			{
				try{
				ObjectMapper objectMapper=new ObjectMapper();
				urlMap=objectMapper.readValue(json, Map.class);
				if (null != urlMap) {
		        	 
		             List<Map<String, Object>> infos = (List<Map<String, Object>>) urlMap.get("result");
		             for(int j=0;j<infos.size();j++)
		             {
		            	 //分理处相关的game id编号
		            	 String gameId=infos.get(j).get("game_id").toString();
		            	 //构造app详情页的url链接地址
		            	 String appUrl="http://app.appgame.com/game/"+gameId+".html";
		            	 System.out.println("fetch app url"+appUrl);
		            	 LOGGER.debug("fetch app url"+appUrl);
		            	 appList.add(appUrl);
		             }
		         }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			else{
				break;
			}
			page.addTargetRequests(appList);
		}
	}
		//提取页面信息
		if(page.getUrl().regex("http://app\\.appgame\\.com/game/.*").match())
		{
			//System.out.println(page.getHtml());
			Apk apk = Appgame_Detail.getApkDetail(page);
			
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
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
}
