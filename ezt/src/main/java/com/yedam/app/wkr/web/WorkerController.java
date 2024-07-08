package com.yedam.app.wkr.web;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.common.service.SimpleFileService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.LicenseVO;
import com.yedam.app.wkr.service.PortfolioVO;
import com.yedam.app.wkr.service.WorkerLcsCriteria;
import com.yedam.app.wkr.service.WorkerLcsPageDTO;
import com.yedam.app.wkr.service.WorkerReqCriteria;
import com.yedam.app.wkr.service.WorkerReqPageDTO;
import com.yedam.app.wkr.service.WorkerRvwCriteria;
import com.yedam.app.wkr.service.WorkerRvwPageDTO;
import com.yedam.app.wkr.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("worker")
public class WorkerController {
   @Autowired
   WorkerService workerService;
   
   @Autowired
   CommonCodeService commonCodeService;
   
   @Autowired
   SimpleFileService simpleFileService;
   
   @Autowired
   UserService userService; 
   
   @Autowired
   UserMapper userMapper;
   
   //정보조회
   @GetMapping("/info")
   public String workerInfo(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	  model.addAttribute("userVO", vo.getUserVO());
      model.addAttribute("categories", workerService.selectCategoryInfo(vo.getUserNo()));
      model.addAttribute("regions", workerService.selectRegionInfo(vo.getUserNo()));
      return "wkr/workerInfo";
   }
   
   //정보수정 -페이지
   @GetMapping("/update")
   public String workerUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
      model.addAttribute("cate", commonCodeService.selectCommonCodeAll("0C"));
      model.addAttribute("regi", commonCodeService.selectCommonCodeAll("0B"));
      model.addAttribute("userVO", vo.getUserVO());
      model.addAttribute("categories", workerService.selectCategoryInfo(vo.getUserNo()));
      model.addAttribute("regions", workerService.selectRegionInfo(vo.getUserNo()));
      return "wkr/workerUpdate";
   }
   
   //정보수정 기능
   @PostMapping("/update")
   public String workerUpdate(UserVO userVO, Model model, @AuthenticationPrincipal LoginUserVO vo){
      workerService.updateWorker(userVO);


      //시큐리티 정보 업데이트
      UserVO uvo = userMapper.selectUserInfo(userVO.getUsersId());
      vo.setUserVO(uvo);
      
      model.addAttribute("msg", "정보수정 완료!");
      model.addAttribute("url", "/worker/update");
      return "gongtong/message";
   }
   
   //비밀번호 변경 -페이지
   @GetMapping("/pwUpdate")
   public String workerPwchangeForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
      model.addAttribute("userId", vo.getUserVO());
      return "wkr/workerPwUpdate";
   }
   
   //비밀번호 변경 기능
   @PostMapping("/pwUpdate")
   @ResponseBody
   public boolean workerPwUpdate(@RequestBody UserVO userVO) {
      return workerService.updateWorkerPw(userVO);
   }
   
   //경력증명서 목록
   @GetMapping("/careerList")
   public String workerCareerList (@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   List<CareerVO> list = workerService.selectCareerList(vo.getUserVO());
	   model.addAttribute("careerList", list); 
	   return "wkr/workerCareerList";
   } 
   
   //경력증명서 등록 페이지
   @GetMapping("/careerInsert")
   public String workerCareerInsertForm(Model model, @AuthenticationPrincipal LoginUserVO vo) {
	   model.addAttribute("userVO", vo.getUserVO());
	   model.addAttribute("car", new CareerVO());
	   return "wkr/workerCareerInsert";
   }
   
   //경력증명서 등록 기능(처리)
   @PostMapping("/careerInsert")
   public String workerCareerInsert(MultipartFile[] uploadFile, CareerVO careerVO, Model model) {
	   int result = simpleFileService.uploadFiles(uploadFile);
	   careerVO.setFileId(result);
	   model.addAttribute("car", workerService.insertCareer(careerVO));
	   return "redirect:/worker/careerList";
   }
   
   //작업자 후기 목록조회
   @GetMapping("/reviewList")
   public String workerReviewList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerRvwCriteria cri) {
	   model.addAttribute("userVO", vo.getUserVO());
	   cri.setUsersNo(vo.getUserVO().getUsersNo());
	   List<ReviewVO> list = workerService.selectWorkerReviewList(cri);
	   model.addAttribute("reviewList", list);
	   //페이징
	   int total = workerService.workerReviewGetTotal(cri);
	   model.addAttribute("page", new WorkerRvwPageDTO(cri, total));
	   return "wkr/workerReviewList";
   }
   
   //작업자 팀후기 목록조회
   @GetMapping("/teamReviewList")
   public String workerTeamReviewList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerRvwCriteria cri) {
	   model.addAttribute("userVO", vo.getUserVO());
	   cri.setUsersNo(vo.getUserVO().getUsersNo());
	   List<ReviewVO> tList = workerService.selectWorkerTeamReviewList(cri);
	   model.addAttribute("teamReviewList", tList);
	   //페이징
	   int tTotal = workerService.workerTeamReviewGetTotal(cri);
	   model.addAttribute("tPage", new WorkerRvwPageDTO(cri, tTotal));
	   return "wkr/workerTeamReviewList";
   }
   
   //작업자 의뢰 목록조회
   @GetMapping("/requestList")
   public String workerRequestList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerReqCriteria cri) {
	   model.addAttribute("userVO", vo.getUserVO());
	   cri.setUsersNo(vo.getUserVO().getUsersNo());
	   List<ReviewVO> list = workerService.selectWorkerRequestList(cri);
	   model.addAttribute("requestList", list);
	   //페이징
	   int total = workerService.workerRequestGetTotal(cri);
	   model.addAttribute("page", new WorkerReqPageDTO(cri, total));
	   return "wkr/workerRequestList";
   }
   
   //작업자 팀의뢰 목록조회
