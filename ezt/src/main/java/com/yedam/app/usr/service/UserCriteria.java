package com.yedam.app.usr.service;

import lombok.Data;

@Data
public class UserCriteria {
	//페이징
	private int pageNum;
	private int amount;
	private int writer;
	
	//검색
	private String type;
	private String keyword;
	
	public UserCriteria() {
		this(1,16);
	}
	public UserCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }
	
}
