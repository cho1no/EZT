package com.yedam.app.usr.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	//정보조회
	@GetMapping("userInfo")
	public String userInfo(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userInfo";
	}
	
	//정보수정 -페이지
	@GetMapping("userUpdate")
	public String userUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userUpdate";
	}
	
	//정보수정 기능 
	@PostMapping("userUpdate")
	@ResponseBody
	public Map<String, Object> userUpdate(@RequestBody UserVO userVO){
		return userService.updateUser(userVO);
	}
	
	//삭제
	@GetMapping("userDelete")
	public String userDelete(UserVO userVO) {
		userService.deleteUser(userVO);
		return "redirect:login";
	}
}
