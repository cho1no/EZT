package com.yedam.app.sgu.web;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.sgu.service.SignUpService;
import com.yedam.app.usr.service.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignUpController {
	@Autowired
	SignUpService signUpService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	// == 사용자 회원가입 ==
	@GetMapping("signUp/joinUser")
    public String signUpUserForm(Model model) {
        model.addAttribute("userVO", new UserVO());
		return "sgu/signUp_user";
	}
    
    @PostMapping("signUp/joinUser")
    public String signUpUser(@Valid UserVO userVO, Errors errors, Model model, HttpServletResponse resp) {
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
            return "sgu/signUp_user";
        }
        //  주석풀어야 회원가입됨
         signUpService.joinUser(userVO);
        model.addAttribute("msg", "회원가입 완료!");
        model.addAttribute("url", "/sgi/login");
        return "gongtong/message";
    }
    
    // == 작업자 회원가입 ==
    @GetMapping("signUp/joinWorker")
    public String signUpWorkerForm(Model model) {
    	model.addAttribute("userVO", new UserVO());
    	model.addAttribute("categories", commonCodeService.selectCommonCodeAll("0C"));
    	model.addAttribute("regions", commonCodeService.selectCommonCodeAll("0B"));
    	return "sgu/signUp_worker";
    }
    

    
    @PostMapping("signUp/joinWorker")
    public String signUpWorker(@Valid UserVO userVO, Errors errors, Model model, HttpServletResponse resp) {
    	if(!userVO.getUsersPw().equals(userVO.getUsersPwCheck())) {
    		errors.rejectValue("usersPwCheck", "error", "비밀번호가 다릅니다");
    		
    	}
    	if (errors.hasErrors()) {
    		// 회원가입 실패시 입력 데이터값을 유지
    		model.addAttribute("userVO", userVO);
    		return "sgu/signUp_worker";
    	}
    	//  주석풀어야 회원가입됨
    	//signUpService.joinWorker(userVO);
    	
    	model.addAttribute("msg", "회원가입 완료!");
    	model.addAttribute("url", "/sgi/login");
    	return "gongtong/message";
    }
    
    //아이디 중복체크
    @GetMapping("signUp/checkId/{id}")
    @ResponseBody
    public String checkId(@PathVariable String id) {
    	UserVO vo = new UserVO();
    	vo.setUsersId(id);
    	return signUpService.idChk(vo);
    }
    
    //닉네임 중복체크
    @GetMapping("signUp/checkNick/{nick}")
    @ResponseBody
    public String checkNick(@PathVariable String nick) {
    	UserVO vo = new UserVO();
    	vo.setUsersNick(nick);
    	return signUpService.nickChk(vo);
    }
    
    //이메일 중복체크
    @GetMapping("signUp/checkEmail")
    @ResponseBody
    public String checkEmail(String email) {
    	UserVO vo = new UserVO();
    	vo.setUsersEmail(email);
    	return signUpService.emailChk(vo);
    }
}
