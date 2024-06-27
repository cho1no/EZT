package com.yedam.app.doc.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.sgi.service.LoginUserVO;


@Controller
public class docController {

	@GetMapping("test")
	public String test(Model model, @AuthenticationPrincipal LoginUserVO user) {
		model.addAttribute("userId", user.getUserVO().getUsersNo());
		return "doc/index";
	}
	
	
}
