package com.yedam.app.doc.service;

import java.util.Date;

import lombok.Data;

@Data
public class ProposalVO {
	private Integer proposalNo; // 견적서 번호
	private int requestNo; // 의뢰 번호
	private Date cttStartDt; // 공사 시작 일
	private Date cttEndDt; // 공사 종료 일
	private Date writeDt; // 작성 일 
	private String workerPostcode; // 작업자 우편번호
	private String workerAddress; // 작업자 주소
	private String workerDetailAddress; // 작업자 상세 주소
	private String comments; // 비고
	private int fileId; // 파일 아이디
	private int worker; // 작업자
	private int requester; // 의뢰자
	private Date updateDt; //수정 일
	private String proposalState; //견적서 상태
}
