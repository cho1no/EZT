package com.yedam.app.tem.mapper;

import java.util.List;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.tem.service.TeamWorkCategoryVO;

public interface TeamMapper {

	//팀신청 전체조회
	public List<TeamVO> teamList();
	
	//팀신청 단건조회
	public TeamVO teamInfo(TeamVO teamVO);
	
	//팀신청 등록
	public int insertTeam(TeamVO teamVO);
	
	//팀신청 수정
	public int updateTeam(TeamVO teamVO);
	
	//팀신청 삭제
	public int deleteTeam(int teamNo); 
	
	//전체 게시물 갯수
	public int getTotalCount(Criteria cri);
	

}
