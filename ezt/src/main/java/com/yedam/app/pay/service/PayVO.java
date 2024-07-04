package com.yedam.app.pay.service;

import java.util.Date;

import lombok.Data;

@Data
public class PayVO {
	private Integer payNo; // 결제 번호
	private int requester; // 의뢰자
	private int price; // 금액
	private Date payDt; // 결제 일
	private int contractNo; // 계약서 번호

	private String virtualAccount; // 가상 계좌 번호
	
	private int proposalNo; // 견적서 번호

}
