package com.yedam.app.tem.service;

import java.util.List;

import com.yedam.app.req.service.Criteria;

public interface TeamService {
	
	//팀신청 전체조회
	public List<TeamVO> teamList(Criteria cri);
	
	//팀신청 단건조회
	public TeamVO teamInfo(TeamVO teamVO);
	
	//팀신청 등록
	public int insertTeam(TeamVO teamVO);
	
	//팀신청 수정(제목, 내용)
	public boolean updateTeam(TeamVO teamVO);
	
	//팀신청 삭제
	public int deleteTeam(int teamNO);
	
	//전체 데이터 갯수
	public int getTotal(Criteria cri);

	
	//팀 신청 상세 등록
	public int insertCategory(TeamWorkCategoryVO twcVO);
	//팀 신청 상세 수정
	public boolean updateCategory(TeamWorkCategoryVO twcVO);
	//팀 신청 상세 삭제
	public int deleteCategory(TeamWorkCategoryVO twcVO);
	
	//카테고리 별 신청자 조회
	public List<MemberEnrollVO> volunteerList(MemberEnrollVO memberEnrollVO);
	
	
	//신청자 반려하기(프로시저 이용)
	public boolean updateMemberEnroll(MemberDenyVO memberDenyVO);
	
	//팀원 신청하기
	public int insertMember(MemberEnrollVO memberEnrollVO);
}
