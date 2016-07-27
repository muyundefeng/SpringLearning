package com.github.muyundfeng.pageProcessor.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.github.muyundefeng.pageProcess.PageProcessor;
import com.github.muyundefeng.utils.News;
import com.github.muyundefeng.utils.Page;
//对每个网站进行相关的爬虫处理
public class SinaExtra implements PageProcessor{
private Logger logger = LoggerFactory.getLogger(SinaExtra.class);
	@Override
	public News pageProcess(Page page) {
		// TODO Auto-generated method stub
		try{
			List<String> newsUrls = page.getHtml().links();
			//System.out.println(newsUrls);
			//System.out.println(page.getHtml());
			for(String str:newsUrls)
			{
				if(str.startsWith("http://news.sina.com.cn/c/")&&!str.contains("doc-ifxuhukv7384297")
						&&str.contains("2016-07"))
						page.addTargetString(str);
			}
			//News news = null;
		}catch(Exception e){
			logger.error(e.toString());
		}
		if(page.getUrl().startsWith("http://news.sina.com.cn/c/"))
		{
			//System.out.println(page.getHtml());
			String newsTitle = null;
			String newsAuthor = null;
			String newsDate = null;
			String newsContent = null;
			String newsDetail = null;
			//System.out.println(page.getHtml());
			
			
			//System.out.println(newsTitle);
			try{
				newsTitle = page.getHtml().xpath("//h1[@id ='artibodyTitle']/text()").toString();
				newsAuthor = page.getHtml().xpath("//span[@id='navtimeSource']/span/text()").toString();
			
				//System.out.println(newsAuthor);
				newsDate = page.getHtml().xpath("//span[@id='navtimeSource']/text()").toString();
				newsContent = page.getHtml().xpath("//div[@id='artibody']").toString();
				newsContent = getText(newsContent);
				if(newsContent.contains("video")){
					newsTitle = null;
				}
			}
			catch(Exception e){
				logger.error(e.toString());
			}
			newsDetail = page.getUrl();
//			System.out.println("newsDetail:"+newsDetail);
//			System.out.println("newsContent:"+newsContent);
//			System.out.println("newsDate:"+newsDate);
//			System.out.println("newsAuthor:"+newsAuthor);
//			System.out.println("newsTitle:"+newsTitle);
			if(newsTitle!=null&&newsContent!=null)
			{
				News news = new News(newsTitle, newsAuthor, newsDate, newsContent, newsDetail);
				return news;
			}
		}
		return null;
	}
	public  static String getText(String sourceText){
		String string = "";
		if(sourceText != null)
		{
			string = sourceText.replaceAll("<[^>]*>", "");
			string = string.replaceAll(" ", "");
			return string;
		}
		return null;
	}

}
