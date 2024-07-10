package com.yedam.app.rpt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.rpt.service.CttReportVO;

public interface ReportMapper {
	
	// 공사 보고 유형 : 계약서 상세 내역
	public List<String> selectCttDivision(@Param("contractNo")int contractNo);
	
	// 공사 보고 등록
	public int insertCttReportInfo(CttReportVO cttReportVO);
	
	// 공사 보고 조회
	public CttReportVO selectCttInfo(@Param("cttReportNo")int cttReportNo);

}
