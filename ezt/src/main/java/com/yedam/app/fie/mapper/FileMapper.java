package com.yedam.app.fie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rpt.service.CttReportVO;

public interface FileMapper {
	
	// 공용
	// 파일 상세 등록
	public int insertFileInfo(FileVO fileVO);
	// 파일 상세 삭제
	public int deleteFileInfo(@Param("fileId")int fileId);
	
	
	// -- 견적서
	// 파일 arrt 등록& 견적서 파일 ID 등록 : 프로시저 이용[insert_proposal_file]
	public int insertFileAttrProInfo(ProposalVO proposalVO);
	// 파일 정보 조회
	public List<FileVO> selectProFileList(@Param("proposalNo")int proposalNo);
	
	
	// -- 계약서
	// 파일 attr 등록 계약서 파일 ID 등록 : 프로시저 이용[insert_contract_file]
	public int insertFileAttrConInfo(ContractVO contractVO);
	// 파일 조회
	public List<FileVO> selectConFileList(@Param("contractNo")int contractNo);
	
	// -- 공사보고
	// 파일 attr 등록 계약서 파일 ID 등록 : 프로시저 이용[insert_report_file]
	public int insertFileAttrRptInfo(CttReportVO cttReportVO);
	// 파일 조회
	public List<FileVO> selectRptFileList(@Param("cttReportNo")int cttReportNo);
}
