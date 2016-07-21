package com.github.muyundefeng.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUrl {

	public String path;
	public String urlEncoding;
	public final String defaultEncoding = "utf-8";
	
	public MyUrl(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
		this.urlEncoding = defaultEncoding;
	}
	
	public MyUrl(String path,String urlEncoding) {
		// TODO Auto-generated constructor stub
		this.path = path;
		this.urlEncoding = urlEncoding;
	}
	
	public boolean match(String regex){
		//boolean flag = true;
		Pattern r = Pattern.compile(regex);
		Matcher matcher = r.matcher(path);
		
		while(matcher.find()){
			if(matcher.group()!=null){
				return true;
			}
		}
		return false;
	}
}
