package com.muyundefeng.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.muyundefeng.trinity.Text;


/**
 * @author lisheng
 *	处理输入的html文档,并将html文档转化为字符串
 */
public class InputDocument {

	private static final String FILE_DIR = "/home/lisheng/GradleWorkplace/TrinityTest/src/main/resources/htmls/";
	
	/**默认读取html文件得目录是在resource目录之下
	 * @throws IOException
	 */
	public static List<Text> getDefaultReadHtml() throws IOException{
		List<Text> texts = new ArrayList<Text>();
		File file = new File(FILE_DIR);
		System.out.println(file.getAbsolutePath());
		if(!file.exists())
			System.out.println("不存在本目录!");
		String htmlName[] = file.list();
		for(String str:htmlName){
			System.out.println(str);
			File file2 = new File(FILE_DIR + str);
			String sourceText = "";
			if(file2.isFile()){
				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file2));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String Line = "";
				while((Line = bufferedReader.readLine())!= null){
					sourceText += Line;
				}
				Text text = new Text(sourceText);
				texts.add(text);
			}
			else{}
		}
		return texts;
	}
	
	/**自定义输入html文件
	 * @param filesNames
	 * @throws IOException 
	 */
	public static List<Text> getDefiniteReadHtml(String...filesNames) throws IOException{
		List<Text> texts = new ArrayList<Text>();
		for(String str:filesNames){
			File file2 = new File(str);
			String sourceText = "";
			if(file2.isFile()){
				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file2));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String Line = "";
				while((Line = bufferedReader.readLine())!= null){
					sourceText += Line;
				}
				Text text = new Text(sourceText);
				texts.add(text);
			}
		}
		return texts;
	}
}
