package com.bupt.muyundefeng.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.bupt.muyundefng.model.Nijia;

@Controller
public class NijiaController {

	@RequestMapping("/input_Nijia")
	public String inputNijiaInfo(Model model)
	{
		System.out.println("hello,world");
		model.addAttribute("nijia", new Nijia());
		return "NijiaInput";
	}
	@RequestMapping("/Nijia_saving")
	public String saveNijia(HttpServletRequest request,@ModelAttribute Nijia nijia,Model model)
	{
		List<MultipartFile> files=nijia.getImages();
		List<String> fileName=new ArrayList<String>();
		if(null!=files&&files.size()>0)
		{
			for(MultipartFile multipartFile:files)
			{
				String filename=multipartFile.getOriginalFilename();
				fileName.add(filename);
				File imgFile=new File(request.getServletContext().getRealPath("/images"),filename);
				System.out.println(imgFile.getAbsolutePath());
				try{
					multipartFile.transferTo(imgFile);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		}
		model.addAttribute("nijia", nijia);
		return "NijiaDetail";
	}
}
