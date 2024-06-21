package com.yedam.app.rpt.service;

import java.util.Date;

import lombok.Data;

@Data
public class cttReportVO {
	private Integer cttReportNo; // 공사 보고 번호
	private String title; // 제목
	private String detailContent; // 상세 내용
	private String cttDivision; // 공사 구분
	private String accessState; // 승인 상태
	private Date cttReportDt; // 공사 보고 일
	private int fileId; // 파일 아이디
	private int contractNo; // 계약서 번호
}
