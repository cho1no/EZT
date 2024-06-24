package com.yedam.app.sgi.web;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yedam.app.sgu.service.SignUpService;
import com.yedam.app.usr.service.UserVO;

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
	
	@GetMapping("signUp")
    public String signUpForm(Model model) {
        model.addAttribute("userVO", new UserVO());
		return "sgu/signUp";
	}
    
    @PostMapping("signUp")
    public String signUp(@Valid UserVO userVO, Errors errors, Model model, HttpServletResponse resp) {
    	 if(!userVO.getUsersPw().equals(userVO.getUsersPwCheck())) {
         	errors.rejectValue("usersPwCheck", "error", "비밀번호가 다릅니다");
         	
         }
        if (errors.hasErrors()) {
            // 회원가입 실패시 입력 데이터값을 유지
            model.addAttribute("userVO", userVO);
            
//            Map<String, String> validatorResult = signUpService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }
            // 회원가입 페이지로 다시 리턴
            //resp.setContentType("text/html; charset=utf-8");
            //  resp.getWriter().append("<script>").append("alert('가입완료!');").append("location.href='sgi/login';</script>");
            return "sgu/signUp";
        }
        //  주석풀어야 회원가입됨
        //  signUpService.joinUser(userVO);
        model.addAttribute("msg", "회원가입 완료!");
        model.addAttribute("url", "sgi/login");
        return "gongtong/message";
    }
    
      @GetMapping("findIdPw")
      public String findIdPw() {
    	  return "sgi/findIdPw";
      }
}
