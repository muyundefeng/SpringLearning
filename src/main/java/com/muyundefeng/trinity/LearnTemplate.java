package com.muyundefeng.trinity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**遍历三叉树,生成模板算法
 * @author lisheng
 *
 */
public class LearnTemplate {

	private static final String NO_PREFFIX = "nill";//当前缀为空时,默认变量
	
	private static final String NO_SEPERATOR = "noll";
	
	private static final String NO_SUFFIX = "nell";
	
	public static final String OPTION_CHAR = "?";
	
	public static final String A_CHAR = ".";
	
	public static final String CAN_HAVE_CHARS = "*";
	
	public static final String HAVE_CHAES = "+";
	
	/**自动生成正则表达式主要方法
	 * @param node
	 * @return
	 */
	public String learnTemplate(Node node,String result){
		if(isOptional(node)){
			result += "(";
		}
		if(isLeaf(node))
		{
			if(isVariable(node)){
				result += freshCaptureGroup();
			}
		}
		else{
			result += learnTemplate(node.getPreffixNode(),result);
			result += node.getPattern().getString();
			if(isRepeatable(node)){
				result += "(" +  learnTemplate(node.getSeparatorNode(), result)
					+ node.getPattern().getString();
				if(contain(node))
					result += ")" + CAN_HAVE_CHARS;
				else
					result += ")" + HAVE_CHAES;
			}
			result += learnTemplate(node.getSuffixNode(), result);
		}
		if(isOptional(node))
			result += ")" + OPTION_CHAR;
		return result;
			
	}
	
	/**判断一个节点是否重复
	 * @param node
	 * @return
	 */
	public boolean isOptional(Node node){
		List<Text> list = node.getTexts();
		boolean isOptional = false;
		for(Text text:list){ 
			if(text.equals(NO_PREFFIX)||text.equals(NO_SEPERATOR)
					||text.equals(NO_SUFFIX)){
				isOptional = true;
				break;
			}
			else{
				isOptional &= false;
			}
		}
		return isOptional;
	}
	
	/**判断一个节点是否为重复.主要是检查共享模板在node节点中出现的次数,如果Text中的pattern至少
	 * 出现的次数多于一次
	 * @param node
	 * @return
	 */
	public boolean isRepeatable(Node node){
		boolean isRepeatable = false;
		String pattern = node.getPattern().getString();
		List<Text> texts = node.getTexts();
		for(Text text:texts){
			if(count(text.getText(),pattern) > 1){
				isRepeatable = true;
				break;
			}
			else{
				isRepeatable &= false;
			}
		}
		return isRepeatable;
	}
	
	/**计算Text中出现共享模板的次数.
	 * @param text
	 * @param pattern
	 * @return
	 */
	public int count(String text,String pattern){
		int count = 0;
		Pattern pattern2 = Pattern.compile(pattern);
		Matcher m = pattern2.matcher(pattern);  
		while(m.find()){
			count ++;
		}
		return count;
	}
	
	/**判断节点中是否存在nill
	 * @param node
	 * @return
	 */
	public boolean contain(Node node){
		boolean isContain = false;
		List<Text> list = node.getTexts();
		for(Text text:list){
			if(text.getText().contains(NO_SEPERATOR)){
				isContain = true;
				break;
			}
			else{
				isContain &= false;
			}
		}
		return isContain;
	}
	/**判断一个节点是否为叶子节点
	 * @param node
	 * @return
	 */
	public boolean isLeaf(Node node){
		boolean isLeaf = false;
		if(node != null)
		{
			if(node.getPreffixNode() == null&&node.getSeparatorNode() == null
					&&node.getSuffixNode() == null){
				isLeaf = true;
			}
		}
		return isLeaf;	
	}
	
	/**判断一个节点是具有值的属性
	 * @param node
	 * @return
	 */
	public boolean isVariable(Node node){
		boolean isVariable = true;
		List<Text> texts = node.getTexts();
		for(Text text:texts){
			if(text.getText().contains("<"))
				isVariable &= true;
			else
			{
				isVariable = false;
				break;
			}
		}
		return isVariable;
	}
	
	/**
	 * 获取的捕获分组可能存在多个,且数目不确定,捕获分组之间代表字符不能出现重复,获取字母之间的组合
	 * @return得到一个捕获分组
	 */
	public String freshCaptureGroup(){
		return A_CHAR + CAN_HAVE_CHARS;
	}
}
