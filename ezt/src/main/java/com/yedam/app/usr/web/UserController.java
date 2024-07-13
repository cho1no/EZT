package com.yedam.app.usr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.yedam.app.alm.web.StompAlarmController;
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
	
	@Autowired StompAlarmController sac;
	
	@Autowired PasswordEncoder passworderEncoder;
	
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
	public String userUpdate(@RequestBody UserVO userVO, @AuthenticationPrincipal LoginUserVO vo){
		userService.updateUser(userVO);
		UserVO uvo = userMapper.selectUserInfo(userVO.getUsersId());
		vo.setUserVO(uvo);
		return "redirect: /user/info";
	}

	//비밀번호 변경 -페이지
	@GetMapping("/pwUpdate")
	public String userPwchangeForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "usr/userPwUpdate";
	}
	
	//비밀번호 변경 - 기능
	@PostMapping("/pwUpdate")
	public String pwUpdate(@RequestParam("currentPw") String currentPw,
	                       @RequestParam("usersNewPw") String usersNewPw,
	                       @AuthenticationPrincipal LoginUserVO loginUser,
	                       Model model,
	                       SessionStatus sessionStatus) {

	    if (loginUser == null || loginUser.getUserNo() == null) {
	        model.addAttribute("msg", "로그인 필요!");
	        model.addAttribute("icon", "warning");
	        model.addAttribute("url", "/login");
	        return "gongtong/message";
	    }

	    // 비밀번호 변경
	    int result = userService.updatePw(loginUser.getUserNo(), currentPw, usersNewPw);

	    if (result > 0) {
	        sessionStatus.setComplete(); // 세션 정리
	        model.addAttribute("msg", "비밀번호 변경완료!");
	        model.addAttribute("icon", "success");
	        model.addAttribute("url", "/user/info");
	        return "gongtong/message"; // 성공 페이지로 리디렉션
	    } else {
 	        model.addAttribute("msg", "비밀번호가 맞지않습니다");
 	        model.addAttribute("icon", "warning");
 	        model.addAttribute("url", "/user/pwUpdate"); // 다시 비밀번호 변경 페이지로
 	        return "gongtong/message"; 
	    }
	}
	
	//사용자 후기목록 조회
	@GetMapping("/revList")
	public String userReviewList(Model model, UserRvwCriteria cri, @AuthenticationPrincipal LoginUserVO vo) {
		model.addAttribute("userVO", vo.getUserVO());
		
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));		
		cri.setWriter(vo.getUserVO().getUsersNo());
		if (cri.getKeyword() == null) {
			cri.setKeyword("");
		}
		if (cri.getType() == null) {
			cri.setType("");
		}
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
		model.addAttribute("userVO", vo.getUserVO());
		return "usr/userStateUpdate";
	}
	
	//사용자 탈퇴 기능 (상태 수정)
	@PostMapping("/quit")
	public String userStateUpdate(UserVO userVO,  @AuthenticationPrincipal LoginUserVO vo, Model model) {
		userService.userStateUpdate(userVO);
		
		UserVO uvo = userMapper.selectUserInfo(userVO.getUsersId());
		vo.setUserVO(uvo);
		model.addAttribute("msg", "탈퇴됨!");
	    model.addAttribute("url", "/main");
		return "gongtong/message";
	}
}
