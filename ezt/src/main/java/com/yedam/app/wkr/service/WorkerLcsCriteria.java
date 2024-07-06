package com.yedam.app.wkr.service;

import lombok.Data;

@Data
public class WorkerLcsCriteria {
	//페이징
	private int pageNum;
	private int amount;
	private int usersNo;
	
	//검색
	private String type;
	private String keyword;
	
	public WorkerLcsCriteria() {
		this(1,10);
	}
	public WorkerLcsCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	
	
}
