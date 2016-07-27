package com.github.muyundfeng.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	//private Condition singalCondition = lock.newCondition();
	private static int  timeout = 5;
	private static boolean completeExit = false;
	
	private boolean stopFlag = false;
	private boolean success;
	private Request request;//起始url地址
	private static int threadNumber = 2; 
	private final myThreadPool threadPool;
	//private ExecutorService exec = Executors.newFixedThreadPool(10);//创建具有十个线程的线程池
	private BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();
	private List<News> list = new ArrayList<News>();
	private PageProcessor processor;
	private AtomicInteger count = new AtomicInteger();
    private Logger logger = LoggerFactory.getLogger(getClass());

	
	public Spidder(String id) {
		// TODO Auto-generated constructor stub
		this.siteId = id;
		this.initSetting = new readXml();
		this.success = false;
		this.threadPool = new myThreadPool(threadNumber, threadNumber);
	}
	
	public void initComponent(){
		initSetting.setId(siteId);
		String homepage = initSetting.getHomePage();
		request = new Request(homepage);
		charSet = initSetting.getCharset();
		downloader = new myHttpClientDownloader(charSet);
		processor = initSetting.getProcessor();
		count.set(0);
	}
	
	public  synchronized boolean rmDumplicate(Request request){
		boolean flag = rmDumplicate.isRmove(request.getUrl());
		return flag;
		
	}
	//等待线程中获取url的数量
	public  void waitNewUrl(){
		lock.lock();
		try {
			condition.await(timeout,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			completeExit = true;
		}//如果在timeout
		lock.unlock();
	}
	
	//从队列中取出元素
	public synchronized Request pollFromQueue(){
		Request request = queue.poll();
		return request;	
	}
	
	//将相关的元素存放到队列中去
	public synchronized void pushToQueue(Request request){
		if(rmDumplicate(request)){
			queue.add(request);
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
		logger.info("push request to queue "+request.getUrl());
		pushToQueue(request);
		while(true)
		{
			logger.info("in while loop");
			if(completeExit !=true &&stopFlag!=true ){
				Request request = pollFromQueue();
				if(request == null){
					if(threadPool.getAliveNumber() == 0)
					{
						logger.info("queue size is 0");
						break;
					}
					waitNewUrl();
				}
				else
				{
					logger.info("execute program here");
					logger.info(threadPool.toString());
 					threadPool.execute( new Runnable() {
						@Override
						public void run() {
							
							//Request request = pollFromQueue();
								//Request nextRequest = queue.take();
							logger.info("access url:"+request.getUrl());
							logger.info("process url:"+request.getUrl());
							String responesHtml = downloader.requestUrl(request);
							//System.out.println(responesHtml);
							logger.info("donwloading url :"+request.getUrl());
							//logger.info(responesHtml);
							handle(responesHtml,request.getUrl());
							logger.info("elements in queue is:"+queue.size());
							addCount();
							logger.info("the program execute here");
							logger.info("elements in queue is:"+queue.size());
							signalNewUrl();
						}
					});
					//firstFlag = false;
					logger.info("hello wolrd");
				}
			}
			else{
				break;
			}
			//exec.submit(runnable);
		}
		if(completeExit == true){
			threadPool.shudowm();
		}	
		afterProcess();
	}
	public synchronized void handle(String html,String url){
		Page page = new Page(html, url, null);
		
		News news = processor.pageProcess(page);
		if(news!=null)
		{
			list.add(news);
		}
		System.out.println("start herere");
		List<Request> requests = page.getRequests();
		
		if(requests != null){
			for(Request request:requests){
				pushToQueue(request);
			}
		}
		else
		{
			Request request = page.getRequest();
			if(request != null){
				pushToQueue(request);
			}
			else{
				List<String> addUrls = page.getaddUrls();
				System.out.println(addUrls);
				if(addUrls != null)
				{
					for(String Url:addUrls){
						if(reasonalUrl.isReasonalUrl(url)){
							Request request2 = new Request(Url);
							pushToQueue(request2);
						}
					}
				}
				else{
					List<String> addurls = page.getaddUrl();
					System.out.println("push url to elements "+addurls);
					if(addurls!= null){
						for(String str:addurls)
						{
							Request request2 = new Request(str);
							pushToQueue(request2);
						}
					}
				}
			}
		}
	}
	
	 private void signalNewUrl() {
	        try {
	            lock.lock();
	            condition.signalAll();
	        } finally {
	        	lock.unlock();
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
		logger.info(string);
		while(!list.isEmpty()){
			News news = list.remove(list.size()-1);
			logger.info("News title:"+news.getNewsTitle());
			logger.info("News title:"+news.getNewsDetail());
			logger.info("News title:"+news.getNewsDate());
			logger.info("News title:"+news.getNewsAuthor());
			logger.info("News title:"+news.getNewsContent());
		}
		//com.github.muyundefeng.utils.saveToFile.saveAsFile(string);
	}
	
	//入口程序
	public  void startWebSpider(){
		initComponent();
		this.start();
	}
	//test
	public static void main(String[] args) {
		Spidder spidder = new Spidder("1");
		spidder.startWebSpider();
	}
}
