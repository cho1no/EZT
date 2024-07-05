package com.yedam.app.pay.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.pay.service.PayService;
import com.yedam.app.pay.service.PayVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class PayController {

	@Autowired
	ContractService conService;

	@Autowired
	ProposalService ppsSerivce;

	@Autowired
	PayService payService;

	// 결제 등록 조회
	@GetMapping("pay")
	public String pay(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);
		model.addAttribute("con", findVO);

		// 유저 정보
		UserVO worker = ppsSerivce.userInfo(findVO.getWorkerInfo());
		model.addAttribute("worker", worker);
		UserVO requester = ppsSerivce.userInfo(findVO.getRequesterInfo());
		model.addAttribute("requester", requester);

		// 견적서 정보 조회
		ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());
		model.addAttribute("ppsInfo", ppsVO);

		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		return "doc/payment";
	}

	@PostMapping("payment")
	@ResponseBody
	public int payInsert(PayVO payVO) {
		int no = payService.payInsert(payVO);

		return no;
	}

	// 결제 명세서
	@GetMapping("payInfo")
	public String payInfo(PayVO payVO, Model model, @AuthenticationPrincipal LoginUserVO user) {
		// 결제 정보 조회
		PayVO pno = payService.payInfo(payVO);
		ContractVO contractVO = new ContractVO();
		contractVO.setProposalNo(pno.getProposalNo());
		
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);
		model.addAttribute("con", findVO);

		// 유저 정보
		UserVO worker = ppsSerivce.userInfo(findVO.getWorkerInfo());
		model.addAttribute("worker", worker);
		UserVO requester = ppsSerivce.userInfo(findVO.getRequesterInfo());
		model.addAttribute("requester", requester);

		// 견적서 정보 조회
		ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());
		model.addAttribute("ppsInfo", ppsVO);

		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		return "doc/payInfo";
	}
}
