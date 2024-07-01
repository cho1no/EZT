package com.yedam.app.wkr.web;

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
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WorkerController {
	@Autowired
	WorkerService workerService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@Autowired
	UserMapper userMapper;
	
	//정보조회
	@GetMapping("worker/info")
	public String workerInfo(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
//		log.info(workerService.selectCategoryInfo(vo.getUserNo()).toString());
		model.addAttribute("categories", workerService.selectCategoryInfo(vo.getUserNo()));
		model.addAttribute("regions", workerService.selectRegionInfo(vo.getUserNo()));
		return "wkr/workerInfo";
	}
	
	//정보수정 -페이지
	@GetMapping("worker/update")
	public String workerUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("cate", commonCodeService.selectCommonCodeAll("0C"));
    	model.addAttribute("regi", commonCodeService.selectCommonCodeAll("0B"));
		model.addAttribute("userVO", vo.getUserVO());
		model.addAttribute("categories", workerService.selectCategoryInfo(vo.getUserNo()));
		model.addAttribute("regions", workerService.selectRegionInfo(vo.getUserNo()));
		return "wkr/workerUpdate";
	}
	
	//정보수정 기능
	@PostMapping("worker/update")
	@ResponseBody
	public Map<String, Object> workerUpdate(@RequestBody UserVO userVO, @AuthenticationPrincipal LoginUserVO vo){
		Map<String, Object> result = workerService.updateWorker(userVO);
		UserVO uvo = userMapper.selectUserInfo(userVO.getUsersId());
		vo.setUserVO(uvo);
		return result;
	}
	
	//비밀번호 변경 -페이지
	@GetMapping("worker/pwUpdate")
	public String userPwchangeForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "wkr/workerPwUpdate";
	}
	
	//비밀번호 변경 기능
	@PostMapping("worker/pwUpdate")
	@ResponseBody
	public boolean userPwUpdate(@RequestBody UserVO userVO) {
		return workerService.updateWorkerPw(userVO);
	}
	
	
	
	//작업자 탈퇴 (상태 수정) 페이지
	@GetMapping("worker/quit")
	public String workerStateUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
		model.addAttribute("userId", vo.getUserVO());
		return "wkr/workerStateUpdate";
	}
	
	//작업자 탈퇴 기능 (상태 수정)
	@PostMapping("worker/quit")
	@ResponseBody
	public boolean workerStateUpdate(@RequestBody UserVO userVO) {
		workerService.workerStateUpdate(userVO);
		return workerService.workerStateUpdate(userVO);
	}
	
	
	
}
