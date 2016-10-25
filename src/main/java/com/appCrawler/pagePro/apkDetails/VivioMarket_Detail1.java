package com.appCrawler.pagePro.apkDetails;



import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


import com.appCrawler.pagePro.apkDetails.VivioMarket_Detail2;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;

public class VivioMarket_Detail1{

	
	
//	public static List<Apk> processMulti(Page page) {
//		List<Apk> apks = new LinkedList<Apk>();	
//		// TODO Auto-generated method stub
//		System.out.println("call");
//		List<Apk> newApk=new ArrayList<Apk>();
//		List<Map<String,String>> list=new ArrayList<Map<String,String>>();//用来存储相关的url连接，让其每页显示1500个app,map的key表示相关的id，value表示相关的id的xml文件的url地址
//		for(int i=100;i<114;i++)
//		{
//			//list.add(SinglePageDownloader(""));
//			//向List中添加主要类别的url地址,i表示相关的类别
//			Map<String,String> map=new HashMap<String,String>();
//			//id为131的进行单独处理
//			//每个id添加29页
//			String url="http://appstore.bbk.com/port/packages/?app_version=401&id="+i+"&apps_per_page=1500&page_index=1&order_type=3&model=&imei=&cs=1";
//			try {
//				String xml=SinglePageDownloader.getHtml(url);
//				InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
//				SAXReader saxReader = new SAXReader();
//				Reader read = new InputStreamReader(inputStream,"utf-8");
//				Document document = saxReader.read(read); 
//				//saxReader.read(inputStream).getRootElement();
//				Element root = document.getRootElement();
//				//将解析出来的allresource下的resourceitem放在list中
//				List listPakcage  = root.elements("Package");
//				//创建source存放每一个resourceitem中资源
//				//List<XmlBean> source = new ArrayList<XmlBean>();
//				//将resourceitem中的各项解析出来，通过XmlBean存放到source中
//				for(Iterator I = listPakcage.iterator();I.hasNext();) {
//					Element resourceitem = (Element) I.next();
//					String id = resourceitem.element("id").getText();
//					//xml文档种apk的id
//					Apk apk = VivioMarket_Detail2.getApkDetail(id);
//					newApk.add(apk);
//					apks.add(apk);
//					
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			map.put(""+i, url);
//			list.add(map);
//			
//		}
//		Map<String,String> map1=new HashMap<String,String>();
//		map1.put("131", "http://appstore.bbk.com/port/packages/?app_version=401&id=131&apps_per_page=1500&page_index=1&order_type=3&model=&imei=&cs=1");
//		list.add(map1);
//		for(int i=0;i<list.size()-1;i++)
//		{
//			Map<String,String> mapXml=list.get(i);
//			//得到相关的url地址
//			String url1=mapXml.get(i+100+"");
//			try {
//				//XmlParse.Prse(SinglePageDownloader2.getHtml(url1));
//				String xml=SinglePageDownloader.getHtml(url1);
//				InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
//				SAXReader saxReader = new SAXReader();
//				Reader read = new InputStreamReader(inputStream,"utf-8");
//				Document document = saxReader.read(read); 
//				//saxReader.read(inputStream).getRootElement();
//				Element root = document.getRootElement();
//				//将解析出来的allresource下的resourceitem放在list中
//				List listPakcage  = root.elements("Package");
//				//创建source存放每一个resourceitem中资源
//				//List<XmlBean> source = new ArrayList<XmlBean>();
//				//将resourceitem中的各项解析出来，通过XmlBean存放到source中
//				for(Iterator I = listPakcage.iterator();I.hasNext();) {
//					Element resourceitem = (Element) I.next();
//					String id = resourceitem.element("id").getText();
//					//xml文档种apk的id
//					Apk apk = VivioMarket_Detail2.getApkDetail(id);
//					apks.add(apk);
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		Map<String,String> mapXml=list.get(list.size()-1);
//		//得到相关的url地址
//		String url1=mapXml.get("131");
//		try {
//			//XmlParse.Prse(SinglePageDownloader2.getHtml(url1));
//			String xml=SinglePageDownloader.getHtml(url1);
//			InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
//			SAXReader saxReader = new SAXReader();
//			Reader read = new InputStreamReader(inputStream,"utf-8");
//			Document document = saxReader.read(read); 
//			//saxReader.read(inputStream).getRootElement();
//			Element root = document.getRootElement();
//			//将解析出来的allresource下的resourceitem放在list中
//			List listPakcage  = root.elements("Package");
//			//创建source存放每一个resourceitem中资源
//			//List<XmlBean> source = new ArrayList<XmlBean>();
//			//将resourceitem中的各项解析出来，通过XmlBean存放到source中
//			for(Iterator I = listPakcage.iterator();I.hasNext();) {
//				Element resourceitem = (Element) I.next();
//				String id = resourceitem.element("id").getText();
//				//xml文档种apk的id
//				Apk apk = VivioMarket_Detail2.getApkDetail(id);
//				newApk.add(apk);
//				page.putField("apk", apk);
//				if(page.getResultItems().get("apk") == null){
//					page.setSkip(true);
//					}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return apks;
//	}

}