//   @GetMapping("/teamRequestList")
//   public String workerTeamRequestList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerReqCriteria cri) {
//	   model.addAttribute("userVO", vo.getUserVO());
//	   cri.setUsersNo(vo.getUserVO().getUsersNo());
//	   List<ReviewVO> list = workerService.selectWorkerTeamRequestList(cri);
//	   model.addAttribute("teamRequestList", list);
//	   //팀목록
//	   //페이징
//	   int total = workerService.workerTeamRequestGetTotal(cri);
//	   model.addAttribute("page", new WorkerReqPageDTO(cri, total));
//	   return "wkr/workerTeamRequestList";
//   }
   
   //작업자 견적서 목록조회
   @GetMapping("/proposalList")
   public String workerProposalList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerRvwCriteria cri) {
	   model.addAttribute("userVO", vo.getUserVO());
	   cri.setUsersNo(vo.getUserVO().getUsersNo());
	   List<ProposalVO> pList = workerService.selectWorkerProposalList(cri);
	   model.addAttribute("proposalList", pList);
	 //페이징
	   int total = workerService.workerProposalGetTotal(cri);
	   model.addAttribute("page", new WorkerRvwPageDTO(cri, total));
	   return "wkr/workerProposalList";
   }
   
//   //작업자 계약서 목록조회
//   @GetMapping("/contractList")
//   public String workerContractList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerRvwCriteria cri) {
//	   model.addAttribute("userVO", vo.getUserVO());
//	   cri.setUsersNo(vo.getUserVO().getUsersNo());
//	   List<ContractVO> cList = workerService.selectWorkerContractList(cri);
//	   model.addAttribute("contractList", cList);
//	   //페이징
//	   int total = workerService.workerContractGetTotal(cri);
//	   model.addAttribute("page", new WorkerRvwPageDTO(cri, total));
//	   return "wkr/workerContractList";
//   }
   
   
   @GetMapping("/contractList")
   public String workerContractList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerRvwCriteria cri, RedirectAttributes redirectAttributes) {
       // 로그인 사용자 정보 가져오기
       model.addAttribute("userVO", vo.getUserVO());

       // 현재 사용자의 계약 목록 조회를 위한 조건 설정
       cri.setUsersNo(vo.getUserVO().getUsersNo());

       // 계약 목록 조회
       List<ContractVO> cList = workerService.selectWorkerContractList(cri);
       model.addAttribute("contractList", cList);

       // 페이징 처리를 위한 총 게시물 수 조회
       int total = workerService.workerContractGetTotal(cri);

       // 페이징 정보 설정
       WorkerRvwPageDTO pageDTO = new WorkerRvwPageDTO(cri, total);
       model.addAttribute("page", pageDTO);

       // 현재 페이지가 총 페이지 수를 초과하는 경우 마지막 페이지로 리디렉션
       if (cri.getPage() > pageDTO.getEndPage()) {
           redirectAttributes.addAttribute("page", pageDTO.getEndPage());
           redirectAttributes.addFlashAttribute("error", "존재하지 않는 페이지입니다. 마지막 페이지로 이동합니다.");
           return "redirect:/worker/contractList";
       }

       // 계약 목록 페이지로 이동
       return "wkr/workerContractList";
   }
   
   //작업자 포트폴리오 목록조회
   @GetMapping("/portfolioList")
   public String workerpfList(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   List<PortfolioVO> list = workerService.selectWorkerPortfolioList(vo.getUserVO());
	   model.addAttribute("portfolioList", list);
	   
	   //페이징
//	   int total = workerService.workerLicenseGetTotal(cri);
//	   model.addAttribute("page", new WorkerLcsPageDTO(cri, total));
	   return "wkr/workerPortfolioList";
   }   
   //작업자 포트폴리오 등록 페이지
   @GetMapping("portfolioInsert")
   public String workerpfInsertForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   model.addAttribute("categories", commonCodeService.selectCommonCodeAll("0C"));
	   model.addAttribute("ptf", new PortfolioVO());
	   return "wkr/workerPortfolioInsert";
   }
   //작업자 포트폴리오 등록 기능(처리)
   @PostMapping("portfolioInsert")
   public String workerpfInsert(MultipartFile[] uploadFile, PortfolioVO pvo, Model model) {
	   int result = simpleFileService.uploadFiles(uploadFile);
	   pvo.setFileId(result);
	   model.addAttribute("ptf", workerService.insertWorkerPortfolio(pvo));
	   return "redirect:/worker/workerPortfolioList";
   }
   
   //작업자 자격증 목록조회
   @GetMapping("licenseList")
   public String workerLicenseList(@AuthenticationPrincipal LoginUserVO vo, Model model, WorkerLcsCriteria cri) {
	   model.addAttribute("userVO", vo.getUserVO());
	   cri.setUsersNo(vo.getUserVO().getUsersNo());
	   List<LicenseVO> list = workerService.selectWorkerLicenseList(cri);
	   model.addAttribute("licenseList", list);
	   
	   //페이징
	   int total = workerService.workerLicenseGetTotal(cri);
	   model.addAttribute("page", new WorkerLcsPageDTO(cri, total));
	   return "wkr/workerLicenseList";
   }
   
   //작업자 자격증 등록 페이지
   @GetMapping("licenseInsert")
   public String workerLicenseForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   model.addAttribute("lcs", new LicenseVO());
	   return "wkr/workerLicenseInsert";
   }
   
   //작업자 자격증 등록 기능(처리)
   @PostMapping("licenseInsert")
   public String workerLicenseInsert(MultipartFile[] uploadFile, LicenseVO lvo, Model model) {
	   int result = simpleFileService.uploadFiles(uploadFile);
	   lvo.setFileId(result);
	   model.addAttribute("lcs", workerService.insertWorkerLicense(lvo));
	   return "redirect:/workerLicenseList";
   }
   
   
   //작업자 탈퇴 (상태 수정) 페이지
   @GetMapping("/quit")
   public String workerStateUpdateForm(@AuthenticationPrincipal LoginUserVO vo, Model model) {
      model.addAttribute("userId", vo.getUserVO());
      return "wkr/workerStateUpdate";
   }
   
   
   //작업자 탈퇴 기능 (상태 수정)
   @PostMapping("/quit")
   @ResponseBody
   public boolean workerStateUpdate(@RequestBody UserVO userVO) {
      return workerService.workerStateUpdate(userVO);
   }
   
   
   
}
