package com.muyundfeng.termcut.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FindCore {
	
	private Map<String, List<Double>> vertexes = null;

	private List<String> docIds = null;
	
	private Set<String> terms = null;
	
	private Map<String,String> idToContens = null;
	
	private Map<String, Set<String>> dicts = null;

	private Map<String, List<Map<Set<String>, List<Double>>>> cluster = null;//要重构前面的类，主要是数据结构的变化，预留出接口
	
	/**
	 * termCut主函数
	 */
	public void termCut(){
		while(true){
			Map<String, List<List<Map<Set<String>, List<Double>>>>> coreAndCluster = findCore(cluster);
			for(Entry<String, List<List<Map<Set<String>, List<Double>>>>> entry:coreAndCluster.entrySet()){
				String coreTerm = entry.getKey();//得到关键单词
				List<List<Map<Set<String>, List<Double>>>> clusters = entry.getValue();//得到该单词分成的聚类的类别
				
			}
		}
	}
	
	/**cluster 参数的含义最外层的Map是做好标签的聚类(就是所说的键)，整个map的值是本聚类所包括的所有的特征向量，用一个list存放
	 * list里面存放的是一个map，map键为Set表示此特征向量覆盖的词，值为本文档所对应的特征向量
	 * @param cluster 已经分配好的聚类标签
	 * @return
	 */
	public Map<String, List<List<Map<Set<String>, List<Double>>>>> findCore(Map<String, List<Map<Set<String>, List<Double>>>> cluster){
		Map<String, List<Map<Set<String>, List<Double>>>> newCluster = new HashMap<String, List<Map<Set<String>,List<Double>>>>();
		String CoreTerm = null; 
		double MinRMcut = Double.MAX_VALUE;//?
		//Cb key---存放coreTerm,value---存放将一个聚类分裂之后的聚类信息
		Map<String, List<List<Map<Set<String>, List<Double>>>>> Cb = null;
		for(Entry<String,List<Map<Set<String>, List<Double>>>> entry: cluster.entrySet()){
			String label = entry.getKey();
			List<Map<Set<String>, List<Double>>> list = entry.getValue();
			List<Set<String>> list2 = new ArrayList<Set<String>>();
			for(Map<Set<String>, List<Double>> map: list){
				//得到本聚类中的所有单词
				for(Entry<Set<String>, List<Double>> entry2: map.entrySet()){
					Set<String> words = entry2.getKey();
					list2.add(words);
				}
			}
			//得到本聚类的所有单词
			Set<String> terms = getSet(list2);
			for(String word: terms){
				Cb = new HashMap<String, List<List<Map<Set<String>, List<Double>>>>>();
				for(Map<Set<String>, List<Double>> map: list){
					for(Entry<Set<String>, List<Double>> entry2: map.entrySet()){
						Set<String> words = entry2.getKey();
						//如果包含term单词，就将聚类c一分为二，其中包含该单词的为聚类一，否则为聚类二
						if(words.contains(word)){
							newCluster.put(label+"-1", list);
						}
						else{
							newCluster.put(label+"-0", list);
						}
					}
				}
				//对已经一分为二的聚类进行RMcut标准计算
				List<Map<Set<String>, List<Double>>> vertex1 = null;
				List<Map<Set<String>, List<Double>>> vertex2 = null;
				int flag = 0;
				for(Entry<String, List<Map<Set<String>, List<Double>>>> entry1: newCluster.entrySet()){
					if(flag == 0)
					{
						vertex1 = entry1.getValue();
						flag ++;
					}
					else {
						vertex2 = entry1.getValue();
					}
				}
				double molecule = calSimilarity(vertex1, vertex2);
				double denominator = calDenominator();
				double RMCut = molecule/denominator;
				//构建返回的特征向量，因为将一个聚类一分为二，所以用链表存放分割之后的聚类信息
				List<List<Map<Set<String>, List<Double>>>> returnVertex = new ArrayList<List<Map<Set<String>,List<Double>>>>();
				if(RMCut < MinRMcut){
					CoreTerm = word;
					MinRMcut = RMCut;
					returnVertex.add(vertex1);
					returnVertex.add(vertex2);
					Cb.put(word, returnVertex);
				}
				
			}
		}
		return Cb;
	}
	
	/**单词库去重
	 * @param list
	 * @return
	 */
	public Set<String> getSet(List<Set<String>> list){
		Set<String> set = new HashSet<String>();
		for(Set<String> words: list){
			set.addAll(words);
		}
		return set;
	}
	
	/**类别1与2之间的相似性判断
	 * cut(Ck ,C − Ck )计算
	 * @param docIds1　Ck
	 * @param docIds2 C − Ck
	 * @return
	 */
	public double calSimilarity(List<Map<Set<String>, List<Double>>> vertex1,List<Map<Set<String>, List<Double>>> vertex2){
		double similarity = 0;
		//获得第一个聚类的特征向量集合
		List<List<Double>> lists = new ArrayList<List<Double>>();
		for(Map<Set<String>, List<Double>> map:vertex1){
			for(Entry<Set<String>, List<Double>> entry: map.entrySet()){
				lists.add(entry.getValue());
			}
		}
		//获得第二个聚类的特征向量集合
		List<List<Double>> lists1 = new ArrayList<List<Double>>();
		for(Map<Set<String>, List<Double>> map:vertex1){
			for(Entry<Set<String>, List<Double>> entry: map.entrySet()){
				lists1.add(entry.getValue());
			}
		}
		//计算RMCut标准
		for(List<Double>list:lists ){
			for(List<Double> list1:lists1){
				similarity+= calMulti(list,list1);
			} 
		}
		return similarity;
	}
	
	/**计算两个特征向量的相似度
	 * @param list 特征向量1
	 * @param another特征向量2
	 * @return
	 */
	public double calMulti(List<Double> list,List<Double> another){
		double sum = 0;
		for(Double number1: list){
			for(Double number2:another){
				sum+= number1*number2;
			}
		}
		return sum;
	}
	
	/**计算分母公式Denominator
	 * @return
	 */
	public double calDenominator(){
		double sum = 0;
		for(String id: docIds){
			List<Double> vertex1 = vertexes.get(id);
			for(String id1: docIds){
				if(!id1.equals(id)){
					List<Double> vertex2 = vertexes.get(id1);
					for(int i =0;i<vertex1.size(); i++){
						sum += vertex1.get(i)*vertex2.get(i);
					}
				}
			}
		}
		return sum/2;
	}
}
