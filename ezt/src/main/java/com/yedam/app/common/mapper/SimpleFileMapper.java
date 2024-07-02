package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.service.FileVO;

public interface SimpleFileMapper {
	public int insertFileAttrInfo(FileVO fileVO);
	
	public int insertFileInfo(FileVO fileVO);
	
	public List<FileVO> selectFileList();
}
