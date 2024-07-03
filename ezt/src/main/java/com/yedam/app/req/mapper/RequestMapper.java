package com.yedam.app.req.mapper;

import java.util.List;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.RequestVO;
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
	//견적서 조회
	public List<ProposalVO> selectProposalAll(ProposalVO proposalVO);
	
	//견적서 상세 조회
	//public List<ProposalDetailVO> selectPpDetailAll(int proposalNo);
	
	//계약서 조회
	public ContractVO selectContract(ContractVO contractVO);
	
	
	
	//전체 게시물 갯수
	public int getTotalCount(Criteria cri);
}
