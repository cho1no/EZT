package com.yedam.app.tem.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.tem.service.TeamService;
import com.yedam.app.tem.service.TeamVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TeamController {

	@Autowired
	TeamService teamService;
	
	// 전체조회
		@GetMapping("teamRequestList")
		public String requestList(Model model) {
			List<TeamVO> teamList = teamService.teamList();
			model.addAttribute("teamList",teamList);
			return "tem/teamRequestList";
		}
}
