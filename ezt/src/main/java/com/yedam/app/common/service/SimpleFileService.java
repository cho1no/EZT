package com.yedam.app.common.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SimpleFileService {
	/**
	 * 간편 업로드<br>
	 * 0. 필요한 Insert문 실행 이전에 실행한다.<br>
	 * 1. MultipartFile을 매개변수로 받는다.<br>
	 * 1-1. MultipartFile이란 input type="file" multiple 속성으로 들어온 데이터를 뜻함.<br>
	 * 2. 실행 시 int 타입의 파일 아이디를 return<br>
	 * 3. 실행 후 얻은 결과(fileId)를 필요한 VO에 setFileId<br>
	 * 4. 이후 각자의 Insert문 실행
	 * @param uploadFile
	 * @return fileId
	 */
	public int uploadFiles(MultipartFile[] uploadFile);
	
	public int deleteFile(List<FileVO> fileVO) throws UnsupportedEncodingException;
	
	public ResponseEntity<Resource> downlodeFile(String fileName);
}
