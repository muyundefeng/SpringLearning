package TestRegex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.muyundefeng.trinity.Text;
import com.muyundefeng.util.StringUtil;

public class Test {
	public static int realStartIndex(Text text, int start){
		String raw = text.getText();
		int length = 0;
		if(raw != null){
			if(raw.startsWith("<")){
				
			}
			else{
				length = raw.indexOf("<");
				System.out.println("length="+length);
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
				System.out.println("matches="+match+","+"temp="+temp);
				
				count ++;
				realStartIndex += match.length();
				String betweenTokenStr = StringUtils.substringBetween(rawTemp,temp, match);
				System.out.println("betweenTokenStr="+betweenTokenStr);
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
	public static int realEndIndex(Text text, int end){
		String raw = text.getText();
		int length = 0;
		if(raw != null){
			if(raw.startsWith("<")){
				
			}
			else{
				length = raw.indexOf("<");
				System.out.println("length="+length);
				raw = raw.substring(length);
				System.out.println(raw);
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
		System.out.println("realEndIndex="+realEndIndex);
		return realEndIndex;
	}
	public static void test(List<String> list){
		if(list == null)
			System.out.println("yes");
		else
			System.out.println("no");
	}
	public static void main(String[] args) {
//		int count = 0;
//		String  string = "adacc";
//		System.out.println(string.substring(1, 3));
		String str ="java<br/><b>Bloch</b><br/>$53.53<br/></body></html>";
		int start = realStartIndex(new Text(str), 1);
		int end = realEndIndex(new Text(str), 5);
		System.out.println("str="+str.substring(start, end));
		int start1 = StringUtil.realStartIndex(new Text(str), 1);
		int end1 = StringUtil.realEndIndex(new Text(str), 5);
		System.out.println("str1="+str.substring(start1, end1));
		
//		str =str.replaceFirst("<[^>]*>", "");
//		System.out.println(str);
//		Pattern p=Pattern.compile("<[^>]*>");  
//		Matcher m=p.matcher(str);  
//		while(m.find()){  
//			System.out.println(m.group());
//		}
//		System.out.println(count);
		List<String> list = new ArrayList<String>();
		
		//test(list);
	}
}
