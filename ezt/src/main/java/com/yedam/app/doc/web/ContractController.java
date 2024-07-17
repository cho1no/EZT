package com.yedam.app.doc.web;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.alm.service.AlarmVO;
import com.yedam.app.alm.web.StompAlarmController;
import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.PartnershipContractVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.fie.service.FileService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.usr.service.UserVO;

@Controller
public class ContractController {

	@Autowired
	ContractService conService;

	@Autowired
	ProposalService ppsSerivce;

	@Autowired
	FileService fileService;

	@Autowired
	StompAlarmController sac;

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

		UserVO requester = ppsSerivce.userInfo(ppsVO.getRequester());
		model.addAttribute("reqName", requester.getUsersName());

		// 은행 코드 조회
		List<CommonCodeVO> codeVO = conService.bankcodeSelect();
		model.addAttribute("code", codeVO);
		// 통일 계약서 조회
		UnityContractVO unityVO = conService.unityConSelect();
		model.addAttribute("unity", unityVO);

		return "doc/contractInsert";
	}

	@PostMapping(value = "/conInsert", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int uploadAjaxPost(MultipartFile[] uploadFile, ContractVO contractVO) {
		if (uploadFile != null && uploadFile.length > 0) {
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
				contractVO.setFileList(list);
			}
		}
		int no = conService.conInsert(contractVO);

		return no;
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
		model.addAttribute("user", user.getUserNo());

		ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());

		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		// 통일 계약서 조회
		UnityContractVO unityVO = conService.IncludeUnityCon(findVO.getContractNo());
		model.addAttribute("unity", unityVO);

		// 분야 코드 조회
		if (conService.ptnConSelect(findVO.getContractNo()) != null) {
			PartnershipContractVO workCode = conService.ptnConSelect(findVO.getContractNo());
			model.addAttribute("leaderCode", workCode.getLeaderCategoryCode());
			model.addAttribute("memberCode", workCode.getMemberCategoryCode());
		}
		
		// 결제 여부
		Integer no = conService.payCount(contractVO.getContractNo());
		model.addAttribute("pcount", no);

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

		// 은행 코드 조회
		List<CommonCodeVO> codeVO = conService.bankcodeSelect();
		model.addAttribute("code", codeVO);
		// 통일 계약서 조회
		UnityContractVO unityVO = conService.IncludeUnityCon(findVO.getContractNo());
		model.addAttribute("unity", unityVO);

		return "doc/contractUpdate";

	}

	@PostMapping(value = "/conUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadAjaxPostUpdate(MultipartFile[] uploadFile, ContractVO contractVO) {
		// 파일 삭제
		List<FileVO> fileList = conService.fileSelect(contractVO);
		if (!fileList.isEmpty()) {
			try {
				fileService.deleteFile(fileList);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// 수정
		if (uploadFile != null && uploadFile.length > 0) {

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
				contractVO.setFileList(list);
			}
		}
		conService.conUpdate(contractVO);

		return new ResponseEntity<>("update", HttpStatus.OK);
	}

	// 계약서 수정
	@GetMapping("conWrite")
	public String conWrite(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {

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

		// 은행 코드 조회
		List<CommonCodeVO> codeVO = conService.bankcodeSelect();
		model.addAttribute("code", codeVO);
		// 통일 계약서 조회
		UnityContractVO unityVO = conService.IncludeUnityCon(findVO.getContractNo());
		model.addAttribute("unity", unityVO);

		// 분야 코드 조회
		if (conService.ptnConSelect(findVO.getContractNo()) != null) {
			PartnershipContractVO workCode = conService.ptnConSelect(findVO.getContractNo());
			model.addAttribute("leaderCode", workCode.getLeaderCategoryCode());
			model.addAttribute("memberCode", workCode.getMemberCategoryCode());
		}

		return "doc/contractWrite";

	}

	// 계약서 전송
	@GetMapping("sendCon")
	public String conSend(ContractVO contractVO) {
		int no = conService.conSend(contractVO);
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);

		AlarmVO alarm = new AlarmVO();
		alarm.setUsersNo(findVO.getRequesterInfo());
		alarm.setTitle("계약서 전송 확인");
		alarm.setContent("확인이 필요한 계약서가 있습니다.");
		sac.message(alarm);

		return "redirect:conInfo?contractNo=" + no;
	}

	// 계약서 전송
	@GetMapping("sendConPtn")
	public String conSendPtn(ContractVO contractVO) {
		int no = conService.conSend(contractVO);
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);

		AlarmVO alarm = new AlarmVO();
		alarm.setUsersNo(findVO.getWorkerInfo());
		alarm.setTitle("계약서 전송 확인");
		alarm.setContent("확인이 필요한 계약서가 있습니다.");
		sac.message(alarm);

		return "redirect:conInfo?contractNo=" + no;
	}

	// 계약서 승인
	@GetMapping("approveCon")
	public String approveCon(ContractVO contractVO) {
		int no = conService.conSend(contractVO);
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);

		AlarmVO alarm = new AlarmVO();
		alarm.setUsersNo(findVO.getWorkerInfo());
		alarm.setTitle("계약서 승인 확인");
		alarm.setContent("승인된 계약서가 있습니다. 확인해주세요.");
		sac.message(alarm);

		return "redirect:conInfo?contractNo=" + no;
	}

	// 계약서 승인
	@GetMapping("approveConPtn")
	public String approveConPtn(ContractVO contractVO) {
		int no = conService.conSend(contractVO);
		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);

		AlarmVO alarm = new AlarmVO();
		alarm.setUsersNo(findVO.getRequesterInfo());
		alarm.setTitle("계약서 승인 확인");
		alarm.setContent("승인된 계약서가 있습니다. 확인해주세요.");
		sac.message(alarm);

		return "redirect:conInfo?contractNo=" + no;
	}

	// 동업 계약서 등록
	@GetMapping("ptnconInsert")
	public String ptnconInsert(ContractVO contractVO, int teamNo, int userNo, Model model,
			@AuthenticationPrincipal LoginUserVO user) {

		model.addAttribute("contractVO", new ContractVO());

		// 계약서 정보 조회
		ContractVO findVO = conService.conInfo(contractVO);
		model.addAttribute("con", findVO);

		// 유저 정보
		model.addAttribute("userName", user.getUserVO().getUsersName());
		model.addAttribute("userPhone", user.getUserVO().getUsersPhone());
		model.addAttribute("userRnn", user.getUserVO().getUsersRnn());
		model.addAttribute("userNo", user.getUserVO().getUsersNo());

		UserVO requester = ppsSerivce.userInfo(userNo);
		model.addAttribute("memberName", requester.getUsersName());
		model.addAttribute("memberNo", requester.getUsersNo());

		// 견적서 정보 조회
		ProposalVO ppsVO = ppsSerivce.ppsInfo(findVO.getProposalNo());
		model.addAttribute("ppsInfo", ppsVO);

		// 의뢰 정보 조회
		RequestVO reqVO = ppsSerivce.reqInfo(ppsVO.getRequestNo());
		model.addAttribute("reqInfo", reqVO);

		// 분야 코드 조회
		CommonCodeVO workCode = conService.leaderCodeSelect(reqVO.getRequestNo());
		model.addAttribute("leaderCode", workCode);
		CommonCodeVO m_workCode = conService.workCodeSelect(teamNo, userNo);
		model.addAttribute("memberCode", m_workCode);

		// 은행 코드 조회
		List<CommonCodeVO> codeVO = conService.bankcodeSelect();
		model.addAttribute("code", codeVO);
		// 통일 계약서 조회
		UnityContractVO unityVO = conService.unityConSelect();
		model.addAttribute("unity", unityVO);

		// 팀 코드
		model.addAttribute("teamNo", teamNo);

		return "doc/ptncontractInsert";
	}

	@PostMapping(value = "/ptnconInsert", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int ptnconInsert(MultipartFile[] uploadFile, ContractVO contractVO, TeamVO teamVO,
			PartnershipContractVO partnershipContractVO) {
		if (uploadFile != null && uploadFile.length > 0) {
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
				contractVO.setFileList(list);
			}
		}
		int no = conService.conInsert(contractVO);
		partnershipContractVO.setContractNo(no);
		partnershipContractVO.setTeamNo(teamVO.getTeamNo());
		partnershipContractVO.setLeaderNo(contractVO.getRequesterInfo());
		partnershipContractVO.setMemberNo(contractVO.getWorkerInfo());
		conService.ptnConInsert(partnershipContractVO);

		return no;
	}

	// 동업 계약서 수정(멤버)
	@GetMapping("ptnconWrite")
	public String ptnconWrite(ContractVO contractVO, Model model, @AuthenticationPrincipal LoginUserVO user) {

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

		// 은행 코드 조회
		List<CommonCodeVO> codeVO = conService.bankcodeSelect();
		model.addAttribute("code", codeVO);
		// 통일 계약서 조회
		UnityContractVO unityVO = conService.IncludeUnityCon(findVO.getContractNo());
		model.addAttribute("unity", unityVO);

		// 분야 코드 조회
		if (conService.ptnConSelect(findVO.getContractNo()) != null) {
			PartnershipContractVO workCode = conService.ptnConSelect(findVO.getContractNo());
			model.addAttribute("leaderCode", workCode.getLeaderCategoryCode());
			model.addAttribute("memberCode", workCode.getMemberCategoryCode());
		}

		return "doc/ptncontractWrite";
	}

}
