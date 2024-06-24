package com.yedam.app.doc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.doc.service.ProposalDetailVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;

public interface ProposalMapper {
	
	// 견적서 의뢰정보조회
	public RequestVO selectReqInfo(RequestVO requestVO);
	
	// 견적서 단건조회
	public ProposalVO selectPpsInfo(ProposalVO proposalVO);
	
	// 견적서 상세조회
	public List<ProposalDetailVO> selectPpszDetailList(@Param("proposalNo")int proposalNo);
	
	// 견적서 등록
	public int insertPpsInfo(ProposalVO proposalVO);

	// 견적서 상세 등록
	public int insertPpsDetailInfo(ProposalDetailVO proposalDetailVO);
}

