package com.muyundefeng.trinity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.muyundefeng.input.InputDocument;
import com.muyundefeng.util.StringUtil;


/**
 * @author lisheng
 *	创建三叉树的核心类
 */
public class CreateTrinity {

	public static final String DEFAULT_TOKEN = "char";//设置默认的的token大小,默认为一个字符
	
	public static int Max;
	
	public static int Min = 1;
	
	public static int S;
	
	private static final String NO_PREFFIX = "nill";//当前缀为空时,默认变量
	
	private static final String NO_SEPERATOR = "noll";
	
	private static final String NO_SUFFIX = "nell";
	
	List<Node> leaves = new ArrayList<Node>();
	
	private static int flag = 0;
	
	public CreateTrinity() throws IOException {
		// TODO Auto-generated constructor stub
		List<Text> texts = InputDocument.getDefaultReadHtml();
		texts.sort(new Comparator<Text>() {
			public int compare(Text t1, Text t2) {
				// TODO Auto-generated method stub
				if(StringUtil.length(t1) > StringUtil.length(t2)){
					return 1;
				}
				else
				{
					if(StringUtil.length(t1) < StringUtil.length(t2)){
						return -1;
					}
					else
						return 0;
				}
			}
		});
		System.out.println(texts.get(0).getText());
		Max = StringUtil.length(texts.get(0));
		//System.out.println(Max);
		System.out.println("Max="+Max);
		S = Max;
	}
	
