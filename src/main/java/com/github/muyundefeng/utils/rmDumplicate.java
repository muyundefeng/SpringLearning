package com.github.muyundefeng.utils;

import java.util.HashSet;
import java.util.Set;

public class rmDumplicate {

	public static  Set<String> set = new HashSet<String>();
	
	public static boolean isRmove(String str){
		return set.add(str)?true:false;
	}
}
