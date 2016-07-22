package com.github.muyundefeng.utils;

public class News {

	public String NewsTitle;
	public String NewsAuthor;
	public String NewsDate;
	public String newsContent;
	public String newsDetail;
	
	public News(String newsTitle, String newsAuthor, String newsDate, String newsContent, String newsDetail) {
		super();
		NewsTitle = newsTitle;
		NewsAuthor = newsAuthor;
		NewsDate = newsDate;
		this.newsContent = newsContent;
		this.newsDetail = newsDetail;
	}
	
	public String getNewsTitle() {
		return NewsTitle;
	}
	
	public void setNewsTitle(String newsTitle) {
		NewsTitle = newsTitle;
	}
	
	public String getNewsAuthor() {
		return NewsAuthor;
	}
	
	public void setNewsAuthor(String newsAuthor) {
		NewsAuthor = newsAuthor;
	}
	
	public String getNewsDate() {
		return NewsDate;
	}
	
	public void setNewsDate(String newsDate) {
		NewsDate = newsDate;
	}
	
	public String getNewsContent() {
		return newsContent;
	}
	
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	
	public String getNewsDetail() {
		return newsDetail;
	}
	
	public void setNewsDetail(String newsDetail) {
		this.newsDetail = newsDetail;
	}
	
	
}
