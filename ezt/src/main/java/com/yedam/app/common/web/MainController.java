package com.yedam.app.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.common.mapper.MainMapper;
@Controller
public class MainController {
	@Autowired
	MainMapper mainMap;
	
	@GetMapping("main")
	public String main(Model model) {
		model.addAttribute("reviews", mainMap.select4Review()); // 후기 높은순 5개
		model.addAttribute("ports", mainMap.select4Port());
		return "gongtong/main";
	}
	@GetMapping("/")
	public String noAddr() {
		return "redirect:/main";
	}
	@GetMapping("admin")
	public String admin() {
		return "adm/index";
	}
}
