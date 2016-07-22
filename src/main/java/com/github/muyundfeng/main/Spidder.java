package com.github.muyundfeng.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.muyundefeng.downloader.Downloader;
import com.github.muyundefeng.downloader.myHttpClientDownloader;
import com.github.muyundefeng.pageProcess.PageProcessor;
import com.github.muyundefeng.utils.News;
import com.github.muyundefeng.utils.Page;
import com.github.muyundefeng.utils.Request;
import com.github.muyundefeng.utils.objectToJson;
import com.github.muyundefeng.utils.readXml;
import com.github.muyundefeng.utils.reasonalUrl;
/*
 * 网络爬虫的入口程序
 */
import com.github.muyundefeng.utils.rmDumplicate;

public class Spidder extends Thread {

	private readXml initSetting;
	private Downloader downloader;//网页下载器
	private String charSet;
	private String siteId;
	private static final String STATE_RUNNING = "running";
	private static final String STATE_STOPPED = "Stopped";
	private String state;
	private boolean stopFlag = false;
	private boolean success;
	private Request request;//起始url地址
	private ExecutorService exec = Executors.newFixedThreadPool(10);//创建具有十个线程的线程池
	private BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();
	private List<News> list = new ArrayList<News>();
	private PageProcessor processor;
	private AtomicInteger count;
    private Logger logger = LoggerFactory.getLogger(getClass());

	
	public Spidder(String id) {
		// TODO Auto-generated constructor stub
		this.siteId = id;
		this.state = STATE_RUNNING;
		this.initSetting = new readXml();
		this.success = false;
	}
	
	public void initCAomponent(){
		initSetting.setId(siteId);
		String homepage = initSetting.getHomePage();
		request = new Request(homepage);
		charSet = initSetting.getCharset();
		downloader = new myHttpClientDownloader(charSet);
		processor = initSetting.getProcessor();
		count.set(0);
	}
	
	public  synchronized void rmDumplicate(Request request){
		boolean flag = rmDumplicate.isRmove(request.getUrl());
		if(flag)
			try {
				queue.put(request);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
			//do nothing
		}
		
	}
	
	public synchronized void addCount(){
		if(success)
			count.incrementAndGet();
		else {
			//do nothing
		}
	}
	//启动爬虫
	public void run(){
		rmDumplicate(request);
		while(true){
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						if(!currentThread().isInterrupted()&&state == STATE_RUNNING){
							if(!queue.isEmpty())
							{
								Request request = queue.take();
								logger.info("process url:"+request.getUrl());
								String responesHtml = downloader.requestUrl(request);
								handle(responesHtml,request.getUrl());
								addCount();
							}
						}
						else{
							stopFlag = true;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			};
			exec.submit(runnable);
			if(stopFlag == true){
				exec.shutdown();
				break;
			}
			afterProcess();
		}
	}
	public synchronized void handle(String html,String url){
		Page page = new Page(html, url, null);
		News news = processor.pageProcess(page);
		list.add(news);
		List<Request> requests = page.getRequests();
		if(requests != null){
			for(Request request:requests){
				rmDumplicate(request);
			}
		}
		else
		{
			Request request = page.getRequest();
			if(request != null){
				rmDumplicate(request);
			}
			else{
				List<String> addUrls = page.getaddUrls();
				if(addUrls != null)
				{
					for(String Url:addUrls){
						if(reasonalUrl.isReasonalUrl(url)){
							Request request2 = new Request(Url);
							rmDumplicate(request2);
						}
					}
				}
				else{
					String addurl = page.getaddUrl();
					if(addurl!= null){
						Request request2 = new Request(addurl);
						rmDumplicate(request2);
					}
				}
			}
		}
	}
	
	public void afterProcess(){
		if(list==null){
			logger.info("there is no news");
		}
		else{
			saveToFile();
		}
	}
	
	public void saveToFile(){
		String string = objectToJson.jsonStr(list);
		while(!list.isEmpty()){
			News news = list.remove(list.size()-1);
			logger.info("News title:"+news.getNewsTitle());
			logger.info("News title:"+news.getNewsDetail());
			logger.info("News title:"+news.getNewsDate());
			logger.info("News title:"+news.getNewsAuthor());
			logger.info("News title:"+news.getNewsContent());
		}
		com.github.muyundefeng.utils.saveToFile.saveAsFile(string);
	}
}
