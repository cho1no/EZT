package com.yedam.app.doc.service;

import java.util.Date;

import lombok.Data;

@Data
public class ContractDetailVO {
	private Integer contractDetailNo; // 계약서 상세 번호
	private int contractNo; // 계약서 번호
	private int price; // 금액
	private Date paymentDt; // 지급 일
	private String history; // 내역
	private int round; // 회차
}
