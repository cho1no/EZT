package com.yedam.app.req.service;

import java.util.List;

public interface RequestService {

	//전체조회
	public List<RequestVO> requestList();
	//단건조회
	public RequestVO requestInfo(RequestVO requestVO);
	//등록
	
	//수정
	
	//삭제
}
