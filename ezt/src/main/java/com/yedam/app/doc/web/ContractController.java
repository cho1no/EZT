package com.yedam.app.doc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class ContractController {

	@Autowired
	ContractService conService;

	@Autowired
	ProposalService ppsSerivce;

	// 계약서 등록
	@GetMapping("conInsert")
	public String conInsert(ProposalVO proposalVO, Model model, @AuthenticationPrincipal LoginUserVO user) {

		model.addAttribute("contractVO", new ContractVO());

		// 유저 정보
		model.addAttribute("userName", user.getUserVO().getUsersName());
		model.addAttribute("userPhone", user.getUserVO().getUsersPhone());
		model.addAttribute("userRnn", user.getUserVO().getUsersRnn());

		// 견적서 정보 조회
		ProposalVO ppsVO = ppsSerivce.ppsInfo(proposalVO.getProposalNo());
		model.addAttribute("ppsInfo", ppsVO);

		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		return "doc/contractInsert";
	}

	@PostMapping("conInsert")
	public String conInsertProcess(ContractVO contractVO) {

		int no = conService.conInsert(contractVO);
		String url = null;

		if (no > -1) {
			url = "redirect:main";
		} else {
			url = "redirect:conInsert?proposalNo=" + contractVO.getProposalNo();
		}

		return url;
	}

	// 계약서 상세
	@GetMapping("conInfo")
	public String conInfo(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {
		
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);
		model.addAttribute("conInfo", findVO);
		
		// 유저 정보
		UserVO worker = ppsSerivce.userInfo(findVO.getWorkerInfo());
		model.addAttribute("worker", worker);
		UserVO requester = ppsSerivce.userInfo(findVO.getRequesterInfo());
		model.addAttribute("requester", requester);
		
		ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());
		
		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);
		
		return "doc/contractInfo";

	}

}
