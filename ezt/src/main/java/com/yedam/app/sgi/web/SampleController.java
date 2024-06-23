package com.yedam.app.sgi.web;

import org.springframework.beans.factory.annotation.Autowired;
//import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yedam.app.sgu.service.SignUpService;
import com.yedam.app.usr.service.UsrVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SampleController {
	@Autowired
	SignUpService signUpService;
	
	@GetMapping("main")
	public String all() {
		return "sgi/loginTest";
	}
	
	@GetMapping("user")
	public void user() {}
	
	@GetMapping("worker")
	public void worker() {}
	
	@GetMapping("admin")
	public void admin() {}
	
	@GetMapping("login")
	public String login() {
		return "sgi/login";
	}
	
	@GetMapping("/SignUp")
	public String SignUpForm() {
		return "sgu/SignUp";
	}
	
	@PostMapping("/SignUp")
	public String SignUp(UsrVO usrVO) {
		signUpService.joinUsr(usrVO);
		return "redirect:/sgu/SignUp";
	}
	
	
}
