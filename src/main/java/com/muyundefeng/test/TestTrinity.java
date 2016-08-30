package com.muyundefeng.test;

import java.io.IOException;
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
		LearnTemplate regex = new LearnTemplate();
		String result = "";
		regex.learnTemplate(node, result);
		System.out.println(result);
	}
}
