package com.yedam.app.doc.web;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class ProposalController {

	@Autowired
	ProposalService ppsSerivce;

	// 견적서 단건조회
	@GetMapping("ppsInfo")
	public String ppsInfo(RequestVO requestVO, ProposalVO proposalVO, Model model) {
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO);
		model.addAttribute("ppsInfo", findVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(findVO.getWorker());
		model.addAttribute("userInfo", usrVO);
		
		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);
		model.addAttribute("reqInfo", reqVO);
		return "doc/proposalInfo";
	}

	// 견적서 등록
	@GetMapping("ppsInsert")
	public String ppsInsert(RequestVO requestVO, Model model, Principal principal) {
		model.addAttribute("proposalVO", new ProposalVO());
		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);
		model.addAttribute("reqInfo", reqVO);
		
		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(Integer.parseInt(principal.getName()));
		model.addAttribute("userInfo", usrVO);

		return "doc/proposalInsert";
	}

	@PostMapping("ppsInsert")
	public String ppsInsertProcess(ProposalVO proposalVO, RequestVO requestVO) {

		int no = ppsSerivce.ppsInsert(proposalVO);
		String url = null;

		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);

		if (no > -1) {
			url = "redirect:ppsInfo?proposalNo=" + no + "&requestNo=" + reqVO.getRequestNo();
		} else {
			url = "redirect:ppsInsert?requestNo=" + reqVO.getRequestNo();
		}

		return url;
	}
	
	@GetMapping("ppsUpdate")
	public String ppsUpdate(RequestVO requestVO, ProposalVO proposalVO, Model model) {
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO);
		model.addAttribute("ppsInfo", findVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(findVO.getWorker());
		model.addAttribute("userInfo", usrVO);
		
		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);
		model.addAttribute("reqInfo", reqVO);
		return "doc/proposalUpdate";
	} 
	
	@PostMapping("ppsUpdate")
	public String ppsUpdateJSON(ProposalVO proposalVO){
		int no = ppsSerivce.ppsUpdate(proposalVO);
		return "redirect:ppsInfo?proposalNo=" + no + "&requestNo=" + proposalVO.getRequestNo();
	}
	
	// 견적서 삭제
	@GetMapping("ppsDelete")
	public String ppsDelete(Integer proposalNo) {
		ppsSerivce.ppsDelete(proposalNo);
		return "redirect:main";
	}
	

}
