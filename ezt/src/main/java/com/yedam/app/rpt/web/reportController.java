package com.yedam.app.rpt.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.fie.service.FileService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rpt.service.CttReportVO;
import com.yedam.app.rpt.service.ReportService;
import com.yedam.app.sgi.service.LoginUserVO;


@Controller
public class reportController {

	@Autowired
	FileService fileService;
	
	@Autowired
	ReportService reportService;
	
	// 공사 보고 유형 값 조회
	@GetMapping("rptDivi")
	@ResponseBody
	public HashMap<String, Object> rptInsert(ContractVO contractVO, Model model
			   , @AuthenticationPrincipal LoginUserVO user) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> diviList = reportService.reportDiviSelect(contractVO.getContractNo());
		map.put("divi", diviList);
		map.put("contractNo", contractVO.getContractNo());
		model.addAttribute("dd", diviList);
		
		return map;
	}
	
	// 공사 보고 등록
	@PostMapping("rptInsertInfo")
	@ResponseBody
	public int uploadAjaxRpt(MultipartFile[] uploadFile, CttReportVO cttReportVO) {
		if(uploadFile != null && uploadFile.length > 0) {
			List<FileVO> list = fileService.uploadFiles(uploadFile);
			if(!list.isEmpty()) {
				cttReportVO.setFileList(list);
			}
		}
		int no = reportService.reportInsert(cttReportVO);
		return no;
	}
	
	// 공사 보고 상세
	@GetMapping("rptInfo")
	@ResponseBody
	public CttReportVO rptInfo(CttReportVO cttReportVO, Model model
			   , @AuthenticationPrincipal LoginUserVO user){
		
		CttReportVO cvo = reportService.reportSelect(cttReportVO.getCttReportNo());
		return cvo;
	}
	
	// 공사 보고 수정
	@PostMapping("rptUpdate")
	@ResponseBody
	public CttReportVO rptUpdate(MultipartFile[] uploadFile, CttReportVO cttReportVO) {
		// DB 파일 삭제
		List<FileVO> fileList = reportService.fileSelect(cttReportVO);
		if(!fileList.isEmpty()) {
			try {
				fileService.deleteFile(fileList);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		// 수정
		if (uploadFile != null  && uploadFile.length > 0) {
			List<FileVO> list = fileService.uploadFiles(uploadFile);
			if (!list.isEmpty()) {
				cttReportVO.setFileList(list);
			}
		}
		int no = reportService.reportUpdate(cttReportVO);
		
		CttReportVO cvo = new CttReportVO();
		
		if(no > -1 ) {
			cvo = reportService.reportSelect(cttReportVO.getCttReportNo());
		}
		
		
		return cvo;
	}
	
	
	// 공사 보고 삭제
	@PostMapping("rptDelete")
	@ResponseBody
	public ResponseEntity<String> rtpDelete(@RequestBody List<FileVO> fileVO) 
			throws UnsupportedEncodingException { 
		fileService.deleteFile(fileVO);
		reportService.reportDelete(fileVO.get(0).getFileId());
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
	// 첨부 파일 업로드
	@PostMapping("rptFileInsert")
	@ResponseBody
	public List<FileVO> rptFileInsert(MultipartFile[] uploadFile) {
		
		List<FileVO> list = fileService.uploadFiles(uploadFile);
		
		return list;
	}
	// 첨부 파일 삭제
	@PostMapping("rptFileDelete")
	@ResponseBody
	public ResponseEntity<String> rptFileDelete(@RequestBody List<FileVO> fileVO) throws UnsupportedEncodingException {

		fileService.deleteFile(fileVO);
		List<FileVO> sFileVO = new ArrayList<FileVO>();

		for(FileVO file : fileVO) {
			file.setSaveName("s_" + file.getSaveName());
			sFileVO.add(file);
		}
		fileService.deleteFile(sFileVO);
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	// 섬네일
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		return fileService.getFile(fileName);
	}
}
