package com.muyundefeng.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.muyundefeng.trinity.Text;


/**自定义String工具类
 * @author lisheng
 *
 */
public class StringUtil {

	/**获取Text文本长度,将一个标签视为一个token，计算token的个数
	 * @param text
	 * @return
	 */
	public static int length(Text text){
		//String rawText = text.getText().replace("<[^>]*>", "");
		return count(text.getText()); //+ rawText.length();
	}
	
	/**获取String中的标签个数
	 * @param str
	 * @return
	 */
	public static int count(String str){
		int count = 0;
		Pattern pattern = Pattern.compile("<[^>]*>");  
		Matcher matcher = pattern.matcher(str);  
		while(matcher.find()){  
			count ++;
		}
		return count;
	}
	
	/**截取子字符串
	 * @param text　函数作用的目标
	 * @param start　子字符串开始的位置
	 * @param end　子字符串结束的位置
	 * @return　返回子字符串
	 */
	public static String subString(Text text, int start, int end){
		int realStart = realStartIndex(text, start);
		int realEnd = realEndIndex(text, end);
		//System.out.println(realEnd);
		return text.getText().substring(realStart, realEnd);
	}
	/**查找字符串真正开始的位置
	 * @param text
	 * @param start
	 * @return
	 */
	public static int realStartIndex(Text text, int start){
		String raw = text.getText();
		int length = 0;
		if(raw != null){
			if(raw.startsWith("<")){
				
			}
			else{
				length = raw.indexOf("<");
				//System.out.println("length="+length);
				raw = raw.substring(length);
			}
		}
		Pattern pattern = Pattern.compile("<[^>]*>");  
		Matcher matcher = pattern.matcher(raw);  
		int realStartIndex = 0;
		int count = 0;
		//int realEndIndex = 0;
		String rawTemp = "";
		String temp = "";
		String match = "";
		String endMatch = "";
		while(matcher.find()){
			if(count != (start + 1) )
			{
				temp = match;
				match = matcher.group();
				endMatch = match;
				//System.out.println("matches="+match+","+"temp="+temp);
				
				count ++;
				realStartIndex += match.length();
				String betweenTokenStr = StringUtils.substringBetween(rawTemp,temp, match);
				//System.out.println("betweenTokenStr="+betweenTokenStr);
				if(betweenTokenStr!=null&&betweenTokenStr.length()>0){
					
					realStartIndex += betweenTokenStr.length();
				}
				rawTemp = raw;
				raw = raw.replaceFirst("<[^>]*>", "");
			}
			else
				break;
		}
		return realStartIndex + length -endMatch.length() ;
	}
	/**查找字符串真正结束的位置
	 * @param text
	 * @param start
	 * @return
	 */
	public static int realEndIndex(Text text, int end){
		String raw = text.getText();
		int length = 0;
		if(raw != null){
			if(raw.startsWith("<")){
				
			}
			else{
				length = raw.indexOf("<");
				//System.out.println("length="+length);
				raw = raw.substring(length);
				//System.out.println(raw);
			}
		}
		Pattern pattern = Pattern.compile("<[^>]*>");  
		Matcher matcher = pattern.matcher(raw);  
		int realEndIndex = length;
		int count = 0;
		String rawTemp = raw;
		String temp = "";
		String match = "";
		//String endMacth = "";
		while(matcher.find()){
			if(count != end)
			{
				temp = match;
				match = matcher.group();
				//endMacth = match;
				count ++;
				realEndIndex += match.length();
				//System.out.println(realEndIndex);
				//System.out.println("matches="+match+","+"temp="+temp);				
				String betweenTokenStr = StringUtils.substringBetween(rawTemp,temp, match);
				//System.out.println(betweenTokenStr);
				if(betweenTokenStr!=null&&betweenTokenStr.length()>0){
					//System.out.println(betweenTokenStr.length());
					realEndIndex += betweenTokenStr.length();
				}
				rawTemp = raw;
				raw = raw.replaceFirst("<[^>]*>", "");
			}
			else
				break;
		}
		//System.out.println("realEndIndex="+realEndIndex);
		return realEndIndex;
	}
	
	/**查找字符串真正开始的位置
	 * @param text
	 * @param start
	 * @return
	 */
	/*
	public static int realStartIndex(Text text, int start){
		String raw = text.getText();
		Pattern pattern = Pattern.compile("<[^>]*>");  
		Matcher matcher = pattern.matcher(raw);  
		int realStartIndex = 0;
		int count = 0;
		while(matcher.find()){
			count ++;
			if(count != start + 1)
			{
				String match = matcher.group();
				realStartIndex += match.length();
				raw = raw.replaceFirst("<[^>]*>", "");
			}
			else
				break;
		}
		int index = raw.indexOf("<");
		return realStartIndex + index;
	}
	/**查找真正结束的位置
	 * @param text
	 * @param end
	 * @return
	 */
	/*
	public static int realEndIndex(Text text, int end){
		String raw = text.getText();
		Pattern pattern = Pattern.compile("<[^>]*>");  
		Matcher matcher = pattern.matcher(raw);  
		int realEndIndex = 0;
		int count = 0;
		String rawTemp = "";
		String temp = "";
		String match = "";
		while(matcher.find()){
			if(count != end )
			{
				temp = match;
				match = matcher.group();
				count ++;
				realEndIndex += match.length();
				String betweenTokenStr = StringUtils.substringBetween(rawTemp,temp, match);
				if(betweenTokenStr!=null&&betweenTokenStr.length()>0){
					realEndIndex += betweenTokenStr.length();
				}
				rawTemp = raw;
				raw = raw.replaceFirst("<[^>]*>", "");
			}
			else
				break;
		}
		
		return realEndIndex;
	}
	*/
	
}
