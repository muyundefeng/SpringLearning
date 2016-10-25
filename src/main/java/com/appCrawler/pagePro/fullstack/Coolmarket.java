package com.appCrawler.pagePro.fullstack;
/**
 * 酷派coolmarket http://www.coolmart.net.cn/
 * Coolmarket #209
 * @author DMT
 * @modify author lisheng
 */

import java.util.List;
import java.util.Map;


import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Coolmarket_Detail;
import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;



public class Coolmarket implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);
	public Apk process(Page page) {
		if("http://www.coolmart.net.cn/".equals(page.getUrl().toString()))
		{
			
			for(int i=1;i<=2;i++)
			{
				String category=SinglePageDownloader.getHtml("http://api.aps.qq.com/wapapi/getCategory?type=-"+i+
						"&channel=77905&platform=touch&network_type=unknown&resolution=1440x900");
				ObjectMapper objectMapper=new ObjectMapper();
				//构造json
				category=category.split("}  ]")[0];
				//System.out.println(category);
				category=category.split("cateList")[1].substring(3)+"}  ]";
				//System.out.println(category);
				try {
					List<Map<String, Object>> list = objectMapper.readValue(category, List.class);
					
					//提取category id的值
					for(Map<String, Object> map:list)
					{
						String categoryId=map.get("categoryId").toString();
						System.out.println(categoryId);
						//构造app列表的url地址
						String pageNumber="0";
						for(int j=0;;j++)
						{
							String appListUrl="http://api.aps.qq.com/wapapi/getAppList?"
									+"categoryId="+categoryId+"&pageNo="+pageNumber+"&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1440x900";
							//判断app列表是否存在下一页
							String raw=SinglePageDownloader.getHtml(appListUrl);
							String temp=raw;
							if(!raw.contains("apkId"))
							{
								break;
							}
							else{
								ObjectMapper objectMapper1=new ObjectMapper();
								//构造json
								raw=raw.split("}  ]")[0];
								//System.out.println(raw);
								raw=raw.split("\"app\":")[1]+"}  ]";
								System.out.println(raw);
								try {
									List<Map<String, Object>> list1 = objectMapper1.readValue(raw, List.class);
									for(Map<String, Object> map1:list1)
									{
										String id=map1.get("id").toString();
										//System.out.println(id);
										page.addTargetRequest("http://www.coolmart.net.cn/#id=detail&appid="
												+id);
										
									}
								}
								catch(Exception e){}
							    finally
							    {
							    	//添加下一页的url地址
							    	System.out.println(temp);
							    	String str1[]=temp.split("pageNoContext");
									System.out.println(str1[1]);
									String url1=str1[1].replace("\": \"", "");
									url1=url1.replace("\"}", "");
									pageNumber=url1;
									if(url1.contains("="))
									{
										pageNumber=url1.replace("=", "%3D");
									}
									if(url1.contains("=="))
									{
										break;
										//pageNumber=url1.replace("==", "");
									}
							    }
							}
							}
						}
					}
					catch(Exception e)
					{
						
					}
			}
		}
		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		List<String> id =new ArrayList<String>();
//	   String next=null;
//	   String info=null;
//	   String nexturl=null;
//	   String categoryId=null;
//	   info=page.getHtml().toString();	 
//	   next=getNextPage(info);     
//       categoryId=getcategoryId(page.getUrl().toString());
//	   nexturl="http://api.aps.qq.com/wapapi/getAppList?categoryId="+ categoryId+"&pageNo="+next+"&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1600x900";	  
//	   id=getAppId(page.getHtml().toString());
//	   Set<String> cacheSet = Sets.newHashSet();
//	   for(int i=0;i<id.size();i++){
//		   cacheSet.add("http://www.coolmart.net.cn/#id=detail&appid="+id.get(i));
//	   }
//		if(next!=null){
//			cacheSet.add(nexturl);
//		}
//		if("http://www.coolmart.net.cn/".equals(page.getUrl().toString())){
//			cacheSet.add("http://api.aps.qq.com/wapapi/getAppList?categoryId=-10&pageNo=0&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1600x900");
//		}
//	    if("http://api.aps.qq.com/wapapi/getAppList?categoryId=-10&pageNo=0&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1600x900".equals(page.getUrl().toString()))
//	    {
//	    	for(int i=100;i<=153;i++){
//	    		//String url="http://api.aps.qq.com/wapapi/getAppList?categoryId="+i+"&pageNo=0&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1600x900";
//	    		
//	    		cacheSet.add("http://api.aps.qq.com/wapapi/getAppList?categoryId="+i+"&pageNo=0&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1600x900");
//	    	}
//	    	cacheSet.add("http://api.aps.qq.com/wapapi/getAppList?categoryId="+id+"&pageNo=0&pageSize=20&sortType=2&channel=77905&platform=touch&network_type=unknown&resolution=1600x900");
//	    	
//	    }
//	    for(String url : cacheSet){
//				page.addTargetRequest(url);		
//	  }	
		//提取页面信息
		if(page.getUrl().regex("http://www.coolmart.net.cn/#id=detail&appid=.*").match()){			
			Apk apk = Coolmarket_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
			}
		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
//	private static String getNextPage(String str){
//    	String tmp=null;
//    	//pageNoContext&quot;: &quot;MjA=&quot;
//		//String regex="pageNoContext&quot;: &quot;([^;]+);";
//    	String regex="pageNoContext&quot;: &quot;([^&]+)";
//		Pattern pattern = Pattern.compile(regex);  
//        Matcher matcher = pattern.matcher(str); 
//        if(matcher.find()){ 
//        	tmp=matcher.group(1).toString();
//
//        	tmp=tmp.replace("=","%3D");
//        }     
//    	return tmp;   	
//    }
//	private static List<String> getAppId(String str){
//    	List<String> tmp=new ArrayList<String>();
//    	//&quot;id&quot;: 10910, 
//    	String regex="&quot;id&quot;: ([^,]+),";
//		Pattern pattern = Pattern.compile(regex);  
//        Matcher matcher = pattern.matcher(str); 
//        while(matcher.find()){      	
//        	tmp.add(matcher.group(1).toString().trim());
//        }
//   
//    	return tmp;   	
//    }
//	
//	private static String getcategoryId(String str){
//    	String tmp=null;
//    	//pageNoContext&quot;: &quot;MjA=&quot;
//		//String regex="pageNoContext&quot;: &quot;([^;]+);";
//    	String regex="categoryId=([^&]+)&";
//		Pattern pattern = Pattern.compile(regex);  
//        Matcher matcher = pattern.matcher(str); 
//        if(matcher.find()){ 
//        	tmp=matcher.group(1).toString();
//
//        }     
//    	return tmp;   	
//    }
}
