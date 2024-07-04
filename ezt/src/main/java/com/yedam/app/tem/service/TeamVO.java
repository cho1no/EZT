package com.yedam.app.tem.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TeamVO {
	private Integer teamNo;	//팀신청번호
	private String workTitle;	//팀신청 작업 제목
	private String workContent;	//팀신청 작업 내용
	private int writer;	//팀신청자
	private Date writeDt;	//팀신청 작성일
	private int contractNo;	//계약서 번호
	
	
	//조인컬럼
	private String writerName;
	private String regionCode;
	private String categoryCode;
	private int headCount;
	private int requestNo;
	
	
	
	private List<TeamWorkCategoryVO> workCategoryVO;
}
