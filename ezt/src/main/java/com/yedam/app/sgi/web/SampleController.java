package com.yedam.app.sgi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@GetMapping("main")
	public String all() {
		return "loginTest";
	}
	
	@GetMapping("user")
	public void user() {}
	
	@GetMapping("worker")
	public void worker() {}
	
	@GetMapping("admin")
	public void admin() {}
	
	@GetMapping("login")
	public String login() {
		return "doc/login";
	}
}
