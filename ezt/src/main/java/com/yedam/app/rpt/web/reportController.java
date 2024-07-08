package com.yedam.app.rpt.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.fie.service.FileService;


@Controller
public class reportController {

	@Autowired
	FileService fileService;
	
	@PostMapping("rptInsert")
	public ResponseEntity<String> rptInsert(MultipartFile[] uploadFile) {
		
		List<FileVO> list = fileService.uploadFiles(uploadFile);
		
		return new ResponseEntity<>("String", HttpStatus.OK);
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		return fileService.getFile(fileName);
	}
}
