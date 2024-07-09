package com.yedam.app.rvw.web;

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
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.PageDTO;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.rvw.service.WorkerReplyVO;
import com.yedam.app.sgi.service.LoginUserVO;
//리뷰 CRUD 및 댓글 CRUD
@RequestMapping("review")
@Controller
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	@Autowired
	CommonCodeService commonCodeService;
	@Autowired
	RequestService requestService;

	
	//리뷰 전체조회
	@GetMapping("/list")
	public String reviewList(Criteria cri, Model model) {
		List<ReviewVO> list = reviewService.reviewList(cri);
		model.addAttribute("rvList", list);
		
		//페이징
		int total = reviewService.getTotal(cri);
		model.addAttribute("page", new PageDTO(cri, total));
		
		return "rvw/reviewList";
	}
	
	//리뷰 상세조회
	@GetMapping("/info")
	public String reviewInfo(ReviewVO reviewVO,WorkerReplyVO replyVO,  Model model) {
		//리뷰 조회
		ReviewVO findVO = reviewService.reviewInfo(reviewVO);
		model.addAttribute("review",findVO);
		
		//공통코드
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		
		//댓글 조회
		WorkerReplyVO reply = reviewService.replyInfo(replyVO);			
		model.addAttribute("reply",reply);
		
		return "rvw/reviewInfo";
	}
	
	
	//후기 등록
	@GetMapping("/insert")
	public String reviewInsert( RequestVO requestVO, Model model,@AuthenticationPrincipal LoginUserVO user) {
		//의뢰 단건조회
		RequestVO findVO = requestService.requestInfo(requestVO);
		model.addAttribute("request",findVO);
		
		//후기 등록
		model.addAttribute("review", new ReviewVO());
		
		//유저 정보보내기
		model.addAttribute("writer", user.getUserNo());
		return "rvw/reviewInsert";
	}
	
	@PostMapping("/insert")
	public String reviewInsert(ReviewVO reviewVO) {
		reviewService.insertReview(reviewVO);
		return "redirect:list";
	}
	
	//후기 수정
	@PostMapping("/update")
	@ResponseBody
	public boolean reviewUpdate(@RequestBody ReviewVO reviewVO) {
		return reviewService.updateReview(reviewVO);
	}
	
	//후기 삭제
	@GetMapping("/delete")
	public String reviewDelete(Integer reviewNo) {
		reviewService.deleteReview(reviewNo);
		return "redirect:list";
	}
	
	//댓글 등록
	@GetMapping("/replyInsert")
	public String replyInsert(Model model) {

		model.addAttribute("replyVO", new WorkerReplyVO());

		return "rvw/reviewInsert";
	}
	
	@PostMapping("/replyInsert")
	public String replyInsert(WorkerReplyVO replyVO) {
		reviewService.insertReply(replyVO);
		return "redirect:list";
	}
	
	//댓글 수정
	@PostMapping("/replyUpdate")
	@ResponseBody
	public boolean replyUpdate(@RequestBody WorkerReplyVO replyVO) {
		return reviewService.updateReply(replyVO);
	}
	
	//댓글 삭제
	@GetMapping("/replyDelete")
	public String replyDelete(Integer workerReplyNo) {
		reviewService.deleteReply(workerReplyNo);
		return "rvw/reviewInfo";
	}
	
}
