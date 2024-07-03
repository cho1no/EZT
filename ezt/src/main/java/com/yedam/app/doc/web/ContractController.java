package com.yedam.app.doc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.doc.service.SignsVO;
import com.yedam.app.fie.service.FileService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class ContractController {

	@Autowired
	ContractService conService;

	@Autowired
	ProposalService ppsSerivce;
	
	@Autowired
	FileService fileService;

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

	@PostMapping(value = "/conInsert" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadAjaxPost(MultipartFile[] uploadFile, ContractVO contractVO) {

		List<FileVO> list = fileService.uploadFiles(uploadFile);
		if(!list.isEmpty()) {
		contractVO.setFileList(list);
		}
		conService.conInsert(contractVO);

		return new ResponseEntity<>("String", HttpStatus.OK);
	}
	

	// 계약서 상세
	@GetMapping("conInfo")
	public String conInfo(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {
		
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
		
		return "doc/contractInfo";

	}
	
	// 계약서 수정
		@GetMapping("conUpdate")
		public String conUpdate(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {
			
			// 계약서 정보 조회
			ContractVO findVO = conService.conInfo(contractVO);
			model.addAttribute("con", findVO);
			
			// 유저 정보
			UserVO worker = ppsSerivce.userInfo(findVO.getWorkerInfo());
			model.addAttribute("worker", worker);
			UserVO requester = ppsSerivce.userInfo(findVO.getRequesterInfo());
			model.addAttribute("requester", requester);
			model.addAttribute("user", user.getUserNo());
			
			
			ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());
			
			// 의뢰 정보 조회
			RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
			model.addAttribute("reqInfo", reqVO);
			
			return "doc/contractUpdate";

		}
		
		@PostMapping(value = "/conUpdate" , produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> uploadAjaxPostUpdate(MultipartFile[] uploadFile, ContractVO contractVO) {

			List<FileVO> list = fileService.uploadFiles(uploadFile);
			if(!list.isEmpty()) {
			contractVO.setFileList(list);
			}
			conService.conUpdate(contractVO);

			return new ResponseEntity<>("String", HttpStatus.OK);
		}
	

}
