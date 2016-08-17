package com.muyundefeng.util;

import java.util.List;

import com.muyundefeng.trinity.Node;
import com.muyundefeng.trinity.Text;

/**
 * @author lisheng
 *	定义一个工具类,该工具类的主要作用是将List<Text>转化为节点
 */
public class ListToNode {
 
	 /**
	 * @param texts 表示Text组成节点
	 * @return node表示该树中的节点元素
	 */
	public static Node listToNode(List<Text> texts){
		 Node node = new Node();
		 node.setTexts(texts);
		 return node;
	 }
}
