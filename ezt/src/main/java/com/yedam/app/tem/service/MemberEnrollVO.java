package com.yedam.app.tem.service;

import lombok.Data;

@Data
public class MemberEnrollVO {
	private Integer enrollNo;	//신청 번호
	private String worker; 	//신청자(작업자)
	private String content;	//내용
	private String processResult;	//처리 상태
	private String workCode;	//작업 카테고리
	private int teamNo;	//팀 번호
	
	private int meTeamNo;
	private String categoryCode;
	private int usersNo;
	private int career; //지원자 경력계산
	private int headcount; //카테고리별 지원자 수 
	
	
}
