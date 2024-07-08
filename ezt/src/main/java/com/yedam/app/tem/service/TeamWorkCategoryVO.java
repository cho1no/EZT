package com.yedam.app.tem.service;

import lombok.Data;

@Data
public class TeamWorkCategoryVO {
	private String workCode; //작업코드(code_name)
	private int teamNo;	//팀번호
	private int headcount; //인원수
	private String hireCompleteTf; //모집마감여부
	private String categoryCode; //작업코드(code_no)
	private int partCount; // 작업코드별 인원 수 
	
	
	private int twcTeamNo;
}
