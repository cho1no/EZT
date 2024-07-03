package com.yedam.app.tem.service;

import lombok.Data;

@Data
public class TeamWorkCategoryVO {
	private String workCode; //작업코드
	private int teamNo;	//팀번호
	private int headcount; //인원수
	private String hireCompleteTf; //모집마감여부
}
