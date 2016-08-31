package com.muyundefeng.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.muyundefeng.input.InputDocument;
import com.muyundefeng.trinity.CreateTrinity;
import com.muyundefeng.trinity.LearnTemplate;
import com.muyundefeng.trinity.Node;
import com.muyundefeng.trinity.Text;

/**三叉树测试程序
 * @author lisheng
 *
 */
public class TestTrinity {
	
	public static boolean isVariable(Node node){
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
	public static boolean isLeaf(Node node){
		boolean isLeaf = false;
		if(node != null)
		{
			if(node.getPreffixNode() == null&&node.getSeparatorNode() == null
					&&node.getSuffixNode() == null){
				isLeaf = true;
			}
		}
		System.out.println(isLeaf);
		return isLeaf;	
	}
	//遍历构建成功的三叉树
	public static void preScanTrinity(Node node){
		List<Text> texts = node.getTexts();
		System.out.println("-----------------");
		for(Text text:texts)
		{
			System.out.println(text.getText());
		}
		if(node.getPreffixNode()!=null){
			preScanTrinity(node.getPreffixNode());
			}
		if(node.getSeparatorNode()!=null){
			preScanTrinity(node.getSeparatorNode());
			}
		if(node.getSuffixNode()!=null){
			preScanTrinity(node.getSuffixNode());
			}
	}

	public static void main(String[] args) throws IOException {
		List<Text> texts = null;
		try {
			texts = InputDocument.getDefaultReadHtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node node = new Node();
		node.setTexts(texts);
		CreateTrinity trinity = new CreateTrinity();
		trinity.createTrinity(node);
		preScanTrinity(node);
		
	}
}
