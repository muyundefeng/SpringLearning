package us.codecraft.webmagic;

import com.appCrawler.increment.DBUtil.DBUtil;
import com.appCrawler.utils.FullStackFileReader;
import com.appCrawler.utils.HttpSendData;
import com.appCrawler.utils.JsonUtils;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;
import us.codecraft.webmagic.selector.thread.CountableThreadPool;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Entrance of a crawler.<br>
 * A spider contains four modules: Downloader, Scheduler, PageProcessor and
 * Pipeline.<br>
 * Every module is a field of Spider. <br>
 * The modules are defined in interface. <br>
 * You can customize a spider with various implementations of them. <br>
 * Examples: <br>
 * <br>
 * A simple crawler: <br>
 * Spider.create(new SimplePageProcessor("http://my.oschina.net/",
 * "http://my.oschina.net/*blog/*")).run();<br>
 * <br>
 * Store results to files by FilePipeline: <br>
 * Spider.create(new SimplePageProcessor("http://my.oschina.net/",
 * "http://my.oschina.net/*blog/*")) <br>
 * .pipeline(new FilePipeline("/data/temp/webmagic/")).run(); <br>
 * <br>
 * Use FileCacheQueueScheduler to store urls and cursor in files, so that a
 * Spider can resume the status when shutdown. <br>
 * Spider.create(new SimplePageProcessor("http://my.oschina.net/",
 * "http://my.oschina.net/*blog/*")) <br>
 * .scheduler(new FileCacheQueueScheduler("/data/temp/webmagic/cache/")).run(); <br>
 *
 * @author code4crafter@gmail.com <br>
 * @see Downloader
 * @see Scheduler
 * @see PageProcessor
 * @see Pipeline
 * @since 0.1.0
 */
public class FullStackSpider implements Runnable, Task {

    protected Downloader downloader;

    protected List<Pipeline> pipelines = new ArrayList<Pipeline>();

    protected PageProcessor pageProcessor;

    protected List<Request> startRequests;

    protected Site site;

    protected String uuid;

    protected QueueScheduler scheduler = new QueueScheduler();//内部主要是使用的是BlockingQueue阻塞队列

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected CountableThreadPool threadPool;

    protected ExecutorService executorService;//线程池

    protected int threadNum = 1;//设置下载线程数目

    protected AtomicInteger stat = new AtomicInteger(STAT_INIT);//线程安全的

    protected boolean exitWhenComplete = true;

    protected final static int STAT_INIT = 0;

    protected final static int STAT_RUNNING = 1;

    protected final static int STAT_STOPPED = 2;

    protected boolean spawnUrl = true;

    protected boolean destroyWhenExit = true;

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private List<SpiderListener> spiderListeners;

    private final AtomicLong pageCount = new AtomicLong(0);//访问的url链接计数器

    private Date startTime;

    private int emptySleepTime = 30000;
    
    private String taskId = null;
    
    
    /**
     * container for app information
     */
    private BlockingQueue<Apk> appsInfo;
    
    private String remoteIp = "127.0.0.1";
    
    /**
     * channel id
     */
    private String channelId;

    /**
     * create a spider with pageProcessor.
     *
     * @param pageProcessor
     * @return new spider
     * @see PageProcessor
     */
    public static FullStackSpider create(PageProcessor pageProcessor) {
        return new FullStackSpider(pageProcessor);
    }

    public FullStackSpider() {
    	
    }
    /**
     * create a spider with pageProcessor.
     *
     * @param pageProcessor
     */
    public FullStackSpider(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
        this.startRequests = pageProcessor.getSite().getStartRequests();
    }
    
    public FullStackSpider(PageProcessor pageProcessor , String taskId) {
    	this(pageProcessor);
    	this.taskId = taskId;
    }
    
    public FullStackSpider(PageProcessor pageProcessor , String taskId , String channelId) {
    	this(pageProcessor , taskId);
    	this.channelId = channelId;
    }
    public FullStackSpider(PageProcessor pageProcessor , String taskId , String channelId , String remoteAddress) {
    	this(pageProcessor , taskId , channelId);
    	this.remoteIp = remoteAddress;
        restoreData();
    }

