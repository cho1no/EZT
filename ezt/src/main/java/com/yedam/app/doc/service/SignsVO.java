package com.yedam.app.doc.service;

import java.util.Date;

import lombok.Data;

@Data
public class SignsVO {
	
	private Integer signsNo; // 사인 번호
	private int contractNo; // 계약서 번호
	private int usersNo; // 유저 번호
	private String signsContent; // 사인 바이트
	private Date signsDt; // 사인 등록 일
}
