package com.yedam.app.usr.service;

import com.yedam.app.req.service.Criteria;

import lombok.Data;

@Data
public class UserReqPageDTO {
	
	private int startPage; //시작 페이지
	private int endPage;  //끝 페이지
	private boolean prev, next;
	
	private int total; //전체 게시물 수 
	private UserReqCriteria cri; // 페이지에서 보여주는 게시물 수 (amount), 현재 페이지 번호(pageNum)
	
	public UserReqPageDTO(UserReqCriteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 5.0)) * 5;
		this.startPage = this.endPage -4;
		int realEnd = (int) (Math.ceil((total * 1.0)/cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
			
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
	
	public UserReqPageDTO(int pageNum, int amount, int total) {
		this(new UserReqCriteria(pageNum, amount), total);
	}
	
	
}
