package com.yedam.app.tem.service;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDenyVO {
	private String content; //내용
	private int enrollNo;	//신청번호
	private int writer;		//작성자
	private Date writeDt;	//작성일자
}
