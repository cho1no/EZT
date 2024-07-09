package com.yedam.app.usr.service;

import lombok.Data;

@Data
public class FindWorkerCriteria {
	//페이징
	private int pageNum;
	private int amount;
	private int usersNo;
	
	//검색
	private String type;
	private String keyword;
	
	public FindWorkerCriteria() {
		this(1,8);
	}
	public FindWorkerCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	
	
}
