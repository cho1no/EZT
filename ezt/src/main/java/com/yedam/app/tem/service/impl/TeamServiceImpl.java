package com.yedam.app.tem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.tem.mapper.TeamMapper;
import com.yedam.app.tem.service.MemberDenyVO;
import com.yedam.app.tem.service.MemberEnrollVO;
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

	//팀 수정
	@Override
	public boolean updateTeam(TeamVO teamVO) {
	
		return teamMapper.updateTeam(teamVO) == 1;
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

	//팀 상세 등록
	@Override
	public int insertCategory(TeamWorkCategoryVO twcVO) {
		int result = teamMapper.insertCategory(twcVO);
		return result == 1 ? twcVO.getTeamNo() : -1;
	}
	
	//팀 상세 수정
	@Override
	public boolean updateCategory(TeamWorkCategoryVO twcVO) {
		
		return teamMapper.updateCategory(twcVO) == 1;
	}
	
	//팀 상세 삭제
	@Override
	public int deleteCategory(TeamWorkCategoryVO twcVO) {
		
		return teamMapper.deleteCategory(twcVO);
	}
	
	//팀 
	@Override
	public List<MemberEnrollVO> volunteerList(MemberEnrollVO memberEnrollVO) {
		
		return teamMapper.volunteerList(memberEnrollVO);
	}

	//팀원 신청 반려(프로시저 이용)
	@Override
	public boolean updateMemberEnroll(MemberDenyVO memberDenyVO) {
		
		return teamMapper.updateMemberEnroll(memberDenyVO) == 1;
	}






}
