package com.yedam.app.doc.service;

import java.util.List;

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
	
	// 견적서 목록 조회(특정 의뢰와 관련해 본인이 작성한 견적서 목록)
	public List<ProposalVO> ppsListInfo(ProposalVO proposalVO);
	
	// 견적서 단건 전송
	public int ppsSend(int proposalNo);

}

