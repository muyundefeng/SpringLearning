package us.codecraft.webmagic.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import us.codecraft.webmagic.downloader.SinglePageDownloader;

public class Json2Object {
/* 以下函数主要处理下面所属;类型的json数据格式
 * {"life":0,"letter":0,"description":"","data":"[{数据内容,存放键:值}]","mark":1}
 * 其中参数str时原生json字符串,keyWord是要获得键,data是获得json数据有效部分
 */
	public static List<String> ExtraInfo(String str,String data,String keyWord)
	{
		Map<String, Object> urlMap = null;
		//System.out.println(str);
		List<String> values=new ArrayList<String>();
			try {
		        ObjectMapper objectMapper=new ObjectMapper();
		        urlMap = new ObjectMapper().readValue(str, Map.class);
		         if (null != urlMap) { 
		        	List<LinkedHashMap<String, Object>> list = objectMapper.readValue(urlMap.get(data).toString(), List.class);
		        	//System.out.println(list);
		        	for(int j=0;j<list.size();j++)
		             {
		            	 String value=list.get(j).get(keyWord).toString();
		            	 values.add(value);
		             }
		         }
		    }  catch (Exception e) {
		        e.printStackTrace();
		    }
			return values;
	}
	/* 以下函数主要处理下面所属类型的json数据格式
	 * [{数据内容,存放键:值}]
	 * 其中参数str时原生json字符串,keyWord是要获得键,data是获得json数据有效部分
	 * 这两个函数通过修改字符串都可以相互使用
	 */
	public static List<String> ExtraInfo1(String str,String keyWord)
	{
		List<String> values=new ArrayList<String>();
		ObjectMapper objectMapper=new ObjectMapper();
		 try
		 {
			List<LinkedHashMap<String, Object>> list = objectMapper.readValue(str, List.class);
	    	//System.out.println(list);
	    	for(int j=0;j<list.size();j++)
	         {
	        	 Map<String,Object> map=list.get(j);
	        	 Set<String> set = map.keySet();
	              for (Iterator<String> it = set.iterator();it.hasNext();) {
	                  String key = it.next();
	                  if(keyWord.equals(key))
	                  {
	                	  //System.out.println(map.get(key).toString());
	                	  values.add(map.get(key).toString());
	                  }
	              }
	         }
		 }
	    	catch (Exception e) {
				// TODO: handle exception
			}
		 return values;
	}
	public static void main(String[]args)
	{
//		String json=SinglePageDownloader.getHtml("http://android.ymzs.com/detail/com.anshang.free.CUKOTNOGBBU?userId=&imei=866329024603181");
//		String temp[]=json.split(",\"detail\":");
//		String temp1[]=temp[1].split(",\"id\"");
//		String tString="["+temp1[0]+"]";
//		List<String> packageNames1=Json2Object.ExtraInfo1(tString,"description");
//		System.out.println(packageNames1.get(0));
//		List<String> packageNames2=Json2Object.ExtraInfo1(tString,"screenShots");
//		System.out.println(packageNames2.get(0));
		String json=SinglePageDownloader.getHtml("http://api.aps.qq.com/wapapi/getCategory?type=-1&channel=77905&platform=touch&network_type=unknown&resolution=1440x900");
		
	}
}