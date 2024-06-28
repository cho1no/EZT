package com.yedam.app.fie.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;

public interface FileService {
	public List<FileVO> uploadFiles(MultipartFile[] uploadFile);
}
