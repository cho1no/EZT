package com.yedam.app.usr.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
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
	//비밀번호 변경 -페이지
	@GetMapping("user/PwUpdate")
	public String userPwchangeForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userPwUpdate";
	}
	//비밀번호 변경 기능
	@PostMapping("user/PwUpdate")
	@ResponseBody
	public boolean userPwUpdate(@RequestBody UserVO userVO) {
		return userService.updateUserPw(userVO);
	}
	
	//후기목록 조회
	@GetMapping("user/revList")
	public String userReviewList(Model model, @AuthenticationPrincipal LoginUserVO user) {
		List<RequestVO> list = userService.userReviewList(user.getUserNo());
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));		
		model.addAttribute("reviewList", list);
		return "usr/userReviewList";
	}
	
	//의뢰 목록 조회
	@GetMapping("user/reqList")
	public String userReqList(Model model, @AuthenticationPrincipal LoginUserVO user) {
		List<RequestVO> list = userService.userReqList(user.getUserNo());
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("requestList", list);
		return "usr/userRequestList";
	}
	
	
}
