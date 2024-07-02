package com.yedam.app.wkr.web;

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
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;
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
   UserMapper userMapper;
   
   //정보조회
   @GetMapping("/info")
   public String workerInfo(@AuthenticationPrincipal LoginUserVO vo, Model model) {
//      log.info(workerService.selectCategoryInfo(vo.getUserNo()).toString());
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
      //Map<String, Object> result = 
      workerService.updateWorker(userVO);
      //userMapper.selectUserInfo(userVO.getUsersId());
      workerService.deleteWorkerCategory(userVO);
      workerService.deleteWorkerRegion(userVO);
      workerService.insertWorkerCode(userVO);
      log.info(userVO.toString());
//      model.addAttribute("result", "정보수정완료");
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
	   model.addAttribute("career", workerService.selectCareerList(vo.getUserVO()));
	   return "wkr/workerCareerList";
   } 
   
   //경력증명서 등록 페이지
   @GetMapping("/careerInsert")
   public String workerCareerInsertForm(UserVO userVO, Model model, @AuthenticationPrincipal LoginUserVO vo) {
	   model.addAttribute("userVO", vo.getUserVO());
	   CareerVO findVO = workerService.selectCareerList(userVO);
	   model.addAttribute("career", findVO);
	   
	   //model.addAttribute("car", new CareerVO());
	   return "wkr/workerCareerInsert";
   }
   
   //경력증명서 등록 기능
   @PostMapping("/careerInsert")
   public String workerCareerInsert(CareerVO careerVO, Model model) {
	   workerService.insertCareer(careerVO);
	   return "redirect:/worker/careerList";
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
