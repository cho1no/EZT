package com.yedam.app.doc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.SignsVO;

public interface ContractMapper {
	
	// 계약서 등록
	public int insertConInfo(ContractVO contractVO);
	
	// 계약서 상세 등록
	public int insertConDetailInfo(ContractDetailVO contractDetailVO);
	
	// 계약서 조회
	public ContractVO selectConInfo(@Param("proposalNo")int proposalNo);
	
	// 계약서 상세 조회
	public List<ContractDetailVO> selectConDetailInfo(@Param("contractNo")int contractNo);
	
	// 파일 attr 등록
	public int insertFileAttrConInfo(ContractVO contractVO);
	
	// 파일 조회
	public List<FileVO> selectConFileList(@Param("contractNo")int contractNo);
	
	// 파일 삭제
	public int deleteConFileInfo(ContractVO contractVO);
	
	// 계약서 수정
	public int updateConInfo(ContractVO contractVO);
	
	// 계약서 상세 삭제
	public int deleteConDetailInfo(@Param("contractNo")int contractNo);
	
	// 파일 정보 DB 등록
	public int insertConFileInfo(FileVO fileVO);
	
	// 서명 조회
	public SignsVO selectSignInfo(@Param("contractNo")int contractNo, @Param("usersNo")int usersNo);
	
}
