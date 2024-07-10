package com.yedam.app.tem.mapper;

import java.util.List;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.tem.service.MemberDenyVO;
import com.yedam.app.tem.service.MemberEnrollVO;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.tem.service.TeamWorkCategoryVO;

public interface TeamMapper {

	//팀신청 전체조회
	public List<TeamVO> teamList();
	
	//팀신청 단건조회
	public TeamVO teamInfo(TeamVO teamVO);
	
	//팀신청 등록(제목, 내용)
	public int insertTeam(TeamVO teamVO);
	
	//팀신청 수정(제목, 내용)
	public int updateTeam(TeamVO teamVO);
	
	//팀신청 삭제(제목, 내용)
	public int deleteTeam(int teamNo); 
	
	//전체 게시물 갯수
	public int getTotalCount(Criteria cri);
	
	
	
	
	//팀신청 등록(카테고리, 카테고리 별 인원)
	public int insertCategory(TeamWorkCategoryVO twcVO);
	//팀신청 수정 (카테고리, 카테고리 별 인원)
	public int updateCategory(TeamWorkCategoryVO twcVO);
	//팀신청 삭제 (카테고리,카테고리 별 인원)
	public int deleteCategory(TeamWorkCategoryVO twcVO);
	
	
	//카테고리별 신청자 조회
	public List<MemberEnrollVO> volunteerList(MemberEnrollVO memberEnrollVO);
	
	
	//신청자 반려하기(프로시저 이용)
	public int updateMemberEnroll(MemberDenyVO memberDenyVO);
}
