package com.github.muyundefeng.utils;

import java.util.HashSet;
import java.util.Set;

public class rmDumplicate {

	public static  Set<String> set = new HashSet<String>();
	
	public static boolean isRmove(String str){
		return set.add(str)?true:false;
	}
	public static void main(String[] args) {
		isRmove("http://www.pku.edu.cn/");
		boolean flag = isRmove("http://www.pku.edu.cn/");
		System.out.println(flag);
	}
	
}
