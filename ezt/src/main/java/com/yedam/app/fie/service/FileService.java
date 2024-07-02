package com.yedam.app.fie.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;

public interface FileService {
	public List<FileVO> uploadFiles(MultipartFile[] uploadFile);
	
	public int deleteFile(List<FileVO> fileVO) throws UnsupportedEncodingException;
	
	public ResponseEntity<Resource> downlodeFile(String fileName);
}
