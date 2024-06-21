package com.yedam.app.doc.service;

import java.util.Date;

import lombok.Data;

@Data
public class UnityContractVO {
	private Integer unityContractNo; // 통일 계약서 번호
	private String contractTermsContent; // 계약서 약관 내용
	private Date writeDt; // 작성 일
	private String useTf; // 사용 여부
	private String basicContractTf; // 기본 계약서 여부
}
