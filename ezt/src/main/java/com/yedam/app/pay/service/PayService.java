package com.yedam.app.pay.service;

public interface PayService {

	// 결제 등록
	public int payInsert(PayVO payVO);
	
	// 결제 조회
	public PayVO payInfo(PayVO payVO);
}
