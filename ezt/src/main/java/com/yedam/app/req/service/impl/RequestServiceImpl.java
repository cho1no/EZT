package com.yedam.app.req.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.fie.mapper.FileMapper;
import com.yedam.app.req.mapper.RequestMapper;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rpt.service.CttReportVO;
import com.yedam.app.tem.service.MemberVO;
import com.yedam.app.usr.service.UserVO;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestMapper requestMapper;
	@Autowired
	FileMapper fileMapper;
	
	//유저 정보조회
	@Override
	public UserVO userInfo(int usersNo) {
		
		return requestMapper.selectUser(usersNo);
	}
	//의뢰 전체조회
	@Override
	public List<RequestVO> requestList(Criteria cri) {
		
		return requestMapper.selectRequestAll(cri);
	}
	
	//의뢰 단건조회
	@Override
	public RequestVO requestInfo(RequestVO requestVO) {
		
		return requestMapper.selectRequestInfo(requestVO);
	}
	
	//의뢰 등록
	@Override
	public int insertRequest(RequestVO requestVO) {
		int result = requestMapper.insertRequest(requestVO);
		
		//파일 등록
		if(requestVO.getFileVO() != null) {
			requestMapper.insertFileAttrReqInfo(requestVO);
			requestVO.getFileVO().forEach(e->{
				e.setFileId(requestVO.getFileId());
				fileMapper.insertFileInfo(e);
			});
		}
		return result == 1 ? requestVO.getRequestNo() : -1;
		
	}
	//의뢰 수정
	@Override
	public boolean updateRequest(RequestVO requestVO) {
		
		return requestMapper.updateRequestInfo(requestVO) == 1;
	}
	//의뢰 삭제
	@Override
	public int deleteRequest(int requestNo) {
		//견적서 여부 판단
		//견적서가 있는 경우
		//update 이용해서 개인정보 비식별화. 상태값을 -> 삭제로 
		//견적서가 없는경우
		return requestMapper.deleteRequest(requestNo);
		
	}

	//견적서 조회
	@Override
	public List<ProposalVO> proposalList(ProposalVO proposalVO) {
		List<ProposalVO> list = requestMapper.selectProposalAll(proposalVO);
	
		return list;
	}
	//계약서 조회
	@Override
	public ContractVO contractInfo(ContractVO contractVO) {
		
		return requestMapper.selectContract(contractVO);
	}
	// 공사보고 전체조회
	@Override
	public List<CttReportVO> cttReportList(int requestNo) {
		return requestMapper.selectCttReport(requestNo);
	}
	@Override
	public List<MemberVO> memberList(int requestNo) {
		return requestMapper.selectMembers(requestNo);
	}
	//전체 페이지 수
	@Override
	public int getTotal(Criteria cri) {
		return requestMapper.getTotalCount(cri);
	}
	
	//이미지 삭제
	@Override
	public int deleteFile(int fileId) {
		
		return requestMapper.deleteFile(fileId);
	}
	//견적서 있는경우 삭제('삭제중'으로 update)
	@Override
	public boolean updateRequestState(RequestVO requestVO) {
		return requestMapper.updateRequestState(requestVO) == 1;
	}


	



	
}
