package com.appCrawler.pagePro.fullstack;



import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.appCrawler.pagePro.apkDetails.Gameoao_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 友游  http://www.gameoao.com/index.html
 * Gameoao #306
 * @author tianlei
 */


public class Gameoao implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		if("http://www.gameoao.com/index.html".equals(page.getUrl().toString())){
			cacheSet.add("http://www.gameoao.com/index.php?m=PcWeb&c=Game&a=index");
		}else{
			List<String> urlList=page.getHtml().xpath("//div[@class='game game-main android container clearfix']/div[1]/div/div[2]/div/div///a/@href").all();
			cacheSet.addAll(urlList);		
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				System.out.println(url);
				page.addTargetRequest(url);
			}
		}
		
		//提取页面信息
		if(page.getUrl().toString().contains("detail")){
			Apk apk = Gameoao_Detail.getApkDetail(page);	
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
