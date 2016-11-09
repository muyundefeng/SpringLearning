package com.muyundfeng.termcut.textPreprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.sun.corba.se.spi.ior.Identifiable;

/**进行相关的文本预处理，将文本转化为空间向量
 * @author lisheng
 *
 */
public class PreProcessText {

	private List<String> docs = new ArrayList<String>();//存放文档的id
	
	private Set<String> terms = new HashSet<String>();
	
	private Map<String,String> idToContens = new HashMap<String,String>();
	
	private Map<String, List<Double>> vertexes = new HashMap<String,List<Double>>();//存放文档特征向量
	
	private Map<String, Map<String, Integer>> tfs = new HashMap<String,Map<String,Integer>>();
	
	private Map<String, Integer> dfs = new HashMap<String,Integer>();
	
	/**
	 * @param filePath 输入文件的路径
	 * @return　文件内容
	 * @throws IOException 
	 */
	public String getContent(String filePath) throws IOException{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String temp = null;
		String content = "";
		while ((temp = reader.readLine()) != null){
			content = content + temp +" ";
		}
		reader.close();
		return content;
	}
	
	/**统计词频
	 * @param text 文档的内容
	 * @param docId　文档的标识符
	 */
	public void handleText(String text,String docId){
		docs.add(docId);
		String afterProcessNumber = text.replaceAll("\\d", "");
		String afterProcessStop = afterProcessNumber.replaceAll( "\\p{Punct}", "" );
		
		String temp[] = StringUtils.split(afterProcessStop, " ");
		List<String> words = Arrays.asList(temp);
		terms.addAll(words);
		idToContens.put(docId, afterProcessStop);
	}
	
	public void TFStastic(String text,String docId){
		String afterProcessNumber = text.replaceAll("\\d", "");
		String afterProcessStop = afterProcessNumber.replaceAll( "\\p{Punct}", "" );
		String temp[] = StringUtils.split(afterProcessStop, " ");
		List<String> words = Arrays.asList(temp);
		Map<String, Integer> termToTf = new HashMap<String, Integer>();
		for(String word:words){
			int TF = StringUtils.countMatches(afterProcessStop, word);
			termToTf.put(word, TF);
		}
		tfs.put(docId, termToTf);
		
	}
	
	/**
	 * 统计文档频率
	 */
	public void DFStastic(){
		for(String word: terms)
		{
			int df = 0;
			for(String id: docs){
				if(idToContens.get(id).contains(word))
				{
					df ++;
				}
			}
			dfs.put(word, df);
		}
	}
	
	/**
	 * 构建特征向量
	 */
	public void buildVertex(){
		System.out.println(terms.toString());
		for(String doc:docs)
		{
			List<Double> vertex = new ArrayList<Double>();
			int index = 0;
			for(String word:terms){
				//先要得到词频
				if(!tfs.get(doc).containsKey(word))
					vertex.add(index, (double)0);
				else{
					int tf = tfs.get(doc).get(word);
					int df = dfs.get(word);
					double tf_idf = getWeight(df, tf);
					vertex.add(index, tf_idf);
				}
				index ++;
			}
			vertexes.put(doc, vertex);
		}	
		System.out.println(vertexes.toString());
	}
//	
	/**tf-idf计算公式
	 * @param df 文档频率
	 * @param tf　词频
	 * @param sum　文档数量
	 * @return tf-idf
	 */
	public double getWeight(Integer df, Integer tf){
		return tf * (Math.log((docs.size()/df)));
	}
	
	/**测试函数
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		System.out.println(1 * (Math.log((2))));
		PreProcessText handle = new PreProcessText();
		String filePaths[] = {"./src/main/resources/a.txt","./src/main/resources/b.txt","./src/main/resources/c.txt","./src/main/resources/d.txt"};
		for(String path:filePaths)
		{
			String content = handle.getContent(path);
			handle.handleText(content, path);
		}
		for(String path:filePaths)
		{
			String content = handle.getContent(path);
			handle.TFStastic(content, path);
		}
		System.out.println(handle.tfs.toString());
		handle.DFStastic();
		System.out.println(handle.dfs.toString());
		handle.buildVertex();
	}
}
