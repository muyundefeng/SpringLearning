package com.github.muyundefeng.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//所爬去信息存放在文件中
import com.github.muyundfeng.getProcess.Property;


public class saveToFile {

	public static void saveAsFile(String str) throws IOException{
		String fileName = Property.getFilePath();
		File file = new File(fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter writer = new FileWriter(file);
		BufferedWriter wr = new BufferedWriter(writer);
		wr.write(str);
	}
	
}
