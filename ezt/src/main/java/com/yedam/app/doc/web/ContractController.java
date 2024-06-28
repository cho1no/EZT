package com.yedam.app.doc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.doc.service.ContractVO;

@Controller
public class ContractController {
	
	
	@GetMapping("conInsert")
	public String conInsert(ContractVO contractVO) {
		return "doc/contractInsert";
	}
}
