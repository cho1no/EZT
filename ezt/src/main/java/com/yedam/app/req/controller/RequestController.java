package com.yedam.app.req.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class RequestController {
	@Autowired
	RequestService requestService;	
	@Autowired
	CommonCodeService commonCodeService;
	@Autowired
	ProposalService proposalService;

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
	public String requestInfo(RequestVO requestVO,ProposalVO proposalVO, Model model) {

		//의뢰 목록조회
		RequestVO findVO = requestService.requestInfo(requestVO);

		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("cttPlaceSituation", commonCodeService.selectCommonCodeAll("0H"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		model.addAttribute("request", findVO);
		
		// 견적서 목록 조회
		List<ProposalVO> list = requestService.proposalList(proposalVO);

		model.addAttribute("proposalList", list);

		//견적서 상세보기 조회
		
		//계약서 조회
		
		return "req/requestInfo";
	}

	// 등록
	@GetMapping("requestInsert")
	public String requestInsert(Model model, Principal principal) {
		//의뢰 등록
		model.addAttribute("requestVO", new RequestVO());
		
		//공통코드 정보
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("cttPlaceSituation", commonCodeService.selectCommonCodeAll("0H"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		//유저 정보 조회
		UserVO userVO = requestService.userInfo(Integer.parseInt(principal.getName()));
		model.addAttribute("user", userVO);
		
		return "req/requestInsert";
	}
	
	@PostMapping("requestInsert")
	public String requestInsert(RequestVO requestVO) {
		requestService.insertRequest(requestVO);
		return "redirect:requestList";
	}
	// 수정

	@PostMapping("requestUpdate")
	@ResponseBody
	public boolean requestUpdateJASON(@RequestBody RequestVO requestVO) {
		return requestService.updateRequest(requestVO);
	}
	// 삭제
	@GetMapping("requestDelete")
	public String requestDelet(Integer requestNo) {
		requestService.deleteRequest(requestNo);
		return "redirect:requestList";
	}
}
