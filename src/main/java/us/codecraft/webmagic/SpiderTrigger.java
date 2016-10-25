package us.codecraft.webmagic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.controller.ProcessNumber;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.downloader.CurlPostPageDownloader;
import us.codecraft.webmagic.downloader.MyHttpClientDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 爬虫的启动接口，controller模块接受任务后，调用SpiderTrigger启动爬虫
 * 
 * spiderrTrigger主要是对网站保存在xml文件中的信息进行解析处理，处理property文件，
 * 获得页面处理的主要类，spider是整个爬虫的框架核心，将相关的页面处理类，渠道编号以及，任务编号传递给spider主要类
 * @author buildhappy
 *
 */
public class SpiderTrigger implements Runnable {
	private String channelId;
	private String taskId;
	private String keyword;
	private String remoteIp;
//	private Logger logger = LoggerFactory.getLogger(getClass());
	private Logger logger = null;
	static{
        System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        System.setProperty("http.proxyPort", "8001");
        System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	/**
	 * 
	 * @param taskId
	 * @param channelId
	 * @param keyword
	 */
	public SpiderTrigger(String taskId , String channelId , String keyword ,String remoteAddress){
	//	System.setProperty("myconfig.filepath",channelId);
		System.setProperty("myconfig.filename","error_jar"+channelId+".log");
		this.logger = LoggerFactory.getLogger(getClass());
		logger.info("SpiderTrigger Constructor parameter: taskId,channelId,keyword");
		this.taskId = taskId;
		this.channelId = channelId;
		this.keyword = keyword;
		this.remoteIp = remoteAddress;
		
	}
	
	public SpiderTrigger(String taskId , UserRequest requestData , String remoteAddress){
		logger.info("SpiderTrigger Constructor parameter: taskId,channelId,keyword");
		this.taskId = taskId;
		this.channelId = requestData.getChannelId();
		this.keyword = requestData.getKeyword();
		this.remoteIp = remoteAddress;
	}
	
	public void run() {
		System.out.println("hello world");
		ProcessNumber.modifyProcessNum("ADD");//用来表示爬虫启动以来执行了多少次任务，开启一次记为一次，并把相关信息存入到txt文件中
		 logger.info("SpiderTrigger run");		
		ChannelsDom channelDom = new ChannelsDom();
		channelDom = channelDom.createChannelsDom(channelId);//解析xml文件中相关信息，包括网站主页，网页编码等基本信息，采用w3cdom，在进行相关xml文件解析的时候，获得相关的
		//相关的处理页面（针对每个网站进行编写的页面），并且动态创建该类
		
		if(channelDom != null){
			String referer;
			referer = channelDom.getHomeUrl();
			String pageEncoding;
			pageEncoding = channelDom.getPageEncoding();
			
			//System.out.println(keyword != null && keyword.length() > 0 && keyword != "");
			//searcher request using the keyword
			if(keyword != null && keyword.length() > 0 && keyword != ""){
				logger.info("searcher request using the keyword");
				String searchUrl;
				PageProcessor pagePro;
				String postParam;
				pagePro = channelDom.getPagePro();
				
				String s = channelDom.getSearchUrl();
				
				
				searchUrl = s.replace("*#*#*#", keyword);
				
				String urlEncoding = null;
				urlEncoding = channelDom.getUrlEncoding();
				
				String encodedKeyword=keyword;
				//根据url编码格式，对原生关键字进行编码处理
				if(urlEncoding != null){
					try {
						encodedKeyword = URLEncoder.encode(keyword, urlEncoding);
					} catch (UnsupportedEncodingException e) {
						logger.error(e.toString());
						//e.printStackTrace();
					}
				}

				searchUrl = s.replace("*#*#*#", encodedKeyword);
				logger.info(searchUrl);
				//searchUrl = searchUrl + keyword;
				
				postParam = channelDom.getPostParam();
				Spider spider = new Spider(pagePro ,taskId ,channelId,remoteIp);
				System.out.println("postParam:" + postParam);
				if (postParam != null) {
					Map<String,Object> extras = new HashMap<String , Object>();
					//postParam=postParam.replace("!%!%!%", "&");
					extras.put(postParam, keyword);
					Request request = new Request();
					request.setExtras(extras);
					request.setMethod("post");
					request.setUrl(searchUrl);
					spider.addRequest(request).setDownloader(new CurlPostPageDownloader(postParam, keyword , referer ,pageEncoding,channelId))
							.thread(1).run();
//					spider.setDownloader(new MyHttpClientDownloader())
//					.addRequest(request).run();
				} else {
					spider.setDownloader(new MyHttpClientDownloader())
							.addUrl(searchUrl).thread(1).run();
				}				
			}//the full stack crawler
			else{
				logger.info("full stack crawler");
				String homeUrl = channelDom.getHomeUrl();
				String[] homeUrls = homeUrl.split(",");
				PageProcessor fullPagePro = channelDom.getFullPagePro();
				
				FullStackSpider fullStackSpider = new FullStackSpider(fullPagePro , taskId , channelId , remoteIp);
				fullStackSpider.setDownloader(new MyHttpClientDownloader()).addUrl(homeUrls)
				.addPipeline(new FilePipeline(taskId , channelId))//设置爬取信息存放
				.thread(10)
				.run();//启动爬虫
			}
		}else{
			//if can not find the channel by the channelId
			logger.error("SpiderTrigger no channel");
		}
		ProcessNumber.modifyProcessNum("MINUS");
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException{

		//dongjinkui:
		//Done:66->ApkGfan 69->Ruan8 71->Apk8 72->Anzhuo 73->Zhuannet 74->Gamersky 75->Pcpop(与144重复)
		//78->Mobyware 80->Srui 84->Duote 85->Mi 87->Anfone 91->Anfensi 92->Hzhuti 88->Meizumi
		//138->Ddxia 142->Skycn 144->PageProPcpop(与75重复) 153->Appdh  124->Huawei(与44重复)
		//107->Apk3 135->Www7xz 173->Eoemarket 119->Mgapp 136->Bkill 128->Oyksoft
		//105->UC 77->Leidian 169->Android173Sy 132->Downbank
		//171->oppo(被封) 76->Gezila(网站挂了) 133->Paojiao
		//106->Muzhiwan 130->Liqucn 127->Anzhi 109->Www2265 173->Eoemarket 67->Fpwap
		//150->PageProDowng 145>PageProSoapp(仅一个应用) 152->PageProWabao(仅两个应用)
		//110->Www25az 86->Nduoa 90->Apk91(被封) 96->Play 93->Sjapk 97->www5577
		//99->Zol 116->Newasp 125->Yruan 121->Www51vapp 149->PageProSjapk(与93重复)
		//158->PageProSjapk(与71重复) 155->PageProItopdog(与81重复) 
		//123->Www77l 98->Sina 174->Www9game 4->PagePro159(共10个应用)
		//81->Itopdog 172->PipaPagePro 134->D
		//111->Vsoyou(获取的apk总量不全，应该自己伪造apk详情页链接) (以上共64个) 

		//todo:103->Diyiapp(暂时不能用)
		
		
		
		//liuxixia:
		//Done:2->PagePro163 50->PagePro265g 25->PagePro155 168->360
		//44->PageProHuawei 35->PagePro7613 63->PageProAouu(仅一个应用) 19->PageProApkyx
		//62->PageProandroidcnmo 21->PageProBaidu 64->PageProBaoRuan(仅一个应用) 37->PageProAnZhuoApk 
		//55->PageProAnqu 46->PageProHiApk 146->PageProIfan178 42->PagePro7xz 161->PageProImobile
		//33->PageProCe1111 22->PageProgfanstore 1->PagePro3533 65->PagePro4355
		//29->PageProSoGou 9->PageProTgbus 52->PageProKaiqi  16->PagePro3987(共12个应用) 
		//34->PageProSlideMe 32->PagePro7230 47->PageProAnruan 58->PagePro97sky
		//160->PageProShuaJi 59->PageProMyapp 14->PageProShouYou 24->PageProPconline 
		//20->PageProLenovomm 156->PagePro17et(仅一个应用) 17->PageProKaixin
		//148->PageProYesky(与163重复) (以上共39个)
		
		//Error:15->PagePro91(被封)，40->PageProWandoujia(一次爬虫很久不能用，但搜索接口可用)
		//5->PageProAppChina(疑似被封,80个应用后页面下载不下了,浏览器打不开)
		//13->PageProCncrk(下载链接有问题下载是exe文件)
		//38->PageProShuiGuo(被封，获取的下载链接有问题，在利用动态页面处理后真正的下载链接还是无法获取) 
		//
		
		//Running:
		//6->PageProMM10086(本机)8->PageProMobilePhone(刘) 
		//31->PageProCr173(服务器) 27->PagePro520apk(redmine总量不够，暂时打不开)
		
		//Todo:
		//41->PageProAngeeks 10->PageProAnzow 60->PageProApkye
		//7->PageProSoHu 
		
		//36->PagePro958Shop(可用，与166重复) 166->PageProD958shop(与36重复，不可用)
		
		//error:
		//18->PageProDownza 
		//30->PageProAnGuo(apk文件保存在百度网盘)
		//12->PageProGameDog(总量不全) 53->PageProMz6(总量不全)
		//23->PageProOnlineDown(没有简介) 51->PageProPc6(http://www.pc6.com/ku/molibaobei/类似页面没有获取) 
		//162->PageProNgDCn(获取3000多个，总量不全)
		//49->PageProSinaTech(没有爬虫结果)
//		
//		for(int i=0;i<20;i++){
//			System.out.println(SinglePageDownloader.getHtml("http://www.whereismyip.com/"));
//		}
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
//		map.put("Content-Encoding", "identity");
//		map.put("Content-Type", "text/html");
//		map.put("Accept-Charset", "UTF-8");
//		
//		
//		
//		System.out.println(Html.create(SinglePageDownloader.getHtml("http://app.suning.com/android/app/page?pack=com.baidu.BaiduMap","GET",null)));
		
		SpiderTrigger spiderTrigger = new SpiderTrigger("task1" , "21", "" , "");//工商银行  捕鱼达人 邮政银行
		Thread thread = new Thread(spiderTrigger);
		thread.run();
		
		//提取sitesInfo.xml中的数据
		
//		File file = new File("data.txt");
//		FileWriter writer = new FileWriter(file);
//		int counter = 0;
//		for(int i = 1; i < 200; i++){
//			ChannelsDom channelDom = new ChannelsDom();
//			try{
//				channelDom = channelDom.createChannelsDom(i + "");
//				if(channelDom != null){
//					String name;
//					name = channelDom.getName();
//					String referer;
//					referer = channelDom.getHomeUrl();
//					String id = channelDom.getChannelId();
//					String pagePro = channelDom.getFullPagePro().toString();
//					//System.out.println(i + ":" + name + ":" + referer);
//					//writer.write(id + ":" + name + ":" + referer + "\n");
//					writer.write(id + "\t" + referer + "\t" + name + "\n");
//					writer.flush();
//					counter++;
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//
//		}
//		writer.close();
//		System.out.println(counter);
		
	}

}


