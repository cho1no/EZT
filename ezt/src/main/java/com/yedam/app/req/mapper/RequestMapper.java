package com.yedam.app.req.mapper;

import java.util.List;

import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;

public interface RequestMapper {
	//의뢰 전체조회
	public List<RequestVO> selectRequestAll();
	//의뢰 단건조회
	public RequestVO selectRequestInfo(RequestVO requestVO);
	//의뢰 등록
	
	//의뢰 수정
	public int updateRequestInfo(RequestVO requestVO);
	//의뢰 삭제
	
	
	//견적서 단건조회
	public List<ProposalVO> selectProposalAll(ProposalVO proposalVO);
	//견적서 상세 단건조회
	
	//계약서 단건조회
}
