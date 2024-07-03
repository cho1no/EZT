package com.yedam.app.tem.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.tem.service.TeamService;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.tem.service.TeamWorkCategoryVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TeamController {

	@Autowired
	TeamService teamService;
	
	// 전체조회
		@GetMapping("teamRequestList")
		public String requestList(Model model, TeamWorkCategoryVO twcVO) {
			List<TeamVO> teamList = teamService.teamList();
			model.addAttribute("teamList",teamList);
			
			//총인원수
			//int totalhead = teamService.totalHeadCount(twcVO);
			//model.addAttribute("totalhead", totalhead);
			return "tem/teamRequestList";
		}
}
