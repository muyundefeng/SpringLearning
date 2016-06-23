package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.Dao;



@Controller
public class ControllerProject {

	@Autowired
	public Dao dao;
	@RequestMapping("/")
	public String welcome(Model model){
		model.addAttribute("projectName",dao.getName());
		return "index";
	}
}
