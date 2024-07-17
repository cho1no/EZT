package com.yedam.app.usr.service;

import lombok.Data;

@Data
public class UserReqCriteria {
	//페이징
	private Integer pageNum;
	private int amount;
	private int usersNo;
	
	private String requestState;
	private String categoryCode;
	
	//검색
	private String type;
	private String keyword;
	
	public UserReqCriteria() {
		this(1,9);
	}
	public UserReqCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	
	
}
