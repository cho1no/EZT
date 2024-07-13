package com.yedam.app.rpt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.rpt.service.CttReportVO;

public interface ReportMapper {
	
	// 공사 보고 유형 : 계약서 상세 내역
	public List<String> selectCttDivision(@Param("contractNo")int contractNo);
	
	// 공사 보고 등록
	public int insertCttReportInfo(CttReportVO cttReportVO);
	
	// 공사 보고 조회
	public CttReportVO selectCttInfo(@Param("cttReportNo")int cttReportNo);
	
	// 공사 보고 수정
	public int updateCttInfo(CttReportVO cttReportVO);
	
	// 공사 보고 삭제
	public int deleteCttInfo(@Param("fileId")int fileId);
	
	// 공사 보고 승인
	public int updateApprove(@Param("cttReportNo")int cttReportNo);
}
