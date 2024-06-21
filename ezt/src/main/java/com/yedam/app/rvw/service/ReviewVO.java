package com.yedam.app.rvw.service;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewVO {
	private Integer reviewNo;
	private int requestNo;
	private int writer;
	private int worker;
	private int star;
	private String content;
	private Date writeDt;
	private String reviewDivision;
	private int fileId;
}
