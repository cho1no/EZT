package com.yedam.app.rvw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;

@Controller
public class reviewController {
	@Autowired
	ReviewService reviewService;
	
	//리뷰 전체조회
	
	@GetMapping("reviewList")
	public String reviewList(Model model) {
		List<ReviewVO> list = reviewService.reviewList();
		model.addAttribute("rvList", list);
		
		return "rvw/reviewList";
	}
}
