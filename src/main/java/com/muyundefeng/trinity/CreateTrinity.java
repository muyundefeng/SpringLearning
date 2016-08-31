package com.muyundefeng.trinity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
    private static Logger logger = LoggerFactory.getLogger(CreateTrinity.class);

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
		logger.info(texts.get(0).getText());
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
			flag = 0;
			if(node.getPreffixNode() != null){
				Node node2 = node.getPreffixNode();
				for(Text text:node2.getTexts()){
					logger.info("前缀节点元素为"+text.getText()+" ");
				}
				System.out.println();
				if(isVaribaleLeave(node.getPreffixNode())){
					//leaves.add(node.getPreffixNode());
					logger.info("从前缀开始分裂节点");
					createTrinity(node2, size + 1, Min);
				}
			}
			if(node.getSeparatorNode() != null){
				Node node2 = node.getSeparatorNode();
				for(Text text:node2.getTexts()){
					logger.info("分隔符节点元素为"+text.getText()+" ");
				}
				System.out.println();
				if(isVaribaleLeave(node.getSeparatorNode())){
					//leaves.add(node.getSeparatorNode());
					logger.info("从分隔符开始分裂节点");
					System.out.println("Min="+Min);
					System.out.println("size="+(size+1 ));
					createTrinity(node2, size + 1, Min);
				}
			}
			if(node.getSuffixNode() != null){
				Node node2 = node.getSuffixNode();
				for(Text text:node2.getTexts()){
					logger.info("后缀节点元素为"+text.getText()+" ");
				}
				System.out.println();
				//logger.info("后缀节点元素成为叶子");
				if(isVaribaleLeave(node.getSuffixNode())){
					//logger.info("后缀节点元素成为叶子");
					//leaves.add(node.getSuffixNode());
					logger.info("从后缀开始分裂节点");
					createTrinity(node2, size + 1, Min);
				}
			}