    /**
     * Set startUrls of Spider.<br>
     * Prior to startUrls of Site.
     *
     * @param startUrls
     * @return this
     */
    public FullStackSpider startUrls(List<String> startUrls) {
        checkIfRunning();
        this.startRequests = UrlUtils.convertToRequests(startUrls);
        return this;
    }

    /**
     * Set startUrls of Spider.<br>
     * Prior to startUrls of Site.
     *
     * @param startRequests
     * @return this
     */
    public FullStackSpider startRequest(List<Request> startRequests) {
        checkIfRunning();
        this.startRequests = startRequests;
        return this;
    }

    /**
     * Set an uuid for spider.<br>
     * Default uuid is domain of site.<br>
     *
     * @param uuid
     * @return this
     */
    public FullStackSpider setUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * set scheduler for Spider
     *
     * @param scheduler
     * @return this
     * @Deprecated
     * @see #setScheduler(us.codecraft.webmagic.scheduler.Scheduler)
     */
    public FullStackSpider scheduler(QueueScheduler scheduler) {
        return setScheduler(scheduler);
    }

    /**
     * set scheduler for Spider
     *
     * @param scheduler
     * @return this
     * @see Scheduler
     * @since 0.2.1
     */
    public FullStackSpider setScheduler(QueueScheduler scheduler) {
        checkIfRunning();
        Scheduler oldScheduler = this.scheduler;
        this.scheduler = scheduler;
        if (oldScheduler != null) {
            Request request;
            while ((request = oldScheduler.poll(this)) != null) {
                this.scheduler.push(request, this,false);
            }
        }
        return this;
    }

    /**
     * add a pipeline for Spider
     *
     * @param pipeline
     * @return this
     * @see #addPipeline(us.codecraft.webmagic.pipeline.Pipeline)
     * @deprecated
     */
    public FullStackSpider pipeline(Pipeline pipeline) {
        return addPipeline(pipeline);
    }

    /**
     * add a pipeline for Spider
     *
     * @param pipeline
     * @return this
     * @see Pipeline
     * @since 0.2.1
     */
    public FullStackSpider addPipeline(Pipeline pipeline) {
        checkIfRunning();
        this.pipelines.add(pipeline);//关于设置相关的链表（不采用变量）主要原因是网络下载信息的速度如果大于信息存储到磁盘的速度，
        //就会淹没原来的信息
        return this;
    }

    /**
     * set pipelines for Spider
     *
     * @param pipelines
     * @return this
     * @see Pipeline
     * @since 0.4.1
     */
    public FullStackSpider setPipelines(List<Pipeline> pipelines) {
        checkIfRunning();
        this.pipelines = pipelines;
        return this;
    }

    /**
     * clear the pipelines set
     *
     * @return this
     */
    public FullStackSpider clearPipeline() {
        pipelines = new ArrayList<Pipeline>();
        return this;
    }

    /**
     * set the downloader of spider
     *
     * @param downloader
     * @return this
     * @see #setDownloader(us.codecraft.webmagic.downloader.Downloader)
     * @deprecated
     */
    public FullStackSpider downloader(Downloader downloader) {
        return setDownloader(downloader);
    }

    /**
     * set the downloader of spider
     *
     * @param downloader
     * @return this
     * @see Downloader
     */
    public FullStackSpider setDownloader(Downloader downloader) {
        checkIfRunning();
        this.downloader = downloader;
        return this;
    }

