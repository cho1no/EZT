package com.yedam.app.pay.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.pay.service.PayVO;

public interface PayMapper {

	// 결제 등록
	public int insertPayInfo(PayVO payVO);
	
	// 결제 조회
	public PayVO selectPayInfo(PayVO payVO);
	
	// 오늘 대금 지급 일자인 작업자
	public List<Map<String, String>> selectPaymentDtInfo();
	
	public int updatePaymentTf(@Param("contractDetailNo")int contractDetailNo);
}
