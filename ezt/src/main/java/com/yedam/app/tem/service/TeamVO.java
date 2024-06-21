package com.yedam.app.tem.service;

import java.util.Date;

import lombok.Data;

@Data
public class TeamVO {
	private Integer teamNo;
	private String workTitle;
	private String workContent;
	private int writer;
	private Date writeDt;
	private int contractNo;
}
