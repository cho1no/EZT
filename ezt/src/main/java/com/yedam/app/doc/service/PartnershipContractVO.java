package com.yedam.app.doc.service;

import lombok.Data;

@Data
public class PartnershipContractVO {
	private Integer partnershipContractNo; // 동업 계약서 번호
	private int contractNo; // 계약서 번호
	private String leaderCategoryCode; // 팀장 분야 코드
	private String memberCategoryCode; // 팀원 분야 코드
}
