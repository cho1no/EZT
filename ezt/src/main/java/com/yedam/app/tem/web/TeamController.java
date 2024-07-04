package com.yedam.app.tem.web;
//팀신청 CRUD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.app.tem.service.TeamService;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.tem.service.TeamWorkCategoryVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("team")
public class TeamController {

	@Autowired
	TeamService teamService;
	
	//팀 신청 전체조회
	@GetMapping("/requestList")
	public String teamRequestList(Model model, TeamWorkCategoryVO twcVO) {
		List<TeamVO> teamList = teamService.teamList();
		model.addAttribute("teamList",teamList);
		log.info(teamList.toString());
		
//		int findVO = teamService.totalHeadCount(twcVO);
//		model.addAttribute(findVO)
		return "tem/teamRequestList";
	}
	//팀 신청 단건조회
	@GetMapping("/requestInfo")
	public String teamRequestInfo(Model model, TeamVO teamVO) {
		TeamVO findVO = teamService.teamInfo(teamVO);
		model.addAttribute("team", findVO);
		
		return "tem/teamRequestInfo";
	}

}
