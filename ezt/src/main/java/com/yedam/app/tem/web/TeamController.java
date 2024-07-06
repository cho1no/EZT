package com.yedam.app.tem.web;
//팀신청 CRUD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
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
	@Autowired
	CommonCodeService commonCodeService;
	//팀 신청 전체조회
	@GetMapping("/requestList")
	public String teamRequestList(Model model) {
		List<TeamVO> teamList = teamService.teamList();
		model.addAttribute("teamList",teamList);
		log.info(teamList.toString());

		return "tem/teamRequestList";
	}
	//팀 신청 단건조회
	@GetMapping("/requestInfo")
	public String teamRequestInfo(Model model, TeamVO teamVO) {
		//팀 신청 단건조회
		TeamVO findVO = teamService.teamInfo(teamVO);
		model.addAttribute("team", findVO);
		
		//공통코드
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		return "tem/teamRequestInfo";
	}
	
	//팀 신청 수정
	@PostMapping("/requestUpdate")
	@ResponseBody
	public boolean teamRequestUpdate(@RequestBody TeamVO teamVO) {
		return teamService.updateTeam(teamVO);
	}

	//팀 신청 상세 등록
	@GetMapping("/teamDetailInsert")
	public String teamDetailInsert(Model model) {
		model.addAttribute("twcVO", new TeamWorkCategoryVO());
		
		return "team/teamRequestInfo";
	}
	
	@PostMapping("/teamDetailInsert")
	public String teamDetailInsert(TeamWorkCategoryVO twcVO) {
		teamService.insertCategory(twcVO);
		return "redirect:requestList";
	}
	//팀 신청 상세 수정
	@PostMapping("/teamDetailUpdate")
	@ResponseBody
	public boolean teamDetailUpdate(@RequestBody TeamWorkCategoryVO twcVO) {
		return teamService.updateCategory(twcVO);
	}
	//팀 신청 상세 삭제
	@GetMapping("/teamDetailDelete")
	public String teamDetailDelete(TeamWorkCategoryVO twcVO) {
		teamService.deleteCategory(twcVO);
		
		return "redirect:teamRequestInfo";
	}
}
