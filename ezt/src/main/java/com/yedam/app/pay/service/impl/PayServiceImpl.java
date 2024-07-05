package com.yedam.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.pay.mapper.PayMapper;
import com.yedam.app.pay.service.PayService;
import com.yedam.app.pay.service.PayVO;

@Service
public class PayServiceImpl implements PayService{

	@Autowired
	PayMapper payMapper;
	
	// 결제 등록
	@Override
	public int payInsert(PayVO payVO) {
		int result = payMapper.insertPayInfo(payVO);
		return result ==1 ? payVO.getPayNo() : -1;
	}
	
	// 결제 상세 조회
	@Override
	public PayVO payInfo(PayVO payVO) {
		return payMapper.selectPayInfo(payVO);
	}
}
