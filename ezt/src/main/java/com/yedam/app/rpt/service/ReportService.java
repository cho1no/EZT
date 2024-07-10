package com.yedam.app.rpt.service;

import java.util.List;

public interface ReportService {

	// 공사 보고 유형 조회
	public List<String> reportDiviSelect(int contractNo);
	
	// 공사 보고 등록
	public int reportInsert(CttReportVO cttReportVO);
	
	// 공사 보고 조회
	public CttReportVO reportSelect(int cttReportNo);
}
