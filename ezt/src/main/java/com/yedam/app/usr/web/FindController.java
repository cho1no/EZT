package com.yedam.app.usr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.usr.service.FindWorkerCriteria;
import com.yedam.app.usr.service.FindWorkerPageDTO;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.WorkerService;

@Controller
public class FindController {
	@Autowired
	CommonCodeService commonCodeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	WorkerService workerService;
	
	//작업자 찾기
	@GetMapping("/find/workerList")
	public String findWorker(Model model, FindWorkerCriteria cri) {
//		model.addAttribute("cate", commonCodeService.selectCommonCodeAll("0C"));
//	    model.addAttribute("regi", commonCodeService.selectCommonCodeAll("0B"));
		List<UserVO> list = userService.selectFindWorkerList(cri);
		for (UserVO user : list) {
			user.setCategoryCode(workerService.selectCategoryInfo(user.getUsersNo()));
			user.setRegionCode(workerService.selectRegionInfo(user.getUsersNo()));
		}
		model.addAttribute("workerList", list);
//		model.addAttribute("categories", workerService.selectCategoryInfo(cri.getUsersNo()));
//	    model.addAttribute("regions", workerService.selectRegionInfo(cri.getUsersNo()));
		
		//페이징
		int total = userService.workerListGetTotal(cri);
		model.addAttribute("page", new FindWorkerPageDTO(cri, total));
		return "usr/findWorkerList";
	}
	
	//작업자 상세
	@GetMapping("/find/workerMoreInfo")
	public String workerInfo(Model model) {
		return "usr/findWorkerMoreInfo"; 
	}
}
