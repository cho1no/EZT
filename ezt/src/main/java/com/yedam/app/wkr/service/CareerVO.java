package com.yedam.app.wkr.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.yedam.app.common.service.FileVO;

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
	
	private String careerAccessTf;
	private String careerAccessTfNm;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date writeDt;
	
	private int fileId;
	private List<FileVO> fileVO;
	
	private String savePath;
	private String saveName;
	private String originalFileName;
	private String ext;
	private String bossTf;
}
