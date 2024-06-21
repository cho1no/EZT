package com.yedam.app.doc.service;

import lombok.Data;

@Data
public class ProposalDetailVO {
	private Integer proposalDetailNo; // 견적서 상세 번호
	private int proposalNo; // 견적서 번호
	private String product; // 품명
	private String unit; // 단위
	private int qty; // 수량
	private double unitprice; // 단가
	private String comments; // 비고
	private String division; // 구분

}
