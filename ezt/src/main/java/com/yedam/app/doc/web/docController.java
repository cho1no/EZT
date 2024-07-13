package com.yedam.app.doc.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;


@Controller
public class docController {

	@GetMapping("test")
	public String test(Model model, @AuthenticationPrincipal LoginUserVO user, RequestVO requestVO) {
		model.addAttribute("userId", user.getUserNo());
		model.addAttribute("requestNo", requestVO.getRequestNo());
		
		return "doc/index";
	}
	
	@GetMapping("report")
	public String report(Model model, @AuthenticationPrincipal LoginUserVO user) {
		model.addAttribute("userId", user.getUserVO());
		return "doc/report";
	}
	
	@RequestMapping(value="reportModal", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginModal() {
		return "/report";
    }

	
}
