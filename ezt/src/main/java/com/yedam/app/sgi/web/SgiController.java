package com.yedam.app.sgi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SgiController {
	
	
	@GetMapping("main")
	public String all() {
		return "gongtong/main";
	}
	 
	@GetMapping("user")
	public void user() {}
	
	@GetMapping("worker")
	public void worker() {}
	
	@GetMapping("admin")
	public void admin() {}
	
	@GetMapping("login")
	public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
		return "sgi/login";
	}
	
//	@GetMapping("/loginForm")
//    public String loginForm(@RequestParam(value = "error", required = false) String error,
//                            @RequestParam(value = "exception", required = false) String exception, Model model) {
//		log.info(exception);
//        model.addAttribute("error", error);
//        model.addAttribute("exception", exception);
//        return "sgi/login";
//    }
	
	
    
    @GetMapping("findIdPw")
    public String findIdPw() {
	    return "sgi/findIdPw";
    }
      
      
}
