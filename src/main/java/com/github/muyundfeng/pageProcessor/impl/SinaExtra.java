package com.github.muyundfeng.pageProcessor.impl;

import com.github.muyundefeng.pageProcess.PageProcessor;
import com.github.muyundefeng.utils.News;
import com.github.muyundefeng.utils.Page;
//对每个网站进行相关的爬虫处理
public class SinaExtra implements PageProcessor{

	@Override
	public News pageProcess(Page page) {
		// TODO Auto-generated method stub
		News news = null;
		page.getHtml().xpath().toString();
		return news;
	}

}
