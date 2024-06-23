package com.yedam.app.doc.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;

@Controller
public class ProposalController {
	
	@Autowired
	ProposalService ppsSerivce;
	
	// 견적서 단건조회
	@GetMapping("ppsInfo")
	public String ppsInfo(ProposalVO proposalVO, Model model) {
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO);
		model.addAttribute("ppsInfo", findVO);
		
		// 견적서 의뢰정보조회
		ProposalVO reqVO = ppsSerivce.reqInfo(proposalVO);
		model.addAttribute("reqInfo", reqVO);
		return "doc/proposalInfo";
	}
	
	// 견적서 등록
	@GetMapping("ppsInsert")
	public String ppsInsert(ProposalVO proposalVO, Model model, Principal principal) {
		model.addAttribute("proposalVO", new ProposalVO());
		// 견적서 의뢰정보조회
		ProposalVO reqVO = ppsSerivce.reqInfo(proposalVO);
		model.addAttribute("reqInfo", reqVO);
		
		System.out.println(principal.getName());
		return "doc/proposalInsert";
	}
	
	@PostMapping("ppsInsert")
	public String ppsInsertProcess(ProposalVO proposalVO) {
		int no = ppsSerivce.ppsInsert(proposalVO);
		String url = null;
		
		// 견적서 의뢰정보조회
		ProposalVO reqVO = ppsSerivce.reqInfo(proposalVO);

		if (no > -1) {
			url = "redirect:ppsInfo?proposalNo=" + no + "&requestNo=" + reqVO.getRequestNo();
		} else {
			url = "redirect:ppsInsert";
		}
		return url;
	}
}
