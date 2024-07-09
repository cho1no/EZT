package com.yedam.app.tem.service;

import lombok.Data;

@Data
public class MemberVO {
	private Integer memberNo; //팀원번호
	private int usersNo; //유저번호
	private String memberDivision; //멤버구분 팀장은 =>B 팀원은 =>M으로 구분
	private String workCode; //작업코드
	private int partnershipContractNo; //동업계약서 번호
	private int teamNo;//팀번호
}
