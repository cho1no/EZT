package com.yedam.app.wkr.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PortfolioVO {
	private Integer portfolioNo; //포트폴리오 번호
	private String title; //제목
	private String content; //내용
	private String pyung; //평수
	private int price; //금액
	private int usersNo; //유저번호
	private int fileId; //파일아이디
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date writeDt; //작성일
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updateDt; //수정일
	
	private String workTime; //작업시간(기간)
	
	private String categoryCode; //작업분야(카테고리)
}
