package com.appCrawler.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.appCrawler.utils.PropertiesUtil;

public  class ProcessNumber {
	public static synchronized int modifyProcessNum(String action){
		String path= PropertiesUtil.getProcessPath();
		
		File file = new File(path); 
		int processNumber = 0;
		try {
		if(!file.exists()){
			file.createNewFile();
		}		
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader (reader);
			String content = br.readLine();
			reader.close();
			br.close();
			if(content == null)
				content = "0";
			int count = Integer.parseInt(content);
			
			if(action.equals("ADD"))
				count++;
			else if(action.equals("MINUS")){
				count--;
			}else if(action.equals("getNumber")){
				processNumber = count;
			}
			else{
				System.out.println("the pramater of modifyProcessNum is wrong!");
			}
			String after = String.valueOf(count);
			
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(after);
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return processNumber;
	}
}
