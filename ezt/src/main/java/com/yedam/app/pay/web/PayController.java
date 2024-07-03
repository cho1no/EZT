package com.yedam.app.pay.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class PayController {

	@Autowired
	ContractService conService;

	@Autowired
	ProposalService ppsSerivce;

	@GetMapping("payInsert")
	public String pay(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);
		model.addAttribute("con", findVO);

		// 유저 정보
		UserVO worker = ppsSerivce.userInfo(findVO.getWorkerInfo());
		model.addAttribute("worker", worker);
		UserVO requester = ppsSerivce.userInfo(findVO.getRequesterInfo());
		model.addAttribute("requester", requester);

		ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());

		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		return "doc/payInsert";
	}
}
