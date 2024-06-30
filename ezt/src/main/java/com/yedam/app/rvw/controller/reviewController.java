package com.yedam.app.rvw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.PageDTO;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;

@Controller
public class reviewController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	CommonCodeService commonCodeService;
	//리뷰 전체조회
	
	@GetMapping("reviewList")
	public String reviewList(Criteria cri, Model model) {
		List<ReviewVO> list = reviewService.reviewList(cri);
		model.addAttribute("rvList", list);
		
		//페이징
		int total = reviewService.getTotal(cri);
		model.addAttribute("page", new PageDTO(cri, total));
		
		return "rvw/reviewList";
	}
	
	//소규모 리뷰 상세조회
	@GetMapping("smallrvInfo")
	public String smRvInfo(ReviewVO reviewVO, Model model) {
		ReviewVO findVO = reviewService.reviewInfo(reviewVO);
		
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		model.addAttribute("review",findVO);
		return "rvw/smallrvInfo";
	}
	
	
	//등록
	
	
	//수정
	@PostMapping("reviewUpdate")
	@ResponseBody
	public boolean reviewUpdate(@RequestBody ReviewVO reviewVO) {
		return reviewService.updateReview(reviewVO);
	}
	
	//삭제
	@GetMapping("reviewDelete")
	public String reviewDelete(Integer reviewNo) {
		reviewService.deleteReview(reviewNo);
		return "redirect:reviewList";
	}
	
}
