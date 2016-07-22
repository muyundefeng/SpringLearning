package com.github.muyundfeng.pageProcessor.impl;

import java.util.List;

import com.github.muyundefeng.pageProcess.PageProcessor;
import com.github.muyundefeng.utils.News;
import com.github.muyundefeng.utils.Page;
//对每个网站进行相关的爬虫处理
public class SinaExtra implements PageProcessor{

	@Override
	public News pageProcess(Page page) {
		// TODO Auto-generated method stub
		
		List<String> newsUrls = page.getHtml().links();
		System.out.println(newsUrls);
		page.addTargetStrings(newsUrls);
		//News news = null;
		if(page.getUrl().startsWith("http://news.sina.com.cn/c/"))
		{
			//System.out.println(page.getHtml());
			String newsTitle = null;
			String newsAuthor = null;
			String newsDate = null;
			String newsContent = null;
			String newsDetail = null;
			//System.out.println(page.getHtml());
			
			newsTitle = page.getHtml().xpath("//h1[@id ='artibodyTitle']/text()").toString();
			System.out.println(newsTitle);
			//System.out.println("start herere");
	
			newsAuthor = page.getHtml().xpath("//span[@data-sudaclick='media_name']/text()").toString();
			System.out.println(newsAuthor);
			newsDate = page.getHtml().xpath("//span[@id='navtimeSource']/text()").toString();
			System.out.println(newsDate);
			newsContent = page.getHtml().xpath("//div[@id='artibody']").toString();
			System.out.println(newsContent);
			newsContent = getText(newsContent);
			newsDetail = page.getUrl();
			System.out.println("News title:"+newsDetail);
			System.out.println("News title:"+newsContent);
			System.out.println("News title:"+newsDate);
			System.out.println("News title:"+newsAuthor);
			System.out.println("News title:"+newsTitle);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
