package com.yedam.app.wkr.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CareerVO {
	private Integer careerNo;
	private int usersNo;
	private String usersName;
	private String careerInfo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date careerStartDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date careerEndDt;
	
	private int fileId;
	private String careerAccessTf;
	private String careerAccessTfNm;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date writeDt;
}
