package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Zhuodown_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 捉蛋网http://www.zhuodown.com/
 * Zhuodown #214
 * @author DMT
 * @modify author lisheng
 */


public class Zhuodown implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);
	public Apk process(Page page) { 
		if("http://www.zhuodown.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.zhuodown.com/a/yingyongruanjian/jingxuanruanjian/");
			page.addTargetRequest("http://www.zhuodown.com/a/youxi/xiuxianyouxi/");
			return null;
		}
		if(page.getUrl().regex("http://www\\.zhuodown\\.com/a/yingyongruanjian.*").match()
				||page.getUrl().regex("http://www\\.zhuodown\\.com/a/youxi/.*").match())
		{
			List<String> categorylist=page.getHtml().xpath("//ul[@class='d6']/li/a/@href").all();
			//System.out.println(categorylist);
			List<String> apkList=page.getHtml().xpath("//div[@class='listbox']/ul/li/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@class='dede_pages']/ul/li/a/@href").all();
			apkList.addAll(pageList);
			apkList.addAll(categorylist);
			System.out.println(apkList);
			for(String url:apkList)
			{
				if(PageProUrlFilter.isUrlReasonable(url))
				{
					page.addTargetRequest(url);
				}
			}
		}
		
//		String info=null;
//		String nexturl=null;
//		List<String> urls=page.getHtml().xpath("//ul[@class='e2']//a[1]/@href").all();
// 		info=page.getHtml().xpath("//ul[@class='pagelist']").toString();
// 		if(info!=null){
// 			nexturl=getNextUrl(info);
// 		}
//		Set<String> cacheSet = Sets.newHashSet();
//    
//        	cacheSet.addAll(urls);
//       
//        if(nexturl!=null){
//        	cacheSet.add(nexturl);
//        }
//        if("http://www.zhuodown.com/a/yingyongruanjian/jingxuanruanjian/".equals(page.getUrl().toString())){
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/xitonggongju/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/meitiyule/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/wangluotongxin/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/daohangdingwei/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/shejiaoliaotian/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/shiyonggongju/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/shenghuozhushou/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/yueduzixun/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/chuxinggouwu/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/zhuomianchajian/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/caijinglicai/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/shurufa/");
//        	cacheSet.add("http://www.zhuodown.com/a/yingyongruanjian/qitaruanjian/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/xiuxianyouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/shejiyouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/qipaiyouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/yizhiyouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/tiyuyouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/moniqi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/zhangshangwangyou/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/qitayouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/celueyouxi/");
//        	cacheSet.add("http://www.zhuodown.com/a/youxi/monijingying/");
//        }
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)){
//				page.addTargetRequest(url);
//			}
//		}

	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.zhuodown\\.com/a/yingyongruanjian/.+/[0-9]+/[0-9]+\\.html" ).match()
		  || page.getUrl().regex("http://www\\.zhuodown\\.com/a/youxi/.+/[0-9]+/[0-9]+\\.html" ).match()){

			Apk apk = Zhuodown_Detail.getApkDetail(page);
			
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
	
	private static String getNextUrl(String str){
    	String tmp=null;    	
    	//<a href="list_673_2.html">下一页</a>
		String regex="href=\"([^\"]+)\">下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
}