    protected void initComponent() {
        if (downloader == null) {
            this.downloader = new HttpClientDownloader();//默认使用的Downloader
        }
        if (pipelines.isEmpty()) {
            pipelines.add(new ConsolePipeline());//不会在控制台输出，因为list链表中存放有FilePipe
        }
        downloader.setThread(threadNum);//pageDownloader//还存在疑问???
        if (threadPool == null || threadPool.isShutdown())//分配线程池
        {
            if (executorService != null && !executorService.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum, executorService);
            } else {
                threadPool = new CountableThreadPool(threadNum);
            }
        }
        if (startRequests != null) {
        	//this.startRequests = pageProcessor.getSite().getStartRequests();
            for (Request request : startRequests) {
                scheduler.push(request, this, false);//public void push(Request request, Task task);
            }
            startRequests.clear();
        }
        startTime = new Date();
    }

    //@Override
    public void run() {
        checkRunningStat();
        initComponent();//初始化相关组件信息
        logger.info("FullStackSpider " + getUUID() + " started!");
//        Request request1 = scheduler.poll(this);
//        System.out.println("acccess url is ="+ request1.getUrl());
    
        while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
            Request request = scheduler.poll(this);
            if (request == null) {
                if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                    break;
                }
                // wait until new url added
                waitNewUrl();
            } else {
                final Request requestFinal = request;
                threadPool.execute(new Runnable()//多个线程共享区域，需要进行相关的互斥访问工作
                {
                    public void run() {
                        try {
                            processRequest(requestFinal);
                            
                            onSuccess(requestFinal);
                        } catch (Exception e) {
                            onError(requestFinal);
                            logger.error("process request " + requestFinal + " error", e);
                        } finally {
                            if (site.getHttpProxyPool().isEnable()) {
                                site.returnHttpProxyToPool((HttpHost) requestFinal.getExtra(Request.PROXY), (Integer) requestFinal
                                        .getExtra(Request.STATUS_CODE));
                            }
                            pageCount.incrementAndGet();
                            signalNewUrl();//当前线程发现新的url链接，需要其他线程进行相关的处理，该线程唤醒其他等待的线程，因为该线程在访问详情页面的时候会获取相关的url链接，此时不为空，需要唤醒等待的线程
                        }
                    }
                });
            }
        }
        stat.set(STAT_STOPPED);
        logger.info("The full crawler has ended.");
        //本地爬取不需要发送数据
