package com.yedam.app.rpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.fie.mapper.FileMapper;
import com.yedam.app.rpt.mapper.ReportMapper;
import com.yedam.app.rpt.service.CttReportVO;
import com.yedam.app.rpt.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportMapper reportMapper;
	
	@Autowired
	FileMapper fileMapper;

	// 공사 보고 유형 조회
	@Override
	public List<String> reportDiviSelect(int contractNo) {
		return reportMapper.selectCttDivision(contractNo);
	}
	
	// 공사 보고 등록
	@Override
	public int reportInsert(CttReportVO cttReportVO) {
		int result = reportMapper.insertCttReportInfo(cttReportVO);
		
		// 파일 등록
		if(cttReportVO.getFileList() != null) {
			fileMapper.insertFileAttrRptInfo(cttReportVO);
			cttReportVO.getFileList().forEach(e->{
				e.setFileId(cttReportVO.getFileId());
				fileMapper.insertFileInfo(e);
			});
			
			
		}
		return result == 1 ? cttReportVO.getCttReportNo() : -1;
	}
	
	// 공사 보고 조회
	@Override
	public CttReportVO reportSelect(int cttReportNo) {
		CttReportVO cvo = reportMapper.selectCttInfo(cttReportNo);
		
		List<FileVO> fileList = fileMapper.selectRptFileList(cttReportNo);
		cvo.setFileList(fileList);
		
		return cvo;
	}

}
