package com.yedam.app.usr.service;

import lombok.Data;

@Data
public class UserRevCriteria {
	//페이징
	private int pageNum;
	private int amount;
	private int writer;
	
	//검색
	private String type;
	private String keyword;
	
	public UserRevCriteria() {
		this(1,5);
	}
	public UserRevCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	
	
}
