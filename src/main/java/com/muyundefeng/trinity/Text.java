package com.muyundefeng.trinity;

/**
 * @author lisheng
 *	定义三叉树节点中的Text元素.
 */
public class Text implements DocDucument{
	
	private String text;//定义Text节点中的text字符串

	public Text(String text) {
		// TODO Auto-generated constructor stub
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

//	public int compareTo(String o) {
//		// TODO Auto-generated method stub
//		int minus = text.length() - o.length();
//		if(minus > 0)
//			return 1;
//		else {
//			if(minus < 0)
//				return -1;
//			else
//				return 0;
//		}
//	}
}
