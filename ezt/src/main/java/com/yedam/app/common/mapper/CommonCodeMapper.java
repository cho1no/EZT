package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.service.CommonCodeVO;

public interface CommonCodeMapper {
	
	public List<CommonCodeVO> selectCommonCodeAll(String codeType);
}
