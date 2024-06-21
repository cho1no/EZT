package com.yedam.app.doc.service;

import java.util.Date;

import lombok.Data;

@Data
public class ContractVO {
	private Integer contractNo; // 계약서 번호
	private String cttName; // 공사 명
	private int cttPrice; // 공사 금액
	private String requesterInfo; // 의뢰자 정보
	private String requesterAddress; // 의뢰자 주소
	private String requesterAccount; // 의뢰자 계좌
	private String workerInfo; // 작업자 정보
	private String workerAddress; // 작업자 주소
	private String workerAccount; // 작업자 계좌
	private String contractDivision; // 계약서 구분
	private int unityContractNo; // 통일 계약서 번호
	private int fileId; // 파일 아이디
	private int proposalNo; // 견적서 번호
	private Date writeDt; // 작성 일
	private Date updateDt; // 수정 일
	private String contractState; // 계약서 상태
}
