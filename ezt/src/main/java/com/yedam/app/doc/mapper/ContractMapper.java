package com.yedam.app.doc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractVO;

public interface ContractMapper {
	
	// 계약서 등록
	public int insertConInfo(ContractVO contractVO);
	
	// 계약서 상세 등록
	public int insertConDetailInfo(ContractDetailVO contractDetailVO);
	
	// 계약서 조회
	public ContractVO selectConInfo(ContractVO contractVO);
	
	// 계약서 상세 조회
	public List<ContractDetailVO> selectConDetailInfo(@Param("contractNo")int contractNo);
	
	// 파일 삭제
	public int deleteConFileInfo(@Param("fileId")int fileId);
}
