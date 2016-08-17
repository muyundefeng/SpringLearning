package com.muyundefeng.trinity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.muyundefeng.input.InputDocument;


/**
 * @author lisheng
 *	创建三叉树的核心类
 */
public class CreateTrinity {

	public static final String DEFAULT_TOKEN = "char";//设置默认的的token大小,默认为一个字符
	
	public static int Max;
	
	public static int Min = 1;
	
	public static int S;
	
	public CreateTrinity() throws IOException {
		// TODO Auto-generated constructor stub
		List<Text> texts = InputDocument.getDefaultReadHtml();
		texts.sort(new Comparator<Text>() {

			public int compare(Text t1, Text t2) {
				// TODO Auto-generated method stub
				int minus = t1.getText().length() - t2.getText().length();
				if(minus > 0)
					return 1;
				else{
					if(minus < 0)
						return -1;
					else {
						return 0;
					}
				}
			}	
		});
		Max = texts!=null?texts.get(0).getText().length():-1;
		S = Max;
	}
	
	public void createTrinity(Node node){
		boolean expand = false;
		int size = Max;
		while(size >= Min&&!expand){
			
		}
	}
	
	public boolean expand(Node node ,int size){
		boolean result = false;
		int nodeSize = node.getTexts().size();
		if(nodeSize>0){
			Map<Pattern,List<Map<Text, List<Integer>>>> map = findPattern(node, size);
			for(Entry<Pattern, List<Map<Text, List<Integer>>>> entry:map.entrySet()){
				Pattern pattern = entry.getKey();
				List<Map<Text, List<Integer>>> list = entry.getValue();
				if(list!=null){
					result = true;
					
				}
			}
		}
		return result;
	}
	
	/**
	 * @param node 
	 * @param s 寻找长度为s的模板
	 * @return返回一个map对象,map中的key表示Text中共享的模板,value值表示的是Text中Pattern的具体信息,具体位置
	 */
	public Map<Pattern,List<Map<Text, List<Integer>>>> findPattern(Node node ,int s){
		boolean found = false;
		Text base = findShortTestText(node);
		Pattern pattern = null;
		Map<Pattern,List<Map<Text, List<Integer>>>> targetMap = new HashMap<Pattern,List<Map<Text, List<Integer>>>>();
		List<Map<Text, List<Integer>>> patternList = null;
		for(int i = 0;i > node.getTexts().size();i++){
			if(!found){
				patternList = new ArrayList<Map<Text, List<Integer>>>();
				found = true;
				for(Text text:node.getTexts()){
					List<Integer> matches = findMatches(text, base, i, s);
					found = isFound(matches);
					Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();
					map.put(text, matches); 
					patternList.add(map);
				}
				if(found){
					pattern = new Pattern();
					pattern.setString(subSqence(base,i,s));
					targetMap.put(pattern, patternList);
					break;
				}
			}
		}
		return targetMap;
	}
	
	/**根据传递过来的参数,对节点node进行分裂:前缀,分隔符与后缀
	 * @param node
	 * @param list
	 * @param pattern
	 */
	public void createChilder(Node node,List<Map<Text,List<Integer>>> list,Pattern pattern){
		Node prefix = new Node();
		Node separator = new Node();
		Node suffix = new Node();
		node.setPattern(pattern);
		int i = 0;
		for(Text text:node.getTexts()){
			List<Integer> matches = list.get(i).get(text);
			
		}
	}
	
	public static Text findShortTestText(Node node){
		List<Text> texts = node.getTexts();
		int temp = 0;
		Text tempText = null;
		for(int i = 0; i < texts.size(); i++){
			if(texts.get(i).getText().length() > temp)
			{
				temp = texts.get(i).getText().length();
				tempText = texts.get(i);
			}
		}
		return tempText;
	}
	
	public static List<Integer> findMatches(Text text,Text base,int index,int size){
		String targetString = text.getText();
		String subString = base.getText().substring(index, index + size);
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < targetString.length(); i++){
			String subStr = targetString.substring(i,i + size);
			if(subStr.equals(subString)){
				list.add(i);
				list.add(size);
				break;
			}
		}
		return list;
	}
	
	public static String subSqence(Text base,int index,int size){
		String subString = base.getText().substring(index, index + size);
		return subString;
	}
	
	public static boolean isFound(List<Integer> list){
		return list.size()>0?true:false;
	}
	
	public static Node computePreffix(Text text,List<Integer> list){
		
		
	}
}
