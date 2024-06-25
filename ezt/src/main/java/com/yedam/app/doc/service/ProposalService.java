package com.yedam.app.doc.service;

import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

public interface ProposalService {
	
	// 유저정보조회
	public UserVO userInfo(int userNo);
	
	// 견적서 의뢰정보조회
	public RequestVO reqInfo(RequestVO requestVO);
	
	// 견적서 단건조회
	public ProposalVO ppsInfo(ProposalVO proposalVO);
	
	// 견적서 등록
	public int ppsInsert(ProposalVO proposalVO);
	
	// 견적서 수정
	public int ppsUpdate(ProposalVO proposalVO);
	
	// 견적서 삭제
	public int ppsDelete(int proposalNo);

}

