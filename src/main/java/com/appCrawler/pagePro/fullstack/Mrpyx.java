package com.appCrawler.pagePro.fullstack;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mrpyx_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 凯斯应用
 * 渠道编号:314
 * 网站主页:http://mrpyx.cn/download/list.aspx?classid=1988
 * 主页进行替换
 */


public class Mrpyx implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		if(page.getUrl().toString().equals("http://mrpyx.cn/download/list.aspx?classid=1988"))
		{
			page.addTargetRequest("http://mrpyx.cn/wapindex.aspx?siteid=1000&classid=1992");//主页替换
			return null;
		}
		System.out.println(page.getUrl().toString());
		if(page.getUrl().toString().equals("http://mrpyx.cn/wapindex.aspx?siteid=1000&classid=1992"))
		{
			String appUrl=page.getHtml().xpath("//div[@class='xk_cd']/a[1]/@href").toString();
	 		String gameUrl=page.getHtml().xpath("//div[@class='xk_cd']/a[2]/@href").toString();
	 		page.addTargetRequest(appUrl);
	 		page.addTargetRequest(gameUrl);
	 		return null;
		}
		if(page.getUrl().toString().equals("http://mrpyx.cn/wapindex.aspx?siteid=1000&classid=1973")
				||page.getUrl().toString().equals("http://mrpyx.cn/wapindex.aspx?siteid=1000&classid=1968"))
		{
			List<String> appType=page.getHtml().xpath("//div[@class='xk_cd']/a/@href").all();//获取正真的app列表url
			List<String> appType1=page.getHtml().xpath("//div[@class='xk_yx']/a/@href").all();//获取正真的app列表url
			for(String str:appType)
			{
				if(!str.contains("apk")&&PageProUrlFilter.isUrlReasonable(str))
				{
					page.addTargetRequest(str);
				}
			}
			for(String str:appType1)
			{
				if(!str.contains("apk")&&PageProUrlFilter.isUrlReasonable(str))
				{
					page.addTargetRequest(str);
				}
			}
			return null;
		}
		if(page.getUrl().regex("http://mrpyx\\.cn/download/list\\.aspx\\?classid=\\d*").match())
		{
			String url=page.getHtml().xpath("//div[@class='bt1']/a/@onclick").toString();
			//对跳转onclick属性进行处理
			String str=(url.substring(13,url.length()-1)).replace("'","");
			//获取总的页数
			String totalPage=str.split(",")[0];
			String url1=str.split(",")[3];
			//构造链接
			for(int i=1;i<=Integer.parseInt(totalPage);i++)
			{
				String applistUrl="http://mrpyx.cn"+url1+"&page="+i;
				page.addTargetRequest(applistUrl);
			}
			return null;
		}
		List<String> appList=page.getHtml().xpath("//div[@class='mains']/div/table/tbody/tr/td/a/@href").all();
		for(String str:appList)
		{
			if(!str.contains("apk")&&PageProUrlFilter.isUrlReasonable(str))
			{
				page.addTargetRequest(str);
			}
		}
		//page.addTargetRequests(appList);
		if(page.getUrl().regex("http://mrpyx\\.cn/download-\\d*\\.html").match())
		{
			
			Apk apk = Mrpyx_Detail.getApkDetail(page);
			
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
