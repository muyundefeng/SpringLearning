package com.github.muyundefeng.utils;

public class reasonalUrl {

	public static boolean isReasonalUrl(String url){
		String temp = url.toLowerCase();
		if(!temp.startsWith("http://")||temp.endsWith(".zip") || temp.endsWith(".apk") || temp.endsWith(".jpeg")
				|| temp.endsWith(".exe") || temp.endsWith(".jpg") || temp.endsWith(".rar") 
				|| temp.endsWith(".gif") || temp.contains("#") || temp.endsWith(".png")
				|| temp.endsWith(".doc")|| temp.endsWith(".xml")
				|| temp.endsWith(".css") || temp.endsWith(".js")||temp.endsWith("javascript"))
			return false;
		return true;
	}
}
