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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserReqCriteria;
import com.yedam.app.usr.service.UserReqPageDTO;
import com.yedam.app.usr.service.UserRvwCriteria;
import com.yedam.app.usr.service.UserRvwPageDTO;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	CommonCodeService commonCodeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RequestService requestService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserMapper userMapper;
	
	//정보조회
	@GetMapping("/info")
	public String userInfo(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userVO", vo.getUserVO());
		return "usr/userInfo";
	}	
	
	//정보수정 -페이지
	@GetMapping("/update")
	public String userUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userVO", vo.getUserVO());
		return "usr/userUpdate";
	}
	
	//정보수정 기능 
	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> userUpdate(@RequestBody UserVO userVO, @AuthenticationPrincipal LoginUserVO vo){
		Map<String, Object> result = userService.updateUser(userVO);
		UserVO uvo = userMapper.selectUserInfo(userVO.getUsersId());
		vo.setUserVO(uvo);
		return result;
	}

	//비밀번호 변경 -페이지
	@GetMapping("/pwUpdate")
	public String userPwchangeForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userPwUpdate";
	}
	
	//비밀번호 변경 기능
	@PostMapping("/pwUpdate")
	@ResponseBody
	public boolean userPwUpdate(@RequestBody UserVO userVO) {
		return userService.updateUserPw(userVO);
	}
	
	//사용자 후기목록 조회
	@GetMapping("/revList")
	public String userReviewList(Model model, UserRvwCriteria cri, @AuthenticationPrincipal LoginUserVO vo) {
		model.addAttribute("userVO", vo.getUserVO());
		
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));		
		cri.setWriter(vo.getUserVO().getUsersNo());
		List<ReviewVO> list = userService.userReviewList(cri);
		model.addAttribute("reviewList", list);
		//페이징
		int total = userService.reviewGetTotal(cri);
		model.addAttribute("page", new UserRvwPageDTO(cri, total));
		
		
		return "usr/userReviewList";
	}
	
	
	//사용자 의뢰 목록 조회
	@GetMapping("/reqList")
	public String userReqList(Model model,UserReqCriteria cri, RequestVO requestVO, @AuthenticationPrincipal LoginUserVO vo) {
		model.addAttribute("userVO", vo.getUserVO());
		
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		cri.setUsersNo(vo.getUserNo());
		List<RequestVO> list = userService.userReqList(cri);
		model.addAttribute("requestList", list);
		//페이징
		int total = userService.requestGetTotal(cri);
		model.addAttribute("page", new UserReqPageDTO(cri, total));
		
		return "usr/userRequestList";
	}
	
	//사용자 탈퇴 (상태 수정) 페이지
	@GetMapping("/quit")
	public String userStateUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userStateUpdate";
	}
	
	//사용자 탈퇴 기능 (상태 수정)
	@PostMapping("/quit")
	@ResponseBody
	public boolean userStateUpdate(@RequestBody UserVO userVO) {
		//userService.userStateUpdate(userVO);
		return userService.userStateUpdate(userVO);
	}
}
