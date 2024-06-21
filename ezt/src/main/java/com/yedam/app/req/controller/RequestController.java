package com.yedam.app.req.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;

@Controller
public class RequestController {
	@Autowired
	RequestService requestService;
	
	//전체조회
	@GetMapping("requestList")
	public String requestList(Model model) {
		List<RequestVO> list = requestService.requestList();
		model.addAttribute("requestList", list);
		
		return "request/requestList";
	}
	//단건조회
	
	//등록
	
	//수정
	
	//삭제
}