	/**对主要函数进行封装
	 * @param node
	 */
	public void createTrinity(Node node){
		createTrinity(node, Max, Min);
	}
	/**创建三叉树主函数
	 * @param node
	 */
	public void createTrinity(Node node,int Max,int Min){
		boolean expanded = false;
		int size = Max;
		while(size >= Min&&!expanded){
			expanded = expand(node, size);
			size = size -1;
		}
		if(expanded){
			leaves.clear();
			if(node.getPreffixNode() != null){
				Node node2 = node.getPreffixNode();
				for(Text text:node2.getTexts()){
					System.out.print("前缀节点元素为"+text.getText()+" ");
				}
				System.out.println();
				leaves.add(node.getPreffixNode());
			}
			if(node.getSeparatorNode() != null){
				Node node2 = node.getSeparatorNode();
				for(Text text:node2.getTexts()){
					System.out.print("分隔符节点元素为"+text.getText()+" ");
				}
				System.out.println();
				leaves.add(node.getSeparatorNode());
			}
			if(node.getSuffixNode() != null){
				Node node2 = node.getSuffixNode();
				for(Text text:node2.getTexts()){
					System.out.print("后缀节点元素为"+text.getText()+" ");
				}
				System.out.println();
				leaves.add(node.getSuffixNode());
			}
			for(Node node2:leaves){
				createTrinity(node2, size + 1, Min);
			}
		}
	}
	
	
	public boolean expand(Node node ,int size){
		boolean result = false;
		int nodeSize = node.getTexts().size();
		//System.out.println("size1="+size);
		if(nodeSize>0){
			Map<Pattern,List<Map<Text, List<Integer>>>> map = findPattern(node, size);
			if(map != null){
				for(Entry<Pattern, List<Map<Text, List<Integer>>>> entry:map.entrySet()){
					Pattern pattern = entry.getKey();
					List<Map<Text, List<Integer>>> list = entry.getValue();
					if(list!=null){
						result = true;
						createChilder(node, list, pattern);
					}
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
		//System.out.println(node);
//		for(Text text:node.getTexts()){
//			System.out.println(text.getText());
//		}
		if(base != null)
		{
			System.out.println("shortestText="+base.getText());
			System.out.println("size="+s);
			Pattern pattern = null;
			Map<Pattern,List<Map<Text, List<Integer>>>> targetMap = new HashMap<Pattern,List<Map<Text, List<Integer>>>>();
			List<Map<Text, List<Integer>>> patternList = null;
			for(int i = 0;i < StringUtil.length(base)-s;i++){
				if(!found){
					patternList = new ArrayList<Map<Text, List<Integer>>>();
					found = true;
					for(Text text:node.getTexts()){
						List<Integer> matches = findMatches(text, base, i, s);
						found = isFound(matches);
						if(found){
							if(flag == 0)
							{//只添加一次base
								List<Integer> baseList = new ArrayList<Integer>();
								baseList.add(i);
								baseList.add(s);
								Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();
								map.put(base, matches); 
								patternList.add(map);
								System.out.println("baseText="+base.getText()+","+"base="+baseList);
								flag ++;
							}
							Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();
							map.put(text, matches); 
							System.out.println("Text="+text.getText()+","+"base="+matches);
							patternList.add(map);
						}
						
					}
					if(found){
						pattern = new Pattern();
						pattern.setString(subSqence(base,i,s));
						targetMap.put(pattern, patternList);
						break;
					}
				}
			}
			System.out.println(targetMap);
			return targetMap;
		}
		else{
			return null;
		}
		
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
		int len = list.size();
		List<Text> preffixTexts = new ArrayList<Text>();
		for(Text text:node.getTexts()){
			//System.out.println(text);
			for(int j = 0; j<len; j++){
				Map<Text, List<Integer>> map = list.get(j);
				//map.keySet()
				boolean flag = false;
				System.out.println("map="+map);
				for(Entry<Text, List<Integer>> entry:map.entrySet()){
					Text tempText = entry.getKey();
					System.out.println(tempText);
					if(tempText.hashCode() == text.hashCode()){
						//System.out.println("list="+list.get(i));
						System.out.println(text.getText());
						List<Integer> matches = entry.getValue();
						System.out.println(matches);
						Text text2 = new Text(computePreffix(text,matches));
						preffixTexts.add(text2);
						flag = true;
						break;
					}
				}
				if(flag == true){
					break;
				}
			}
			continue;
		}
		prefix.setTexts(preffixTexts);
		List<Text> separatorTexts = new ArrayList<Text>();
		for(Text text:node.getTexts()){
//			List<Integer> matches = list.get(i).get(text);
//			Text text2 = new Text(computeSeperator(text,matches));
//			separatorTexts.add(text2);
			//System.out.println(text);
			for(int j = 0; j<len; j++){
				Map<Text, List<Integer>> map = list.get(j);
				//map.keySet()
				boolean flag = false;
				System.out.println("map="+map);
				for(Entry<Text, List<Integer>> entry:map.entrySet()){
					Text tempText = entry.getKey();
					System.out.println(tempText);
					if(tempText.hashCode() == text.hashCode()){
						//System.out.println("list="+list.get(i));
						System.out.println(text.getText());
						List<Integer> matches = entry.getValue();
						System.out.println(matches);
						Text text2 = new Text(computeSeperator(text,matches));
						separatorTexts.add(text2);
						flag = true;
						break;
					}
				}
				if(flag == true){
					break;
				}
			}
			continue;
		}
		separator.setTexts(separatorTexts);
		List<Text> suffixTexts = new ArrayList<Text>();
		for(Text text:node.getTexts()){
//			List<Integer> matches = list.get(i).get(text);
//			Text text2 = new Text(computeSuffix(text,matches));
//			suffixTexts.add(text2);
//			

//			List<Integer> matches = list.get(i).get(text);
//			Text text2 = new Text(computeSeperator(text,matches));
//			separatorTexts.add(text2);
			//System.out.println(text);
			for(int j = 0; j<len; j++){
				Map<Text, List<Integer>> map = list.get(j);
				//map.keySet()
				boolean flag = false;
				System.out.println("map="+map);
				for(Entry<Text, List<Integer>> entry:map.entrySet()){
					Text tempText = entry.getKey();
					System.out.println(tempText);
					if(tempText.hashCode() == text.hashCode()){
						//System.out.println("list="+list.get(i));
						System.out.println(text.getText());
						List<Integer> matches = entry.getValue();
						System.out.println(matches);
						Text text2 = new Text(computeSuffix(text,matches));
						suffixTexts.add(text2);
						flag = true;
						break;
					}
				}
				if(flag == true){
					break;
				}
			}
			continue;
		
		}
		suffix.setTexts(suffixTexts);
		node.setPreffixNode(prefix);
		node.setSeparatorNode(separator);
		node.setSuffixNode(suffix);
	}
	
	public static Text findShortTestText(Node node){
		List<Text> texts = node.getTexts();
		boolean flag = false;
		Text tempText = null;
		//判断一个节点中是否全部为nill,noll等字符串，如果全部都是，则不进行相关的处理
		for(Text text:texts){
			flag |= nullText(text);
		}
		if(!flag){
			return null;
		}
		//如果不全部为空，需要对节点进行处理
		if(flag){
			int temp = StringUtil.length(texts.get(0));
			for(int i = 1; i < texts.size(); i++){
				if(StringUtil.length(texts.get(i)) < temp)
				{
					temp = StringUtil.length(texts.get(i));
					tempText = texts.get(i);
				}
			}
		}
		return tempText;
	}
	
	/**判断node节点中是否存在无效的字符串
	 * @param text
	 * @return
	 */
	public static boolean nullText(Text text){
		String str = text.getText();
		if(str.equals(NO_PREFFIX)||str.equals(NO_SEPERATOR)||str.equals(NO_SUFFIX))
			return false;
		else
			return true;
		
	}
	
	/**对节点进行重构
	 * @param node
	 */
	public static void handleNode(Node node){
		List<Text> texts = node.getTexts();
		List<Text> newText = new ArrayList<Text>();
		for(Text text:texts){
			String raw = text.getText();
			if(raw.equals(NO_PREFFIX)||raw.equals(NO_SEPERATOR)||raw.equals(NO_SUFFIX)){
				
			}else {
				Text text2 = new Text(raw);
				newText.add(text2);
			}
		}
		node.setTexts(newText);
	}
	
	/**Text是否匹配Base子字符串(Text中是否包含Base中的子字符串)
	 * @param text node节点中一条Text
	 * @param base node节点中最短的Text
	 * @param index 开始索引的位置
	 * @param size 查找匹配模板的长度
	 * @return
	 */
	public static List<Integer> findMatches(Text text,Text base,int index,int size){
		String targetString = text.getText();
		//String subString = base.getText().substring(index, index + size)
		//System.out.println("index="+index);
		//System.out.println("size="+size);
		String subString = StringUtil.subString(base,index, index + size);
		//System.out.println("subString="+subString);
		List<Integer> list = new ArrayList<Integer>();
		//System.out.println("size="+size);
		for(int i = 0; i < StringUtil.length(text)-size; i++){
			if(!text.getText().equals(base.getText())){
				String subStr =  StringUtil.subString(new Text(targetString),i,i + size);
				System.out.println("subStr="+subStr);
				if(subStr.equals(subString)){
					list.add(i);
					list.add(size);
					//break;
				}
			}
		}
		return list;
	}
	
	public static String subSqence(Text base,int index,int size){
		String subString =StringUtil.subString(base, index, index + size);
		System.out.println("pattern is="+subString);
		return subString;
	}
	
	public static boolean isFound(List<Integer> list){
		return list.size()>0?true:false;
	}
	
	/**该函数是对node节点进行相关的处理,首先需要对节点中的每一个Text进行相关的处理,然后处理完成之后
	 * 在进行相关的合并,组成新的Node节点.
	 * @param text 该节点中的Text
	 * @param list 匹配生成的模板相关位置
	 * @return
	 */
	public  String computePreffix(Text text,List<Integer> list){
		 //Node node = new Node();
		 String raw = text.getText();
		 //System.out.println(text.getText());
		 int index1 = list.get(0); 
		 int index = list.get(1).intValue();
		 System.out.println(index);
		 String preffix = "";
		 if(index1 == 0){
			 //如果匹配的模式开始的位置是0,则不存在前缀
			 preffix = NO_PREFFIX;
		 }
		 else{
			 //preffix = raw.substring(0, index); 
			 preffix = StringUtil.subString(text, 0, index);
		 }
		 System.out.println(preffix);
		return preffix;
	}
	
	/**计算节点的后缀
	 * @param text
	 * @param list
	 * @return
	 */
	public  String computeSeperator(Text text,List<Integer> list){
		 int length = list.size();
		 String sperator = "";
		 //满足该条件存在分隔符,如果存在多个符合条件的分隔符,只需要取第一分隔符就可以
		 if(length>2){
			 int start = list.get(0) + list.get(1);
			 int end  = list.get(2);
			// sperator = text.getText().substring(start, end);
			 sperator = StringUtil.subString(text, start, end);
		 }
		 else{
			 sperator = NO_SEPERATOR;
		 }
		 System.out.println(sperator);
		return sperator;
	}
	
	/**计算节点的后缀
	 * @param text
	 * @param list
	 * @return
	 */
	public  String computeSuffix(Text text,List<Integer> list){
		//System.out.println("");
		int index = list.get(0);
		int size = list.get(1);
		System.out.println("index="+index+","+"size="+size);
		String suffix = "";
		if(index+size == StringUtil.length(text)){
			suffix = NO_SUFFIX;
		}
		else{
			//suffix = text.getText().substring(index, index + size);
			String temp = StringUtil.subString(text, index, index + size);
			suffix = text.getText().replace(temp, "");
		}
		System.out.println("suffix="+suffix);
		return suffix;
	}
	
}
