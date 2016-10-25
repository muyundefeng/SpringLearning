package com.appCrawler.pagePro.apkDetails;
import java.util.HashMap;
/**
 *龙珠游乐园  http://www.lzvw.com/
 * Aawap #222
 * @author DMT
 */
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Lzvw_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Lzvw_Detail.class);
	public static Apk getApkDetail(Page page){
		Apk apk = null;
		String appName = null;				//app名字
		String appDetailUrl = null;			//具体页面url
		String appDownloadUrl = null;		//app下载地址
		String osPlatform = null ;			//运行平台
		String appVersion = null;			//app版本
		String appSize = null;				//app大小
		String appUpdateDate = null;		//更新日期
		String appType = null;				//下载的文件类型 apk？zip？rar？
		String appVenderName = null;			//app开发者  APK这个类中尚未添加
		String appDownloadedTime=null;		//app的下载次数
		String appDescription = null;		//app的详细描述
		List<String> appScrenshot = null;			//app的屏幕截图
		String appTag = null;				//app的应用标签
		String appCategory = null;			//app的应用类别 
		appDetailUrl = page.getUrl().toString();
		//System.out.println("电我啊");
		//String[] temp1=appDetailUrl.split("?");
		appName = page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_name']/h1/text()").toString();
//		String number=appDetailUrl+temp1[1];
	//	appDownloadedTime=SinglePageDownloader.getHtml(number, "", null);
//		System.out.println(appDownloadedTime);
		String temp11=page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_user']/script/@src").toString();
		//String id=page.getUrl().toString();
		int length=page.getUrl().toString().length();
		String temp2=page.getUrl().toString();
		String temp1="";
		int i=length-4;
		while(true)
		{
			if((int)temp2.charAt(i)==47)
			{
				break;
			}
			else
			{
				i--;
			}
		}
		String id=temp2.substring(i+1,length-4);
		System.out.println(id);
		Map<String, String> map = new HashMap<String, String>();  
		map.put("Cookie", "sogou_ts_ads0=popdate:115%u5E7411%u67081%u65E5&vt:0&ht:&qt:0&jd:; ASPSESSIONIDQACDBCCB=DOMBMLFDPDKFGHIKKJHJHJBJ; __jsluid=1ec644ac3958b8f2ea9e93e9deb64a0f; ASPSESSIONIDSCACDDDA=OGHJOKFDPJMPFDPPEDOMKNAE; ASPSESSIONIDQCBCCDCA=FFOLPLFDLGKGIEFPNHLMABLC; CNZZDATA1221717=cnzz_eid%3D1220294204-1446169774-%26ntime%3D1446426220; ASPSESSIONIDSCACCCDA=OICDKMFDIEBIBPEAMKNLEPEO; ASPSESSIONIDQACACDCB=BLCHKMFDBBFNOHBEMPLCIEFJ; ASPSESSIONIDQCAACDCA=PBGNCNFDHBDDFDCNEOPLPLEB; ASPSESSIONIDSCDDACDB=DHJBLNFDKOIHOFLONFJIBOEF");
		map.put("Host", "www.lzvw.com");  
		map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36" );
	
		String temp12 = SinglePageDownloader.getHtml("http://www.lzvw.com/js/hits.asp?id="+id,"",null,map);
		//int length1=temp12.length();
		//System.out.println(temp12);
		int end=0;
		int start=0;
		int j=0;
		while(true)
		{
			
			if(temp12.charAt(j)>=48&&temp12.charAt(j)<=57)
			{
				end++;
			}
			else
			{
				start++;
			}
			if(temp12.charAt(j)==59)
			{
				break;
			}
			j++;
		}
		int newStart=start-3;
		appDownloadedTime=temp12.substring(newStart,newStart+end);
		//System.out.println(appDownloadedTime);
		//System.out.println("http://www.lzvw.com"+temp11);
		//appDownloadedTime=SinglePageDownloader.getHtml("http://www.lzvw.com"+temp11, "", null);
		//System.out.println(appDownloadedTime);
		//appDownloadUrl = page.getHtml().xpath("//div[@class='game_mainInfo_right_links']/a[1]/@href").toString();
		//过滤掉ios平台
		appTag=page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[1]/text()").toString();
		if(page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[2]/text()").toString()!=null)
		{
			appTag=appTag+" "+page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[2]/text()").toString();
			if(page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[3]/text()").toString()!=null)
			{
				appTag=appTag+" "+page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[3]/text()").toString();
				if(page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[4]/text()").toString()!=null)
				{
					appTag=appTag+" "+page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[4]/text()").toString();
					if(page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[5]/text()").toString()!=null)
					{
						appTag=appTag+" "+page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[5]/text()").toString();
						if(page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[6]/text()").toString()!=null)
						{
							appTag=appTag+" "+page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_tags']/a[6]/text()").toString();
						}
					}
				}
			}
		}
		//appTag=page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_name']//a/text()").toString();
//		String downType=page.getHtml().xpath("//div[@class='gameDown']/a[1]/text()").toString();
//		String temp="";
//		if(("Android下载").equals(page.getHtml().xpath("//div[@class='gameDown']/a[2]/text()").toString()))
//		{
//			temp = page.getHtml().xpath("//div[@class='gameDown']/a[2]/@href").toString();
//			if(appDownloadUrl.equals("www.shouyou520.com"))
//			{
//				appDownloadUrl=null;
//			}
//			else
//			{
//				appDownloadUrl=temp;
//			}
//		}
//		else
//		{
//			if(("IOS用户下载").equals(page.getHtml().xpath("//div[@class='gameDown']/a[1]/text()").toString()))
//			{
//				appDownloadUrl=null;
//			}
//			else
//			{
//				temp = page.getHtml().xpath("//div[@class='gameDown']/a[1]/@href").toString();
//				if(("www.shouyou520.com").equals(appDownloadUrl))
//				{
//					appDownloadUrl=null;
//				}
//				else
//				{
//					appDownloadUrl=temp;
//				}
//			}
//		}
		//}""
		
		String appSize1 = page.getHtml().xpath("//div[@class='p_part_content_item_title Ek_dl']/span/text()").toString();
		if(appSize1!=null)
		{
			if(appSize1.contains("/"))
			{
			String[] temp=appSize1.split("/");
			appSize=temp[1].substring(0,temp[1].length()-1);
			}
			else
			{
				appSize=appSize1;
			}
		}
		osPlatform=page.getHtml().xpath("//div[@class='p_part_content_item_title Ek_dl']/strong/text()").toString();
		if(osPlatform!=null)
		{
			if(!osPlatform.contains("android"))
			{
				appDownloadUrl=null;
			}
			else
			{
				appDownloadUrl = page.getHtml().xpath("//div[@class='p_part_content_item_title EK_dl']/a[2]/@href").toString();
			}
		}
//		String osPlatform1=page.getHtml().xpath("//div[@class='gameDetail']/dl/dd[1]/text()").toString();
//		if(osPlatform1.contains(" ")||osPlatform1.contains(";")||osPlatform1.contains(",")||osPlatform1.contains("&")||osPlatform1.contains("")||osPlatform1.contains("/"))
//		{
//			if(osPlatform1.contains(" "))
//			{
//				String [] osPlatform3=osPlatform1.split(" ");
//				osPlatform=osPlatform3[0];
//			}
//			if(osPlatform1.contains(";"))
//			{
//				String [] osPlatform3=osPlatform1.split(";");
//				osPlatform=osPlatform3[0];
//			}
//			if(osPlatform1.contains(","))
//			{
//				
//				String [] osPlatform3=osPlatform1.split(",");
//				if(osPlatform3[0].contains("安卓")||osPlatform3[0].contains("android")||osPlatform3[0].contains("Android"))
//					osPlatform=osPlatform3[0];
//				else
//					osPlatform=osPlatform3[1];
//			}
//			if(osPlatform1.contains("&"))
//			{
//				String [] osPlatform3=osPlatform1.split("&");
//				osPlatform=osPlatform3[0];
//			}
//			if(osPlatform1.contains("/"))
//			{
//				String [] osPlatform3=osPlatform1.split("/");
//				osPlatform=osPlatform3[1];
//			}
//			if(osPlatform1.contains("、"))
//			{
//				String [] osPlatform3=osPlatform1.split("、");
//				osPlatform=osPlatform3[0];
//			}
//				
//		}
		//if(osPlatform1.contains("、")||)
		//{
			//String [] osPlatform3=osPlatform1.split("、");
			//osPlatform1=osPlatform3[0];
		//}
		appCategory=page.getHtml().xpath("//div[@class='gameDetail']/dl/dt[4]/a/text()").toString();
		//appUpdateDate=page.getHtml().xpath("//div[@class='gameDetail']/dl/dt[6]/text()").toString();
		//appDownloadedTime=page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_user']/script/text()").toString();
		//String appDownloadedTime1=page.getHtml().xpath("//div[@class='game_mainInfo_right_all_left_user']//text()").toString();
		//if(appDownloadedTime)
//		boolean flag=false;
//		for(int i=0;i<10;i++)
//		{
//			if(appDownloadedTime1.contains(""+i));
//				
//				{	
//				flag=true;
//				break;}
//				
//		}
//		if(flag==true)
//		{
//			String []temp2=appDownloadedTime1.split(":");
//			appDownloadedTime=temp2[1]; 
//		}
//		else
//		{
//			appDownloadedTime=null;
//		}
		//appDownloadedTime=temp2[1];
		appDescription=page.getHtml().xpath("//div[@class='game_description_content']/text()").toString();
		appScrenshot=page.getHtml().xpath("//div[@class='game_screenshots']//img/@src").all();
		
		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
			System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			System.out.println("appVenderName="+appVenderName);
			System.out.println("appDownloadedTime="+appDownloadedTime);
			System.out.println("appDescription="+appDescription);
			System.out.println("appTag="+appTag);
			System.out.println("appScrenshot="+appScrenshot);
			System.out.println("appCategory="+appCategory);

						
		}
		else return null;
		
		return apk;
	}
	
//	private static String usefulInfo(String allinfoString)
//	{
//		String info = null;
//		while(allinfoString.contains("<"))
//			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
//			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//		info = allinfoString.replace("\n", "").replace(" ", "");
//		return info;
//	}
	
	
	
	

}
