package com.yedam.app.req.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.common.service.SimpleFileService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.fie.service.FileService;
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
	@Autowired
	FileService fileService;
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
		if(findVO.getFileVO().isEmpty()) {
			findVO.setFileVO(null);
		}
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
	@ResponseBody
	public int requestInsert(MultipartFile[] uploadFile, RequestVO requestVO) {
		if (uploadFile != null && uploadFile.length > 0) {
			List<FileVO> list = fileService.uploadFiles(uploadFile);
			int i = 0;
			for(FileVO e : list) {
				if( i == 0) {
					e.setBossTf("Y");
				}else {
					e.setBossTf("N");
				}
				i+= 1;
			}
			if (!list.isEmpty()) {
				requestVO.setFileVO(list);
				
			}
			
		}	
		System.out.println(requestVO);
		requestService.insertRequest(requestVO);
		return 1;
	}
	
	// 첨부 파일 업로드
		@PostMapping("/fileInsert")
		@ResponseBody
		public List<FileVO> rptFileInsert(MultipartFile[] uploadFile) {

			List<FileVO> list = fileService.uploadFiles(uploadFile);

			return list;
		}
	
	// 수정
	@PostMapping("/update")
	@ResponseBody
	public boolean requestUpdate(@RequestBody RequestVO requestVO) {
		
		
		return requestService.updateRequest(requestVO);
	}
	
	// 삭제
	@GetMapping("/delete")
	@ResponseBody
	public int requestDelet(Integer requestNo) {
		RequestVO requestVO = new RequestVO(); 
		requestVO.setRequestNo(requestNo);
		RequestVO findVO = requestService.requestInfo(requestVO);
		 
		requestService.deleteRequest(requestNo);
		if(findVO.getFileId() != 0) {
			requestService.deleteFile(findVO.getFileId());
		}
		
		
		
		return 1;
	}
	
	// 첨부 파일 삭제
	@PostMapping("/fileDelete")
	@ResponseBody
	public ResponseEntity<String> fileDelete(@RequestBody List<FileVO> fileVO) throws UnsupportedEncodingException {

		fileService.deleteFile(fileVO);
		List<FileVO> sFileVO = new ArrayList<FileVO>();
		System.out.println("=======" + fileVO);

		for (FileVO file : fileVO) {
			file.setSaveName("s_" + file.getSaveName());
			sFileVO.add(file);
		}
		fileService.deleteFile(sFileVO);

		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}

}
