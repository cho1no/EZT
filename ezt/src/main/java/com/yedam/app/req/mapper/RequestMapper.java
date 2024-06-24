package com.yedam.app.req.mapper;

import java.util.List;

import com.yedam.app.req.service.RequestVO;

public interface RequestMapper {
	//전체조회
	public List<RequestVO> selectRequestAll();
	//단건조회
	public RequestVO selectRequestInfo(RequestVO requestVO);
	//등록
	
	//수정
	
	//삭제
}
