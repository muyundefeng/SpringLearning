package com.github.muyundefeng.utils;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class objectToJson {
	

	public static  String jsonStr(List<News> list){
		ObjectMapper objectMapper = new ObjectMapper();
		String returnStr="";
		try {
			returnStr = objectMapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStr;
		
	}
}
