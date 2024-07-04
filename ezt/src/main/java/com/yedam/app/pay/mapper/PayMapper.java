package com.yedam.app.pay.mapper;

import com.yedam.app.pay.service.PayVO;

public interface PayMapper {

	// 결제 등록
	public int insertPayInfo(PayVO payVO);
	
	// 결제 조회
	public PayVO selectPayInfo(PayVO payVO);
}
