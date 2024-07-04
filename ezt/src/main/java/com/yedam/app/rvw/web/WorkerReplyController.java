package com.yedam.app.rvw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.rvw.service.WorkerReplyService;
import com.yedam.app.rvw.service.WorkerReplyVO;

//댓글 CRUD

@Controller
public class WorkerReplyController {
	@Autowired
	WorkerReplyService replyService;
	
	//댓글 조회
		@GetMapping("replyInfo")
		public String replyInfo(WorkerReplyVO replyVO, Model model) {
			WorkerReplyVO findVO = replyService.replyInfo(replyVO);			
			model.addAttribute("reply",findVO);
			return "rvw/reviewInfo";
		}
		
		
		//댓글 등록
		@GetMapping("replyInsert")
		public String replyInsert(Model model) {

			model.addAttribute("reply", new WorkerReplyVO());

			return "rvw/reviewInsert";
		}
		
		@PostMapping("replyInsert")
		public String replyInsert(WorkerReplyVO replyVO) {
			replyService.insertReply(replyVO);
			return "redirect:list";
		}
		
		//댓글 수정
		@PostMapping("replyUpdate")
		@ResponseBody
		public boolean replyUpdate(@RequestBody WorkerReplyVO replyVO) {
			return replyService.updateReply(replyVO);
		}
		
		//댓글 삭제
		@GetMapping("replyDelete")
		public String replyDelete(Integer workerReplyNo) {
			replyService.deleteReply(workerReplyNo);
			return "redirect:list";
		}
}
