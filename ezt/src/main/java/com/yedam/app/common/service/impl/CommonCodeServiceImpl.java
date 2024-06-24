package com.yedam.app.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.mapper.CommonCodeMapper;
import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.common.service.CommonCodeVO;
@Service
public class CommonCodeServiceImpl implements CommonCodeService {
	
	@Autowired
	CommonCodeMapper commonCodeMapper;
	@Override
	public List<CommonCodeVO> selectCommonCodeAll(String codeType) {
	
		return commonCodeMapper.selectCommonCodeAll(codeType);
	}

}
