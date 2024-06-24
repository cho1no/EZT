package com.yedam.app.doc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class docController {

	@GetMapping("test")
	public String test(Model model) {

		return "doc/index";
	}
	
	
}
