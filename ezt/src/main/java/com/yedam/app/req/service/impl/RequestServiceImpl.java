package com.yedam.app.req.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.mapper.RequestMapper;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestMapper requestMapper;
	
	@Override
	public List<RequestVO> requestList() {
		
		return requestMapper.selectRequestAll();
	}

}
