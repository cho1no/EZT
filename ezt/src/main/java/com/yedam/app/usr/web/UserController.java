package com.yedam.app.usr.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@Autowired
	UserMapper userMapper;
	
	//정보조회
	@GetMapping("user/info")
	public String userInfo(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userInfo";
	}
	
	//정보수정 -페이지
	@GetMapping("user/update")
	public String userUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userVO", vo.getUserVO());
		return "usr/userUpdate";
	}
	
	//정보수정 기능 
	@PostMapping("user/update")
	@ResponseBody
	public Map<String, Object> userUpdate(@RequestBody UserVO userVO, @AuthenticationPrincipal LoginUserVO vo){
		Map<String, Object> result = userService.updateUser(userVO);
		UserVO uvo = userMapper.selectUserInfo(userVO.getUsersId());
		vo.setUserVO(uvo);
		return result;
	}

	//비밀번호 변경 -페이지
	@GetMapping("user/pwUpdate")
	public String userPwchangeForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userPwUpdate";
	}
	
	//비밀번호 변경 기능
	@PostMapping("user/pwUpdate")
	@ResponseBody
	public boolean userPwUpdate(@RequestBody UserVO userVO) {
		return userService.updateUserPw(userVO);
	}
	
	//사용자 후기목록 조회
	@GetMapping("user/revList")
	public String userReviewList(Model model, @AuthenticationPrincipal LoginUserVO user, @PageableDefault(size = 5) Pageable pageable) {
		List<ReviewVO> list = userService.userReviewList(user.getUserNo());
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));		
		model.addAttribute("reviewList", list);
		
//		Page<ReviewVO> reviews = 
		
		//페이징
//		int total = userService.getTotal(cri);
//		model.addAttribute("page", new UserPageDTO(cri, total));
		return "usr/userReviewList";
	}
	
	//사용자 의뢰 목록 조회
	@GetMapping("user/reqList")
	public String userReqList(Model model, @AuthenticationPrincipal LoginUserVO user) {
		List<RequestVO> list = userService.userReqList(user.getUserNo());
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("requestList", list);
		return "usr/userRequestList";
	}
	
	
	//사용자 탈퇴 (상태 수정) 페이지
	@GetMapping("user/quit")
	public String userStateUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userStateUpdate";
	}
	
	//사용자 탈퇴 기능 (상태 수정)
	@PostMapping("user/quit")
	@ResponseBody
	public boolean userStateUpdate(@RequestBody UserVO userVO) {
		//userService.userStateUpdate(userVO);
		return userService.userStateUpdate(userVO);
	}
}
