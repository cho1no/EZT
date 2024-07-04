package com.yedam.app.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.pay.service.PayVO;

@Mapper
public interface ProcessMapper {
	// 결제 전체 조회
	public List<PayVO> selectPayList();
}
