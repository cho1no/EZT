package com.yedam.app.wkr.service;

import lombok.Data;

@Data
public class WorkerPFCriteria {
	//페이징
	private Integer pageNum;
	private int amount;
	private int usersNo;
	private String title;
	private String careerAccessTf;
	//검색
	private String type;
	private String keyword;
	
	public WorkerPFCriteria() {
		this(1,6);
	}
	public WorkerPFCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	public int getPage() {
		return pageNum;
	}
	
	
	
}
