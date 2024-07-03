package com.yedam.app.req.service;

import java.util.List;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.usr.service.UserVO;

public interface RequestService {
	//유저 정보조회
	public UserVO userInfo(int usersNo);
	//전체조회
	public List<RequestVO> requestList(Criteria cri);
	//단건조회
	public RequestVO requestInfo(RequestVO requestVO);
	//등록
	public int insertRequest(RequestVO requestVO);
	//수정
	public boolean updateRequest(RequestVO requestVO);
	//삭제
	public int deleteRequest(int requestNo);
	//견적서 단건조회
	public List<ProposalVO> proposalList(ProposalVO proposalVO);
	//계약서 조회
	public ContractVO contractInfo(ContractVO contractVO);
	//전체 데이터 갯수
	public int getTotal(Criteria cri);
}
