package com.yedam.app.req.service;

import java.util.List;

import com.yedam.app.doc.service.ProposalVO;

public interface RequestService {

	//전체조회
	public List<RequestVO> requestList();
	//단건조회
	public RequestVO requestInfo(RequestVO requestVO);
	//등록
	
	//수정
	public boolean updateRequest(RequestVO requestVO);
	//삭제
	
	
	//견적서 단건조회
	public List<ProposalVO> proposalList();
	
	//견적서 상세 단건조회
	
	//계약서 조회
}
