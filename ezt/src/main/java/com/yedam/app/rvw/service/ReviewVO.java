package com.yedam.app.rvw.service;

import java.util.Date;
import java.util.List;

import com.yedam.app.common.service.FileVO;

import lombok.Data;

@Data
public class ReviewVO {
	private Integer reviewNo; //리뷰번호
	private int requestNo;	//의뢰번호
	private int writer;	//작성자
	private int worker;	//작업자
	private int star;	//별점
	private String content;	//내용
	private Date writeDt;	//작성일시
	private String reviewDivision;	//리뷰구분(1차후기(F), 하자보수후기(S) 여부 )
	private int fileId;	//파일아이디
	private String originalFileName;
	private String savePath;
	private String saveName;
	private String ext;
	
	//조인컬럼
	private String regionCode; //지역카테고리
	private String categoryCode; //분야 카테고리
	private String cttPlace; //공간형태..?(ex.원룸/아파트)
	private int cttPlaceArea;// 공간면적
	private String writerName;	//작성자 이름
	private String workerName;	//작업자 이름
	
	private List<FileVO> fileVO;
}
