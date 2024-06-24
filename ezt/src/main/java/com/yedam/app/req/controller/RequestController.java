package com.yedam.app.req.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;

@Controller
public class RequestController {
	@Autowired
	RequestService requestService;	
	@Autowired
	CommonCodeService commonCodeService;

	// 전체조회
	@GetMapping("requestList")
	public String requestList(Model model) {
		List<RequestVO> list = requestService.requestList();
		model.addAttribute("requestList", list);

		return "req/requestList";
	}

//단건조회
	// 의뢰
	@GetMapping("requestInfo")
	public String boardInfo(RequestVO requestVO, Model model) {

		
		RequestVO findVO = requestService.requestInfo(requestVO);
		System.out.println(findVO);
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("cttPlaceSituation", commonCodeService.selectCommonCodeAll("0H"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		model.addAttribute("request", findVO);
		
		return "req/requestInfo";
	}
	// 견적서

	// 계약서

	// 등록

	// 수정

	// 삭제
}