//        try {
////			sendData();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        // release some resources
        if (destroyWhenExit) {
            close();
        }
    }

    protected void onError(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onError(request);
            }
        }
    }

    protected void onSuccess(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onSuccess(request);
            }
        }
        //将访问过的url存放到数据库中
        logger.info("acessed url is "+request.getUrl().toString());
       DBUtil.saveAccessedUrlDB(request.getUrl().toString());
    }

    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("FullStackSpider is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }

    public void close() {
        destroyEach(downloader);
        destroyEach(pageProcessor);
        for (Pipeline pipeline : pipelines) {
            destroyEach(pipeline);
        }
        threadPool.shutdown();
    }
    public void sendData() throws IOException{
    	logger.info("FullStackSpider sendData()");
    	String header = "";
    	//taskId = "task1";
    	//channelId = "168";
    	//taskId = "52c265e8139c3c497abd8f9e739193c746fbf999e56df8cc0c63d59862d4cf5brrrrr";
    	//channelId = "168";
    	String path = PropertiesUtil.getCrawlerDataPath();
    	FullStackFileReader fileReader = new FullStackFileReader(path + taskId + "_" + channelId + ".txt");
    	logger.info("send data from file:" + path + taskId + "_" + channelId + ".txt");
    	HttpSendData httpSendData = new HttpSendData();
    	int rowCounter = 0;
    	while(fileReader.isHasMore()){
        	String appsInfo = fileReader.getAppsInfoFromFile();
        	String agentId = null;
        	String toSendData = JsonUtils.getSenderData(agentId , channelId , header , "4" , appsInfo);
        	
        	logger.info("FullStackSpider sendData() rowCounter:" + rowCounter++);
        	httpSendData.sendData(remoteIp , taskId , toSendData);
        	try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            }
    	}
    	httpSendData.sendData(remoteIp , taskId , "Searcher sendData end");
    	logger.info("Total sendData count:" + fileReader.getApkCounter());

    }
    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Process specific urls without url discovering.
     *
     * @param urls urls to process
     */
    public void test(String... urls) {
        initComponent();
        if (urls.length > 0) {
            for (String url : urls) {
                processRequest(new Request(url));
            }
        }
    }

    protected void processRequest(Request request)//下载一个页面
    {
    	System.out.println("url:"+request.getUrl());
    	
    	
        Page page = downloader.download(request, this);
        if (page == null) {
            sleep(site.getSleepTime());
            onError(request);
            return;
        }
        // for cycle retry
        if (page.isNeedCycleRetry()) {
            extractAndAddRequests(page, true);
            sleep(site.getSleepTime());
            return;
        }
        pageProcessor.process(page);//调用已经实现的每个网站的处理类，在进行每个页面
        //处理的时候，在每个页面中发现新的连接，并将连接放入到page中
        
//  Apk apk = pageProcessor.process(page);
//        
//        if(apk != null){
//        	//apk.setChannelId(channelId);
//        	if(!appsInfo.contains(apk)){
//        		appsInfo.add(apk);//add apk to the appsInfo
//        	}
//        	
//        }else{
//        	List<Apk> apks = pageProcessor.processMulti(page);
//        	if(apks != null){
//        		for(Apk curApk:apks){
//        			if(curApk.getAppDownloadUrl() != null){
//        				appsInfo.add(curApk);
//        			}
//        		}
//        		//appsInfo.addAll(apks);
//        	}
//        }
        extractAndAddRequests(page, spawnUrl);
        if (!page.getResultItems().isSkip()) {
            for (Pipeline pipeline : pipelines) {
                pipeline.process(page.getResultItems(), this);//将获取的结果信息存入到file文件中
            }
        }
        sleep(site.getSleepTime());
    }

    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            for (Request request : page.getTargetRequests()) {
//            	System.out.println("+++"+request.getUrl().toString());
                addRequest(request);//新发新的url链接也要放入到page中
            }
        }
    }

    private void addRequest(Request request) {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl()));//去除相关的传输协议，设置主机名
        }//site主要存放对一个网站相关信息的设置，包括超时时间的设置以及相关的http请求头部信息的设置，首次调用是空，请求url队列也为空
        //
        scheduler.push(request, this,false);//将request存入到队列中，采用这种数据结构也表明了采用的爬取策略是广度优先遍历
        //在将相关的url链接存放在队列的时候，需要考虑该url链接是否先前已经访问过。，主要使用.BloomFilter工具
        //scheduler.pushWhenNoDuplicate(request, this);
    }

    protected void checkIfRunning() {
        if (stat.get() == STAT_RUNNING) {
            throw new IllegalStateException("FullStackSpider is already running!");
        }
    }

    public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    /**
     * Add urls to crawl. <br/>
     *
     * @param urls
     * @return
     */
    public FullStackSpider addUrl(String... urls)//jdk5的新特性，可以依次输入不定量的参数 
    {
    	if(scheduler.isEmpty()){
	        for (String url : urls) {
	            addRequest(new Request(url));//request对象主要存放爬取链接的url地址
	        }
    	}
        signalNewUrl();
        return this;
    }

    /**
     * Download urls synchronizing.
     *
     * @param urls
     * @return
     */
    public <T> List<T> getAll(Collection<String> urls) {
        destroyWhenExit = false;
        spawnUrl = false;
        startRequests.clear();
        for (Request request : UrlUtils.convertToRequests(urls)) {
            addRequest(request);
        }
        CollectorPipeline collectorPipeline = getCollectorPipeline();
        pipelines.add(collectorPipeline);
        run();
        spawnUrl = true;
        destroyWhenExit = true;
        return collectorPipeline.getCollected();
    }

    protected CollectorPipeline getCollectorPipeline() {
        return new ResultItemsCollectorPipeline();
    }

    public <T> T get(String url) {
        List<String> urls = Lists.newArrayList(url);
        List<T> resultItemses = getAll(urls);
        if (resultItemses != null && resultItemses.size() > 0) {
            return resultItemses.get(0);
        } else {
            return null;
        }
    }

    /**
     * Add urls with information to crawl.<br/>
     *
     * @param requests
     * @return
     */
    public FullStackSpider addRequest(Request... requests) {
        for (Request request : requests) {
            addRequest(request);
        }
        signalNewUrl();
        return this;
    }

    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            //double check
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.warn("waitNewUrl - interrupted, error {}", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    private void signalNewUrl() {
        try {
            newUrlLock.lock();
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

    public void start() {
        runAsync();
    }

    public void stop() {
        if (stat.compareAndSet(STAT_RUNNING, STAT_STOPPED)) {
            logger.info("FullStackSpider " + getUUID() + " stop success!");
        } else {
            logger.info("FullStackSpider " + getUUID() + " stop fail!");
        }
    }

    /**
     * start with more than one threads
     *
     * @param threadNum
     * @return this
     */
    public FullStackSpider thread(int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        return this;
    }

    /**
     * start with more than one threads
     *
     * @param threadNum
     * @return this
     */
    public FullStackSpider thread(ExecutorService executorService, int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        return this;
    }

    public boolean isExitWhenComplete() {
        return exitWhenComplete;
    }

    /**
     * Exit when complete. <br/>
     * True: exit when all url of the site is downloaded. <br/>
     * False: not exit until call stop() manually.<br/>
     *
     * @param exitWhenComplete
     * @return
     */
    public FullStackSpider setExitWhenComplete(boolean exitWhenComplete) {
        this.exitWhenComplete = exitWhenComplete;
        return this;
    }

    public boolean isSpawnUrl() {
        return spawnUrl;
    }

    /**
     * Get page count downloaded by spider.
     *
     * @return total downloaded page count
     * @since 0.4.1
     */
    public long getPageCount() {
        return pageCount.get();
    }

    /**
     * Get running status by spider.
     *
     * @return running status
     * @see Status
     * @since 0.4.1
     */
    public Status getStatus() {
        return Status.fromValue(stat.get());
    }


    public enum Status {
        Init(0), Running(1), Stopped(2);

        private Status(int value) {
            this.value = value;
        }

        private int value;

        int getValue() {
            return value;
        }

        public static Status fromValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            //default value
            return Init;
        }
    }

    /**
     * Get thread count which is running
     *
     * @return thread count which is running
     * @since 0.4.1
     */
    public int getThreadAlive() {
        if (threadPool == null) {
            return 0;
        }
        return threadPool.getThreadAlive();
    }

    /**
     * Whether add urls extracted to download.<br>
     * Add urls to download when it is true, and just download seed urls when it is false. <br>
     * DO NOT set it unless you know what it means!
     *
     * @param spawnUrl
     * @return
     * @since 0.4.0
     */
    public FullStackSpider setSpawnUrl(boolean spawnUrl) {
        this.spawnUrl = spawnUrl;
        return this;
    }

    //@Override
    public String getUUID() {
        if (uuid != null) {
            return uuid;
        }
        if (site != null) {
            return site.getDomain();
        }
        uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public FullStackSpider setExecutorService(ExecutorService executorService) {
        checkIfRunning();
        this.executorService = executorService;
        return this;
    }

    //@Override
    public Site getSite() {
        return site;
    }

    public List<SpiderListener> getSpiderListeners() {
        return spiderListeners;
    }

    public FullStackSpider setSpiderListeners(List<SpiderListener> spiderListeners) {
        this.spiderListeners = spiderListeners;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Set wait time when no url is polled.<br></br>
     *
     * @param emptySleepTime In MILLISECONDS.
     */
    public void setEmptySleepTime(int emptySleepTime) {
        this.emptySleepTime = emptySleepTime;
    }
    
    /**
     * 还原数据库中的数据
     */
    public void restoreData(){
    	
    	scheduler.cleanSet(this);
    	List<String> accessedUrls = DBUtil.restoreAccessedUrl();
    	List<String> accessToUrls = DBUtil.readToAccessUrl();
    	boolean flag = true;
    	if(accessedUrls.size()>0)
    			if(accessedUrls.size()==accessToUrls.size()){
		    		for(int i=0;i<accessedUrls.size();i++){
		    			String url = accessedUrls.get(i);
		    			String urls = accessToUrls.get(i);
		    			if(!url.equals(urls)){
		    				flag = false;
		    				break;
		    			}
		    		}
    	}
    	else {
    		flag = false;
		}
    	if(flag == true)
    		return;
//    	accessedUrls.remove("http://shouji.baidu.com/");
    	System.out.println("accessedUrls:"+accessedUrls);
//    	System.out.println("accessedUrls:"+request1.getUrl());
    	for(String str:accessedUrls){
    		Request request = new Request();
    		request.setUrl(str);
    		scheduler.push(request, this,true);
    	}
    	scheduler.cleanDB(this);//清空scheduler链表
//    	
    	
    	System.out.println("to accessUrls:"+accessToUrls);
    	if(accessToUrls!=null)
	    	for(String str:accessToUrls){
	    		Request request = new Request();
	    		request.setUrl(str);
	    		scheduler.push(request, this,false);
	    	}
//    	scheduler.push(request1, this);
    	System.out.println(scheduler);
    }
    
    public static void main(String[] args) throws IOException{
    	FullStackSpider spider = new FullStackSpider();
    	spider.sendData();
    }
}
