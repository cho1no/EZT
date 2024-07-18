package com.yedam.app.req.mapper;

import java.util.List;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rpt.service.CttReportVO;
import com.yedam.app.tem.service.MemberVO;
import com.yedam.app.usr.service.UserVO;

public interface RequestMapper {
	//유저정보 조회
	public UserVO selectUser(int usersNo);
	//의뢰 전체조회
	public List<RequestVO> selectRequestAll(Criteria cri);
	//의뢰 단건조회
	public RequestVO selectRequestInfo(RequestVO requestVO);
	//의뢰 등록
	public int insertRequest(RequestVO requestVO);
	//의뢰 수정
	public int updateRequestInfo(RequestVO requestVO);
	//의뢰 삭제
	public int deleteRequest(int requestNo);
	
	//견적서 있는경우 삭제('삭제중' 상태로 update)
	public int updateRequestState(RequestVO requestVO);
	
	//견적서 조회
	public List<ProposalVO> selectProposalAll(ProposalVO proposalVO);
	
	//계약서 조회
	public ContractVO selectContract(ContractVO contractVO);
	
	// 공사 보고 조회
	public List<CttReportVO> selectCttReport(int requestNo);
	// 팀원 조회
	public List<MemberVO> selectMembers(int requestNo);
	//전체 게시물 갯수
	public int getTotalCount(Criteria cri);
	//파일 등록
	public int insertFileAttrReqInfo(RequestVO requestVO);
	//파일 삭제
	public int deleteFile(int fileId);
}
