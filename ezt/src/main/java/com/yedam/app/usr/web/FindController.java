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
import com.yedam.app.usr.mapper.FindWorkerMapper;
import com.yedam.app.usr.service.FindWorkerCriteria;
import com.yedam.app.usr.service.FindWorkerPageDTO;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.PortfolioVO;
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
	
	@Autowired
	FindWorkerMapper fwMap;
	
	//작업자 찾기페이지(목록)
	@GetMapping("/workerList")
	public String findWorker(Model model, FindWorkerCriteria cri) {
		List<UserVO> list = userService.selectFindWorkerList(cri);
		
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));	
		for (UserVO user : list) {
			user.setCategoryCode(workerService.selectCategoryInfo(user.getUsersNo()));
			user.setRegionCode(workerService.selectRegionInfo(user.getUsersNo()));
		}
		model.addAttribute("workerList", list);
		
		//페이징
		int total = userService.workerListGetTotal(cri);
		model.addAttribute("page", new FindWorkerPageDTO(cri, total));
		
		return "usr/findWorkerList";
	}
	
	//작업자 상세
	@GetMapping("/workerMoreInfo")
	public String workerInfo(@RequestParam int usersNo, Model model) {
		// 유저찾기
		UserVO vo =  adminService.getUser(usersNo);
		
		// 카테고리, 지역 코드
		model.addAttribute("categories", workerService.selectCategoryInfo(vo.getUsersNo()));
		model.addAttribute("regions", workerService.selectRegionInfo(vo.getUsersNo()));
		// 작업자 정보
		model.addAttribute("userInfo", vo);
		
		model.addAttribute("careers", fwMap.selectCareers(usersNo));
		model.addAttribute("licenses", fwMap.selectLicenes(usersNo));
		model.addAttribute("avgReview", fwMap.avgReview(usersNo));
		model.addAttribute("cntReview", fwMap.countReview(usersNo));
		model.addAttribute("portfolioList", fwMap.selectPortFiles(usersNo));
		model.addAttribute("reviewList", fwMap.selectReviews(usersNo));
		
		return "usr/findWorkerMoreInfo"; 
	}
	
	//작업자 포트폴리오 상세
	@GetMapping("/workerPortfolio")
	public String workerPortfolio(@RequestParam int portfolioNo,
								  Model model) {
		
		PortfolioVO pvo = fwMap.selectPortfolioInfo(portfolioNo);
		
		model.addAttribute("portfolioInfo", pvo);
		model.addAttribute("userInfo",adminService.getUser(pvo.getUsersNo()));
		model.addAttribute("categories", workerService.selectCategoryInfo(pvo.getUsersNo()));
		model.addAttribute("portFiles", fwMap.selectPortInfoFiles(pvo.getFileId()));
		return "wkr/workerPortfolioInfo";
	}
}
