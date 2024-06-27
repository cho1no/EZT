package com.yedam.app.doc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ProposalDetailVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

public interface ProposalMapper {
	
	// 유저정보조회
	public UserVO selectUserInfo(int userNo);
	
	// 견적서 의뢰정보조회
	public RequestVO selectReqInfo(RequestVO requestVO);
	
	// 견적서 단건조회
	public ProposalVO selectPpsInfo(ProposalVO proposalVO);
	
	// 견적서 상세조회
	public List<ProposalDetailVO> selectPpsDetailList(@Param("proposalNo")int proposalNo);
	
	// 견적서 등록
	public int insertPpsInfo(ProposalVO proposalVO);

	// 견적서 상세 등록
	public int insertPpsDetailInfo(ProposalDetailVO proposalDetailVO);
	
	// 견적서 수정
	public int updatePpsInfo(ProposalVO proposalVO);
	
	// 견적서 삭제
	public int deletePpsInfo(@Param("proposalNo")int proposalNo);
	
	// 견적서 상세 삭제
	public int deletePpsDetailInfo(@Param("proposalNo")int proposalNo);
	
	// 견적서 목록 조회(특정 의뢰와 관련해 본인이 작성한 견적서 목록)
	public List<ProposalVO> selectPpsListInfo(ProposalVO proposalVO);
	
	// 견적서 단건 전송
	public int sendPpsInfo(@Param("proposalNo")int proposalNo);
	
	// 견적서 파일 첨부
	public int updatePpsFileInfo(ProposalVO proposalVO);
	
	// 파일 정보 DB 등록 attr
	public int insertFileAttrInfo(ProposalVO proposalVO);
	
	// 파일 정보 DB 등록
	public int insertFileInfo(FileVO fileVO);
	
	// 파일 정보 DB 삭제
	public int deleteFileInfo(@Param("fileId")int fileId);
	
	// 파일 정보 조회
	public List<FileVO> selectFileList(@Param("proposalNo")int proposalNo);
}

