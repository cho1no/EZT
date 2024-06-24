package com.yedam.app.common.service;

import java.util.List;

import com.yedam.app.common.service.CommonCodeVO;

public interface CommonCodeService {
	
	public List<CommonCodeVO> selectCommonCodeAll(String codeType);
}
