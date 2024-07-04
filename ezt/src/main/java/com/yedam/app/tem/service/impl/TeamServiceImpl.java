package com.yedam.app.tem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.tem.mapper.TeamMapper;
import com.yedam.app.tem.service.TeamService;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.tem.service.TeamWorkCategoryVO;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamMapper teamMapper;
	
	//팀신청 전체조회
	@Override
	public List<TeamVO> teamList() {

		return teamMapper.teamList();
	}

	//팀신청 단건조회
	@Override
	public TeamVO teamInfo(TeamVO teamVO) {

		return teamMapper.teamInfo(teamVO);
	}

	//팀신청 등록
	@Override
	public int insertTeam(TeamVO teamVO) {
		int result = teamMapper.insertTeam(teamVO);
		return result == 1 ? teamVO.getTeamNo() : -1;
	}

	//팀신청 수정
	@Override
	public boolean updateTeam(TeamVO teamVO) {

		return teamMapper.updateTeam(teamVO) == 1;
	}

	//팀신청 삭제
	@Override
	public int deleteTeam(int teamNO) {

		return teamMapper.deleteTeam(teamNO);
	}

	//전체 페이지 수
	@Override
	public int getTotal(Criteria cri) {

		return teamMapper.getTotalCount(cri);
	}

	//전체 팀원 신청 인원수 
	@Override
	public int totalHeadCount(TeamWorkCategoryVO twcVO) {
		
		return teamMapper.totalHeadCount(twcVO);
	}
	
	
}
