package com.yedam.app.doc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.doc.mapper.ProposalMapper;
import com.yedam.app.doc.service.ProposalDetailVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

@Service
public class ProposalServiceImpl implements ProposalService {

	@Autowired
	ProposalMapper ppsMapper;

	@Override
	public UserVO userInfo(int userNo) {
		return ppsMapper.selectUserInfo(userNo);
	}

	// 견적서 의뢰정보조회
	@Override
	public RequestVO reqInfo(RequestVO requestVO) {
		return ppsMapper.selectReqInfo(requestVO);
	}

	// 견적서 단건조회 & 상세 동시 처리
	@Override
	public ProposalVO ppsInfo(ProposalVO proposalVO) {
		List<ProposalDetailVO> list = ppsMapper.selectPpsDetailList(proposalVO.getProposalNo());
		ProposalVO pps = ppsMapper.selectPpsInfo(proposalVO);
		pps.setList(list);
		return pps;
	}

	// 견적서 등록 & 상세 동시 처리
	@Override
	public int ppsInsert(ProposalVO proposalVO) {
		int result = ppsMapper.insertPpsInfo(proposalVO);

		if (proposalVO.getList() != null) {
			proposalVO.getList().forEach(e -> {
				e.setProposalNo(proposalVO.getProposalNo());
				ppsMapper.insertPpsDetailInfo(e);
			});
		}
		return result == 1 ? proposalVO.getProposalNo() : -1;
	}

	@Override
	public int ppsUpdate(ProposalVO proposalVO) {
		ppsMapper.deletePpsDetailInfo(proposalVO.getProposalNo());
		int result = ppsMapper.updatePpsInfo(proposalVO);
		if (proposalVO.getList() != null) {
			proposalVO.getList().forEach(e -> {
				e.setProposalNo(proposalVO.getProposalNo());
				ppsMapper.insertPpsDetailInfo(e);
			});

		}

		return result == 1 ? proposalVO.getProposalNo() : -1;
	}

	@Override
	public int ppsDelete(int proposalNo) {
		boolean result = false;
		if (ppsMapper.deletePpsDetailInfo(proposalNo) == 1) {
			result = true;
		}

		if (result == true) {
			int success = ppsMapper.deletePpsInfo(proposalNo);
			return success == 1 ? 1 : -1;
		} else {
			return -1;
		}
	}

}
