package com.yedam.app.doc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.doc.mapper.ProposalMapper;
import com.yedam.app.doc.service.ProposalDetailVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;

@Service
public class ProposalServiceImpl implements ProposalService {

	@Autowired
	ProposalMapper ppsMapper;
	
	// 견적서 의뢰정보조회
	@Override
	public RequestVO reqInfo(RequestVO requestVO) {
		return ppsMapper.selectReqInfo(requestVO);
	}
	
	// 견적서 단건조회 & 상세 동시 처리
	@Override
	public ProposalVO ppsInfo(ProposalVO proposalVO) {
		List<ProposalDetailVO> list = ppsMapper.selectPpszDetailList(proposalVO.getProposalNo());
		ProposalVO pps = ppsMapper.selectPpsInfo(proposalVO);
		pps.setList(list);
		return pps;
	}

	// 견적서 등록 & 상세 동시 처리
	@Override
	public int ppsInsert(ProposalVO proposalVO) {
		int result = ppsMapper.insertPpsInfo(proposalVO);
		
		if(proposalVO.getList() != null) {
		proposalVO.getList().forEach(e -> {
			e.setProposalNo(proposalVO.getProposalNo());
			ppsMapper.insertPpsDetailInfo(e);
		});
		}
		return result == 1 ? proposalVO.getProposalNo() : -1;
	}
	
}
