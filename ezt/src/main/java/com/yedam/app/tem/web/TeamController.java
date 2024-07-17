package com.yedam.app.tem.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.PageDTO;
import com.yedam.app.tem.service.MemberDenyVO;
import com.yedam.app.tem.service.MemberEnrollVO;
import com.yedam.app.tem.service.MemberVO;
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


    // 팀 신청 전체 조회
    @GetMapping("/requestList")
    public String teamRequestList(Criteria cri, Model model) {
        List<TeamVO> teamList = teamService.teamList(cri);
        model.addAttribute("teamList", teamList);

        //페이징
  		int total = teamService.getTotal(cri);
  		model.addAttribute("page", new PageDTO(cri, total));
  		
        return "tem/teamRequestList";
    }

    // 팀 신청 단건 조회
    @GetMapping("/requestInfo")
    public String teamRequestInfo(Model model, TeamVO teamVO, MemberEnrollVO memberEnrollVO) {
        // 팀 신청 단건 조회
        TeamVO findVO = teamService.teamInfo(teamVO);
        model.addAttribute("team", findVO);
        
        // 공통 코드
        model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
        
        return "tem/teamRequestInfo";
    }

    //카테고리별 지원자 전체조회
    @GetMapping("/volunteerList")
    @ResponseBody
    public List<MemberEnrollVO> volunteerList(Model model, MemberEnrollVO meVO) {
        
    	return teamService.volunteerList(meVO);
    }
    // 팀 신청 등록 페이지
    @GetMapping("/requestInsert")
    public String teamRequestInsert(Model model, TeamVO teamVO) {
    	//의뢰 찾기
    	TeamVO findVO = teamService.findRequest(teamVO);
    	model.addAttribute("request",findVO);
    	
    	
        model.addAttribute("team", new TeamVO());
        model.addAttribute("twc", new TeamWorkCategoryVO());
        
        model.addAttribute("contractNo",teamVO.getContractNo()) ;
        
        // 공통 코드
        model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));

        return "tem/teamRequestInsert";
    }

    // 팀 신청 등록 처리
    @PostMapping("/requestInsert")
    @ResponseBody
    public boolean teamRequestInsert(@RequestBody TeamVO teamVO) {
        
            // 팀 정보 삽입
           int number =  teamService.insertTeam(teamVO);
           System.out.println(teamVO);
           System.out.println(number);
            // 카테고리 정보 삽입
            List<TeamWorkCategoryVO> workCategoryVOList = teamVO.getWorkCategoryVO();
            if (workCategoryVOList != null) {
                for (TeamWorkCategoryVO twcVO : workCategoryVOList) {
                    twcVO.setTwcTeamNo(number);
                    System.out.println(twcVO);
                    teamService.insertCategory(twcVO);
                    
                }
            }

            return true;
        } 
    
    // 팀 신청 수정
    @PostMapping("/requestUpdate")
    @ResponseBody
    public boolean teamRequestUpdate(@RequestBody TeamVO teamVO) {
        return teamService.updateTeam(teamVO);
    }

    // 팀 신청 삭제
    @GetMapping("/requestDelete")
    public String teamDelete(Integer teamNo) {
        teamService.deleteTeam(teamNo);

        return "redirect:/team/requestList";
    }

    // 팀 신청 상세 등록
    @GetMapping("/teamDetailInsert")
    public String teamDetailInsert(Model model) {
        model.addAttribute("twcVO", new TeamWorkCategoryVO());

        return "team/teamRequestInfo";
    }

    @PostMapping("/teamDetailInsert")
    public String teamDetailInsert(TeamWorkCategoryVO twcVO) {
        teamService.insertCategory(twcVO);
        return "redirect:/team/requestList";
    }

    // 팀 신청 상세 수정
    @PostMapping("/teamDetailUpdate")
    @ResponseBody
    public boolean teamDetailUpdate(@RequestBody TeamWorkCategoryVO twcVO) {
        return teamService.updateCategory(twcVO);
    }

    // 팀 신청 상세 삭제
    @GetMapping("/teamDetailDelete")
    public String teamDetailDelete(@RequestParam("teamNo") int teamNo, @RequestParam("workCode") String workCode) {
        TeamWorkCategoryVO twcVO = new TeamWorkCategoryVO();
        twcVO.setTeamNo(teamNo);
        twcVO.setWorkCode(workCode);

        teamService.deleteCategory(twcVO);
        return "tem/teamRequestInfo";
    }
    
    //신청자 반려
    @GetMapping("/memberDeny")
    public String memberDeny(Model model) {
    	model.addAttribute("deny", new MemberDenyVO());
    	
    	return "team/teamRequestInfo";
    }
    
    @PostMapping("/memberDeny")
    @ResponseBody
    public boolean memberDeny(@RequestBody MemberDenyVO memberDenyVO) {
    	return teamService.updateMemberEnroll(memberDenyVO);
    }
    

 // 팀원 신청 등록 (GET)
    @GetMapping("/memberInsert")
    public String memberInsert(Model model) {
        model.addAttribute("memberEnrollVO", new MemberEnrollVO());
        return "team/teamRequestInfo";
    }

    // 팀원 신청 등록 (POST)
    @PostMapping("/memberInsert")
    public String memberInsert(MemberEnrollVO memberEnrollVO, @RequestParam("teamNo") int teamNo) {
        memberEnrollVO.setTeamNo(teamNo);
        teamService.insertMember(memberEnrollVO);
        return "redirect:requestInfo?teamNo="+teamNo;
    }
    
    
  //신청자 승인
    @GetMapping("/approveMember")
    public String approveMember(Model model) {
    	model.addAttribute("approve", new MemberVO());
    	
    	return "team/teamRequestInfo";
    }
    
    @PostMapping("/approveMember")
    @ResponseBody
    public boolean approveMember(@RequestBody MemberVO memberVO) {
    	System.out.println("=====" + memberVO);
        teamService.approveMember(memberVO);
        return memberVO.getResult()==1? true:false;
    }
}