//			for(Node node2:leaves){
//				logger.info(leaves.size()+"");
//				for(Text text:node2.getTexts()){
//					logger.info(text.getText());
//				}
//				createTrinity(node2, size + 1, Min);
//			}
		}
	}
	
	/**判断叶子节点是否为提取的文本信息，对于非文本信息进行返回true，再进行相关的分裂
	 * 对于文本信息进行保留，不做分裂处理
	 * @param node
	 * @return
	 */
	public static boolean isVaribaleLeave(Node node){
		List<Text> list = node.getTexts();
		boolean flag = false;
		for(Text text:list){
			if(text.getText().contains("<"))
				flag |=true;
		}
		return flag;
	}
	
	public boolean expand(Node node ,int size){
		boolean result = false;
		int nodeSize = node.getTexts().size();
		logger.info("size1="+size);
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
			logger.info("shortestText="+base.getText());
			logger.info("size="+s);
			Pattern pattern = null;
			Map<Pattern,List<Map<Text, List<Integer>>>> targetMap = new HashMap<Pattern,List<Map<Text, List<Integer>>>>();
			List<Map<Text, List<Integer>>> patternList = null;
			for(int i = 0;i <=StringUtil.length(base)-s;i++){
				if(!found){
					patternList = new ArrayList<Map<Text, List<Integer>>>();
					found = true;
					for(Text text:node.getTexts()){
						List<Integer> matches = findMatches(text, base, i, s);
						found = isFound(matches);
						if(found){
							logger.info("flag="+flag);
							if(flag == 0)
							{//只添加一次base
								List<Integer> baseList = new ArrayList<Integer>();
								baseList.add(i);
								baseList.add(s);
								Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();
								map.put(base, baseList); 
								patternList.add(map);
								logger.info("baseText="+base.getText()+","+"base="+baseList);
								logger.info(patternList.toString());
								flag ++;
							}
							Map<Text, List<Integer>> map = new HashMap<Text, List<Integer>>();
							map.put(text, matches); 
							logger.info("Text="+text.getText()+","+"base="+matches);
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
			logger.info(targetMap.toString());
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
		logger.info(""+list.size());
		List<Text> preffixTexts = new ArrayList<Text>();
		for(Text text:node.getTexts()){
			//System.out.println(text);
			for(int j = 0; j<len; j++){
				Map<Text, List<Integer>> map = list.get(j);
				//map.keySet()
				boolean flag = false;
				logger.info("map="+map);
				for(Entry<Text, List<Integer>> entry:map.entrySet()){
					Text tempText = entry.getKey();
					logger.info(tempText.toString());
					if(tempText.hashCode() == text.hashCode()){
						//System.out.println("list="+list.get(i));
						logger.info(text.getText());
						List<Integer> matches = entry.getValue();
						logger.info(matches.toString());
						Text text2 = new Text(computePreffix(text,matches,pattern));
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
				logger.info("map="+map);
				for(Entry<Text, List<Integer>> entry:map.entrySet()){
					Text tempText = entry.getKey();
					logger.info(tempText.toString());
					if(tempText.hashCode() == text.hashCode()){
						//System.out.println("list="+list.get(i));
						logger.info(text.getText());
						List<Integer> matches = entry.getValue();
						logger.info(matches.toString());
						Text text2 = new Text(computeSeperator(text,matches,pattern));
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
				logger.info("map="+map);
				for(Entry<Text, List<Integer>> entry:map.entrySet()){
					Text tempText = entry.getKey();
					logger.info(tempText.toString());
					if(tempText.hashCode() == text.hashCode()){
						//System.out.println("list="+list.get(i));
						logger.info(text.getText());
						List<Integer> matches = entry.getValue();
						logger.info(matches.toString());
						Text text2 = new Text(computeSuffix(text,matches,pattern));
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
		for(Text text:prefix.getTexts()){
			logger.info("前缀＝"+text.getText());
		}
		for(Text text:separator.getTexts()){
			logger.info("分隔符＝"+text.getText());
		}
		for(Text text:suffix.getTexts()){
			logger.info("后缀＝"+text.getText());
		}
	}
	
	public static Text findShortTestText(Node node){
		List<Text> texts = node.getTexts();
		boolean flag = false;
		Text tempText = null;
		//判断一个节点中是否全部为nill,noll等字符串，如果全部都是，则不进行相关的处理
		for(Text text:texts){
			flag |= nullText(text);
		}
		logger.info("flag="+flag);
		if(!flag){
			return null;
		}
		//如果不全部为空，需要对节点进行处理
		if(flag){
			int temp = StringUtil.length(texts.get(0));
			tempText = texts.get(0); 
			for(int i = 1; i < texts.size(); i++){
				if(StringUtil.length(texts.get(i)) < temp&&!texts.get(i).getText().equals(NO_PREFFIX)
						&&!texts.get(i).getText().equals(NO_SEPERATOR)
						&&!texts.get(i).getText().equals(NO_SUFFIX))
				{
					temp = StringUtil.length(texts.get(i));
					logger.info("text="+texts.get(i).getText()+","+"length="+temp);
					tempText = texts.get(i);
				}
			}
		}
		logger.info("tempText="+tempText);
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
		logger.info("index="+index);
		//System.out.println("size="+size);
		String subString = StringUtil.subString(base,index, index + size);
		logger.info("ＢａｓｅsubString="+subString);
		List<Integer> list = new ArrayList<Integer>();
		//System.out.println("size="+size);
		for(int i = 0; i <= StringUtil.length(text)-size; i++){
			if(!text.getText().equals(base.getText())){
				String subStr =  StringUtil.subString(new Text(targetString),i,i + size);
				logger.info("subStr="+subStr);
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
		logger.info("pattern is="+subString);
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
	public  String computePreffix(Text text,List<Integer> list,Pattern pattern){
		 //Node node = new Node();
		 String raw = text.getText();
		 //System.out.println(text.getText());
		 int index1 = list.get(0); 
		 int index = list.get(1).intValue();
		 logger.info(index1+"");
		 String preffix = "";
		 
		 if(index1 == 0){
			 //如果匹配的模式开始的位置是0,则不存在前缀
			 if(!exitStrBefore(raw))
			 {
				 preffix = NO_PREFFIX;
				 return preffix;
			 }
		 }
		 //preffix = raw.substring(0, index); 
		 preffix = text.getText().split(pattern.getString())[0];
		 //preffix = StringUtil.subString(text, 0, index1);
		logger.info(preffix);
		return preffix;
	}
	
	/**进行前缀匹配之前查看是否存在字符串
	 * @param str
	 * @return
	 */
	public static boolean exitStrBefore(String str){
		int index = str.indexOf("<");
		String str1 = str.substring(0,index);
		return str1.length()>0?true:false;
	}
	
	/**计算节点的后缀
	 * @param text
	 * @param list
	 * @return
	 */
	public  String computeSeperator(Text text,List<Integer> list,Pattern pattern){
		 int length = list.size();
		 logger.info(list.toString());
		 String sperator = "";
		 //满足该条件存在分隔符,如果存在多个符合条件的分隔符,只需要取第一分隔符就可以
		 if(length>2&&patternTimes(text.getText(), pattern)>0){
			 int start = list.get(0) + list.get(1);
			 int end  = list.get(list.size()-2);//如果出现多个共享模板，分隔符取最大长度
			// sperator = text.getText().substring(start, end);
			 sperator = StringUtil.subString(text, start, end);
			 String temp = sperator;
			 //</b><br/>$35.99<br/><br/>测试结果是上述所示，需要考虑模板的后面与前面是否存在文本信息
			 sperator += strExitAndAfterPattern(text,pattern.getString(),temp);
			 sperator = strExitAndBeforePattern(text, pattern.getString(), temp)+sperator;
		 }
		 else{
			 sperator = NO_SEPERATOR;
		 }
		 logger.info(sperator);
		return sperator;
	}
	
	/**判断共享模板出现的次数
	 * <br><b>java<br><b>
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static int patternTimes(String text,Pattern pattern) {
		String tempStr = text;
		String temp = pattern.getString();
		int times = 0;
		while(true&&tempStr.contains(temp)){
			int index = text.indexOf(temp);
			if(index!= -1){
				times ++;
				tempStr = tempStr.replaceFirst(temp, "");
			}
		}
		return times;
	}
	
	/**查看分隔符后面，如果存在则返回相关的字符串信息
	 * @param text　
	 * @param pattern
	 * @param list　表明了模板相关位置
	 * @return
	 */
	public static String strExitAndAfterPattern(Text text, String pattern, String sperator){
		String betweenTextSep = StringUtils.substringBetween(text.getText(),sperator,pattern );

		if(betweenTextSep.length()>0){
			return betweenTextSep;
		}
		else {
			return "";
		}
		
	}
	/**查看分隔符前面，如果存在则返回相关的字符串信息
	 * @param text　
	 * @param pattern
	 * @param list　表明了模板相关位置
	 * @return
	 */
	public static String strExitAndBeforePattern(Text text, String pattern, String sperator){
		String betweenTextSep = StringUtils.substringBetween(text.getText(), pattern,sperator );
		if(betweenTextSep.length()>0){
			return betweenTextSep;
		}
		else {
			return "";
		}
		
	} 
	
	/**计算节点的后缀
	 * @param text
	 * @param list
	 * @return
	 */
	public  String computeSuffix(Text text,List<Integer> list,Pattern pattern){
		//System.out.println("");
		int index = list.get(0);
		int size = list.get(1);
		logger.info("index="+index+","+"size="+size);
		String suffix = "";
//		if(text.getText().indexOf(text.getText().length()-1) == '>')
//		{
//			logger.info("text="+text);
		if(index+size == StringUtil.length(text)){
			if(afterPatternText(text, pattern)!=null
					&&afterPatternText(text, pattern).length()>0)
				suffix = afterPatternText(text, pattern);
			else
				suffix = NO_SUFFIX;
		}
//		}
		else{
			//suffix = text.getText().substring(index, index + size);
			String temp[] = text.getText().split(pattern.getString());
			suffix = temp[temp.length-1];
//			String temp = StringUtil.subString(text, index, index + size);
//			suffix = text.getText().replace(temp, "");
		}
		logger.info("suffix="+suffix);
		return suffix;
	}
	
	/**在满足index+size == StringUtil.length(text)条件下，判断标签后面是否存在文本信息
	 * @param text
	 * @return
	 */
	public static String afterPatternText(Text text,Pattern pattern){
		logger.info(text.getText());
		logger.info(pattern.getString());
		String temp[] = text.getText().split(pattern.getString());
		String raw = null;
		if(temp.length > 1)	
			return temp[temp.length-1];
		else
			return null;
//		logger.info(raw);
//		if(raw.length()>0)
//			return raw;
//		else
//			return null;
	}
	
}
