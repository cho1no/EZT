package com.yedam.app.doc.service;

import java.util.Date;

import lombok.Data;

@Data
public class SignsVO {
	
	private Integer signsNo;
	private int contractNo;
	private int usersNo;
	private String signsContent;
	private Date signsDt;
}
