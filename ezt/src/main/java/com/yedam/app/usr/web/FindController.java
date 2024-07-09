package com.yedam.app.usr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedam.app.adm.service.AdminService;
import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.usr.service.FindWorkerCriteria;
import com.yedam.app.usr.service.FindWorkerPageDTO;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.PortfolioVO;
import com.yedam.app.wkr.service.WorkerPFCriteria;
import com.yedam.app.wkr.service.WorkerRvwCriteria;
import com.yedam.app.wkr.service.WorkerService;

@Controller
@RequestMapping("find")
public class FindController {
	@Autowired
	CommonCodeService commonCodeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	WorkerService workerService;
	
	@Autowired
	AdminService adminService;
	
	//작업자 찾기
	@GetMapping("/workerList")
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
	@GetMapping("/workerMoreInfo")
	public String workerInfo(@RequestParam int usersNo, Model model) {
		UserVO vo =  adminService.getUser(usersNo);
		WorkerPFCriteria wpc = new WorkerPFCriteria();
		wpc.setUsersNo(usersNo);
		List<PortfolioVO> pvo = workerService.selectWorkerPortfolioList(wpc);
		WorkerRvwCriteria wrc = new WorkerRvwCriteria();
		wrc.setUsersNo(usersNo);
		List<ReviewVO> rev = workerService.selectWorkerReviewList(wrc);
		model.addAttribute("userInfo", vo);
		model.addAttribute("revList", rev);
		model.addAttribute("pfList", pvo);
		return "usr/findWorkerMoreInfo"; 
	}
}
