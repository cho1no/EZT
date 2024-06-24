package com.yedam.app.doc.service;

import com.yedam.app.req.service.RequestVO;

public interface ProposalService {
	
	// 견적서 의뢰정보조회
	public RequestVO reqInfo(RequestVO requestVO);
	
	// 견적서 단건조회
	public ProposalVO ppsInfo(ProposalVO proposalVO);
	
	// 견적서 등록
	public int ppsInsert(ProposalVO proposalVO);

}

