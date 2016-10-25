package com.app.saveDB.Persistent;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class svaeInfoToData {

	 /**
	 * @param path:存放文件的路径
	 * @param apks:apk的文件名与下载地址，采用json存放
	 */
	public static void writeToFile(String path,String apks)
    { 
		File file = new File(path);
		if(file.exists()){
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream out = null;   
        try {   
            out = new FileOutputStream(file,true);   
            out.write(apks.getBytes());    
            out.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }  
	public static void main(String[] args) {
		writeToFile("/home/lisheng/data/", "apks");
	}
}
