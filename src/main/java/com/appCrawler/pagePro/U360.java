package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.U360_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 360游戏大厅
 * 网站主页：http://ku.u.360.cn/
 * Aawap #514
 * @author lisheng
 */
public class U360 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://u\\.360\\.cn/search/search/.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='serlists']").all();
			for(String str:apps)
			{
				if(!str.contains("http://api.np.mobilem.360.cn/")&&!str.contains(".apk"))
				{
					page.addTargetRequest(str);
				}
			}
		}
		if(page.getUrl().regex("http://ku\\.u\\.360\\.cn/detail\\.php.*").match()
				||page.getUrl().regex("http://u\\.360\\.cn/.*").match())
			{
				return U360_Detail.getApkDetail(page);
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
