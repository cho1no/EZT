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

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.common.service.SimpleFileService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.PortfolioVO;
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
   public String workerReviewList(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   List<ReviewVO> list = workerService.selectWorkerReviewList(vo.getUserVO());
	   model.addAttribute("reviewList", list);
	   List<ReviewVO> tList = workerService.selectWorkerTeamReviewList(vo.getUserVO());
	   model.addAttribute("teamReviewList", tList);
	   return "wkr/workerReviewList";
   }
   
   //작업자 의뢰 목록조회
   @GetMapping("/requestList")
   public String workerRequestList(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   List<ReviewVO> list = workerService.selectWorkerRequestList(vo.getUserVO());
	   model.addAttribute("requestList", list);
	   //팀목록
//	   List<ReviewVO> tList = workerService.selectWorkerTeamReviewList(vo.getUserVO());
//	   model.addAttribute("teamRequestList", tList);
	   return "wkr/workerRequestList";
   }
   
   //작업자 견적서/계약서 목록조회
   @GetMapping("/documentList")
   public String workerPpsCttList(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   List<ProposalVO> pList = workerService.selectWorkerProposalList(vo.getUserVO());
	   model.addAttribute("proposalList", pList);
	   List<ContractVO> cList = workerService.selectWorkerContractList(vo.getUserVO());
	   model.addAttribute("contractList", cList);
	   return "wkr/workerDocumentList";
   }
   
   //작업자 포트폴리오 목록조회
   @GetMapping("/portfolioList")
   public String workerpfList(@AuthenticationPrincipal LoginUserVO vo, Model model) {
	   model.addAttribute("userVO", vo.getUserVO());
	   List<PortfolioVO> list = workerService.selectWorkerPortfolioList(vo.getUserVO());
	   model.addAttribute("portfolioList", list);
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
