package com.yedam.app.doc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.doc.mapper.ProposalMapper;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;

@Service
public class ProposalServiceImpl implements ProposalService {

	@Autowired
	ProposalMapper ppsMapper;
	
	// 견적서 의뢰정보조회
	@Override
	public ProposalVO reqInfo(ProposalVO proposalVO) {
		return ppsMapper.selectReqInfo(proposalVO);
	}
	
	// 견적서 단건조회
	@Override
	public ProposalVO ppsInfo(ProposalVO proposalVO) {
		return ppsMapper.selectPpsInfo(proposalVO);
	}

	// 견적서 등록
	@Override
	public int ppsInsert(ProposalVO proposalVO) {
		int result = ppsMapper.insertPpsInfo(proposalVO);
		return result == 1 ? proposalVO.getProposalNo() : -1;
	}

}
