package com.yedam.app.rvw.service;

import java.util.Date;

import lombok.Data;

@Data
public class WorkerReplyVO {
	private Integer workerReplyNo; //댓글 번호
	private int reviewNo;	//리뷰 번호
	private int writer;	//작성자
	private String reply;	//댓글
	private Date writeDt;	//댓글 작성일
}
