package com.yedam.app.rpt.service;

import java.util.List;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractDetailVO;


public interface ReportService {

	// 공사 보고 유형 조회
	public List<String> reportDiviSelect(int contractNo);
	
	// 공사 보고 등록
	public int reportInsert(CttReportVO cttReportVO);
	
	// 공사 보고 조회
	public CttReportVO reportSelect(int cttReportNo);
	
	// 공사 보고 수정
	public int reportUpdate(CttReportVO cttReportVO);
	
	// 공사 보고 삭제
	public int reportDelete(int fileId);
	
	// 공사 보고 파일 조회
	public List<FileVO> fileSelect(CttReportVO cttReportVO);
	
	// 공사 보고 승인
	public int reportApprove(int cttReportNo);
}
