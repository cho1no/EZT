package com.yedam.app.doc.service;

import lombok.Data;

@Data
public class PartnershipContractVO {
	private Integer partnershipContractNo; // 동업 계약서 번호
	private int contractNo; // 계약서 번호
	private String leaderCategoryCode; // 팀장 분야 코드
	private String memberCategoryCode; // 팀원 분야 코드
	
	private int teamNo; // 팀 번호
	private int leaderNo; // 리더 유저 번호
	private int memberNo; // 멤버 유저 번호
}
