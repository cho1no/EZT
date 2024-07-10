package com.yedam.app.doc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.mapper.ProposalMapper;
import com.yedam.app.doc.service.ProposalDetailVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.fie.mapper.FileMapper;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

@Service
public class ProposalServiceImpl implements ProposalService {

	@Autowired
	ProposalMapper ppsMapper;

	@Autowired
	FileMapper fileMapper;

	@Override
	public UserVO userInfo(int userNo) {
		return ppsMapper.selectUserInfo(userNo);
	}

	// 견적서 의뢰정보조회
	@Override
	public RequestVO reqInfo(int requestNo) {
		return ppsMapper.selectReqInfo(requestNo);
	}

	// 견적서 단건조회 & 상세 동시 처리
	@Override
	public ProposalVO ppsInfo(int proposalNo) {
		ProposalVO pps = ppsMapper.selectPpsInfo(proposalNo);
		List<ProposalDetailVO> list = ppsMapper.selectPpsDetailList(proposalNo);
		List<FileVO> fileList = fileMapper.selectProFileList(proposalNo);
		pps.setList(list);
		pps.setFileList(fileList);
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

	// 견적서 수정
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

	// 견적서 삭제
	@Override
	public int ppsDelete(int proposalNo) {
		ppsMapper.deletePpsDetailInfo(proposalNo);
		int success = ppsMapper.deletePpsInfo(proposalNo);
		return success == 1 ? proposalNo: -1;

	}

	// 견적서 목록 조회(특정 의뢰와 관련해 본인이 작성한 견적서 목록)
	@Override
	public List<ProposalVO> ppsListInfo(ProposalVO proposalVO) {
		return ppsMapper.selectPpsListInfo(proposalVO);
	}

	// 견적서 파일 첨부 & 전송
	@Override
	public int ppsFileUpdate(ProposalVO proposalVO) {
		
		if (proposalVO.getFileList() != null) {
			fileMapper.insertFileAttrProInfo(proposalVO);
			proposalVO.getFileList().forEach(e -> {
				e.setFileId(proposalVO.getFileId());
				fileMapper.insertFileInfo(e);
			});
		}
		ppsMapper.sendPpsInfo(proposalVO);
		return proposalVO.getProposalNo();
	}

	// 파일&견적서 삭제
	@Override
	public int ppsFileDelete(int fileId) {
		boolean result = false;
		if (ppsMapper.deleteFileInfo(fileId) == 1) {
			result = true;
		}

		return result == true ? 1 : -1;
	}
	
	// 견적서 승인
	@Override
	public int ppsApprove(ProposalVO proposalVO) {
		int result = ppsMapper.approvePpsInfo(proposalVO);
		return result == 1 ? proposalVO.getProposalNo() : -1;
	}

}
