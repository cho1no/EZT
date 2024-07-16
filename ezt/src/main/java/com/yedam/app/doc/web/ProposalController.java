package com.yedam.app.doc.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.alm.service.AlarmVO;
import com.yedam.app.alm.web.StompAlarmController;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.fie.service.FileService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProposalController {

	@Autowired
	ProposalService ppsSerivce;
	
	@Autowired
	FileService fileService;
	
	@Autowired 
	StompAlarmController sac;
	
	// 견적서 단건조회
	@GetMapping("ppsInfo")
	public String ppsInfo(ProposalVO proposalVO
						  , Model model) {
		// 견적서 정보 조회
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO.getProposalNo());
		if(findVO.getList().isEmpty()) {
			findVO.setList(null);
		}
		model.addAttribute("ppsInfo", findVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(findVO.getWorker());
		model.addAttribute("userInfo", usrVO);

		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(findVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		return "doc/proposalInfo";
	}

	// 견적서 등록
	@GetMapping("ppsInsert")
	public String ppsInsert(ProposalVO proposalVO
							, RequestVO requestVO
							, Model model
							, @AuthenticationPrincipal LoginUserVO user) {
		model.addAttribute("proposalVO", new ProposalVO());
		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(user.getUserVO().getUsersNo());
		model.addAttribute("userInfo", usrVO);

		return "doc/proposalInsert";
	}

	@PostMapping("ppsInsert")
	public String ppsInsertProcess(ProposalVO proposalVO
								   , RequestVO requestVO
								   , Model model) {

		int no = ppsSerivce.ppsInsert(proposalVO);

		if (no > -1) {
			
			model.addAttribute("msg", "견적서 등록 완료");
		    model.addAttribute("icon", "success");
		    model.addAttribute("url", "/ppsInfo?proposalNo=" + no);
		    return "gongtong/message";
		} else {
			model.addAttribute("msg", "견적서 등록 실패");
 	        model.addAttribute("icon", "warning");
 	        model.addAttribute("url", "/ppsInsert?requestNo=" + proposalVO.getRequestNo());
 	        return "gongtong/message"; 
		}
	}

	// 견적서 수정
	@GetMapping("ppsUpdate")
	public String ppsUpdate(RequestVO requestVO
							, ProposalVO proposalVO
							, Model model
							, @AuthenticationPrincipal LoginUserVO user) {
		// 견적서 정보 조회
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO.getProposalNo());
		model.addAttribute("ppsInfo", findVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(user.getUserVO().getUsersNo());
		model.addAttribute("userInfo", usrVO);

		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(findVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);
		return "doc/proposalUpdate";
	}

	// 견적서 수정
	@PostMapping("ppsUpdate")
	public String ppsUpdate(ProposalVO proposalVO) {
		int no = ppsSerivce.ppsUpdate(proposalVO);
		return "redirect:ppsInfo?proposalNo=" + no;
	}

	// 견적서 삭제
	@GetMapping("ppsDelete")
	public String ppsDelete(Integer proposalNo
							, Model model) {
		ppsSerivce.ppsDelete(proposalNo);
		
		model.addAttribute("msg", "견적서 삭제 완료");
	    model.addAttribute("icon", "success");
	    model.addAttribute("url", "/main");
	    return "gongtong/message";
	}
	
	// 파일 업로드
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadAjaxPost(MultipartFile[] uploadFile
												, ProposalVO proposalVO) {
		if(uploadFile != null && uploadFile.length > 0) {
			List<FileVO> list = fileService.uploadFiles(uploadFile);
			int i = 0;
			for (FileVO e : list) {
				if (i == 0) {
					e.setBossTf("Y");
				} else {
					e.setBossTf("N");
				}
				i += 1;
			}
			if (!list.isEmpty()) {
			// proposalVO에 값 넣기
			proposalVO.setFileList(list);
			}
		}
		ppsSerivce.ppsFileUpdate(proposalVO);
		
		// 견적서 정보 조회
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO.getProposalNo());
						
		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(findVO.getRequestNo());
		
		AlarmVO alarm = new AlarmVO();
		alarm.setUsersNo(findVO.getRequester());
		alarm.setTitle("등록된 견적서가 있습니다");
		alarm.setContent("["+ reqVO.getTitle() + "] 에서 견적서를 확인해 주세요.");
		sac.message(alarm);

		return new ResponseEntity<>("true", HttpStatus.OK);
	}
	// 파일 다운로드
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(String fileName) {

		return fileService.downlodeFile(fileName);
	}
	
	// 파일 삭제
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(@RequestBody List<FileVO> fileVO
											, ProposalVO proposalVO)
			throws UnsupportedEncodingException {

		fileService.deleteFile(fileVO);
		ppsSerivce.ppsFileDelete(fileVO.get(0).getFileId());

		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}
	
	// 견적서 승인
	@GetMapping("ppsApprove")
	public String ppsApprove(ProposalVO proposalVO
							, Model model) {
		int result = ppsSerivce.ppsApprove(proposalVO);
		
		if( result > 0) {
			// 견적서 정보 조회
			ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO.getProposalNo());
				
			// 견적서 의뢰정보조회
			RequestVO reqVO = ppsSerivce.reqInfo(findVO.getRequestNo());
		
			AlarmVO alarm = new AlarmVO();
			alarm.setUsersNo(findVO.getWorker());
			alarm.setTitle("견적서 승인 완료");
			alarm.setContent("["+ reqVO.getTitle() + "] 에 등록한 견적서가 승인 되었습니다.");
			sac.message(alarm);
		
	        model.addAttribute("msg", "견적서 승인 완료");
	        model.addAttribute("icon", "success");
	        model.addAttribute("url", "/ppsInfo?proposalNo=" + proposalVO.getProposalNo());
	        return "gongtong/message";
	    }else {
	    	model.addAttribute("msg", "다시 확인해 주세요");
 	        model.addAttribute("icon", "warning");
 	        model.addAttribute("url", "/ppsInfo?proposalNo=" + proposalVO.getProposalNo());
 	        return "gongtong/message"; 
	    }
	}
}
