package com.yedam.app.doc.mapper;

import com.yedam.app.doc.service.ProposalVO;

public interface ProposalMapper {
	
	// 견적서 의뢰정보조회
	public ProposalVO selectReqInfo(ProposalVO proposalVO);
	
	// 견적서 단건조회
	public ProposalVO selectPpsInfo(ProposalVO proposalVO);
	
	// 견적서 등록
	public int insertPpsInfo(ProposalVO proposalVO);
}
