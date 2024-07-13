package com.yedam.app.req.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.PageDTO;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;

import lombok.extern.slf4j.Slf4j;

//의뢰 CRUD
@RequestMapping("request")
@Controller
@Slf4j
public class RequestController {
	@Autowired
	RequestService requestService;	
	@Autowired
	CommonCodeService commonCodeService;

	// 전체조회
	@GetMapping("/list")
	public String requestList(Criteria cri , Model model) {
		log.info(cri.toString());
		List<RequestVO> list = requestService.requestList(cri);
		model.addAttribute("requestList", list);
		
		//페이징
		int total = requestService.getTotal(cri);
		model.addAttribute("page", new PageDTO(cri, total));
		return "req/requestList";
	}

//단건조회
	// 의뢰
	@GetMapping("/info")
	public String requestInfo(RequestVO requestVO,
			                  ContractVO contractVO,
			                  ProposalVO proposalVO,
			                  Model model,
			                  @AuthenticationPrincipal LoginUserVO user) {

		//의뢰 단건조회
		RequestVO findVO = requestService.requestInfo(requestVO);
		model.addAttribute("request", findVO);

		//공통코드
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("cttPlaceSituation", commonCodeService.selectCommonCodeAll("0H"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		
		// 견적서 목록 조회
		List<ProposalVO> list = requestService.proposalList(proposalVO);
		model.addAttribute("proposalList", list);

//		//계약서 조회
		ContractVO findContractVO = requestService.contractInfo(contractVO);
		model.addAttribute("contract",findContractVO);
		// 공사 보고 조회
		model.addAttribute("cttReports", requestService.cttReportList(requestVO.getRequestNo()));
		// 팀원 조회
		model.addAttribute("members", requestService.memberList(requestVO.getRequestNo()));
		
		// 로그인 중 유저 번호
		if(user != null) {
		model.addAttribute("userNo", user.getUserNo());
		}
		// 계약한 작업자 번호
		if(findContractVO != null) {
			model.addAttribute("workerNo", findContractVO.getWorkerInfo());
		}
		return "req/requestInfo";
	}

	// 등록
	@GetMapping("/insert")
	public String requestInsert(Model model) {
		//의뢰 등록
		model.addAttribute("requestVO", new RequestVO());
		
		//공통코드 정보
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("cttPlaceSituation", commonCodeService.selectCommonCodeAll("0H"));
		model.addAttribute("requestState", commonCodeService.selectCommonCodeAll("0R"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		
		
		return "req/requestInsert";
	}
	
	@PostMapping("/insert")
	public String requestInsert(RequestVO requestVO) {
		requestService.insertRequest(requestVO);
		return "redirect:list";
	}
	
	// 수정
	@PostMapping("/update")
	@ResponseBody
	public boolean requestUpdate(@RequestBody RequestVO requestVO) {
		return requestService.updateRequest(requestVO);
	}
	
	// 삭제
	@GetMapping("/delete")
	public String requestDelet(Integer requestNo) {

		requestService.deleteRequest(requestNo);

		
		return "redirect:list";
	}

}
