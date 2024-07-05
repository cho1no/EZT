package com.yedam.app.tem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.tem.mapper.TeamMapper;
import com.yedam.app.tem.service.TeamService;
import com.yedam.app.tem.service.TeamVO;

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

	//팀신청 수정 & 팀상세 삭제 및 등록
	@Override
	public int updateTeam(TeamVO teamVO) {
		//팀신청 수정
		
		int result = teamMapper.updateTeam(teamVO);
		System.out.println("------>"+teamVO);
		//팀상세 삭제 후 등록
		if(teamVO.getWorkCategoryVO() != null) {
			teamMapper.deleteCategory(teamVO.getTeamNo());
			teamVO.getWorkCategoryVO().forEach(e -> {
				e.setTeamNo(teamVO.getTeamNo());
				teamMapper.insertCategory(e);
			});
		}
		
		return result == 1 ? teamVO.getTeamNo() : -1;
	}
	
	//팀신청 삭제
	@Override
	public int deleteTeam(int teamNo) {

		return teamMapper.deleteTeam(teamNo);
	}

	//전체 페이지 수
	@Override
	public int getTotal(Criteria cri) {

		return teamMapper.getTotalCount(cri);
	}





}
