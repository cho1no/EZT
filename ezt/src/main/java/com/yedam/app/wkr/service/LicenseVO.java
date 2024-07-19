package com.yedam.app.wkr.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LicenseVO {
	private Integer licenseNo; //자격증 번호 
	private String licenseDetailNo; // 자격증 상세번호
	private int usersNo; //유저 번호
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date licenseGetDt; //자격증 발급일
	
	private String licenseInfo; //자격증 정보
	private int fileId; //파일 아이디
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date writeDt; //작성일
	
	private String checkCenter; //인증기관
	
	private String savePath;
	private String saveName;
	private String originalFileName;
	private String ext;
}
