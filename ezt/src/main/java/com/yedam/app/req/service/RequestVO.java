package com.yedam.app.req.service;

import java.util.Date;
import java.util.List;

import com.yedam.app.common.service.FileVO;

import lombok.Data;

@Data
public class RequestVO {
	private Integer requestNo;             //의뢰번호
	private int requester;				   //의뢰자
	private String cttPlace;			   //공사공간
	private String cttPlaceNm;			   //공사공간
	
	private int cttPlaceArea;			   //공사공간면적
	private String cttPlaceSituation;	   //공사공간상황
	private String cttPlaceSituationNm;	   //공사공간상황
	private int hopeCttBudget;			   //희망공사예산
	private Date hopeCttStartDt;		   //희망공사시작일
	private Date hopeCttEndDt;			   //희망공사종료일
	private String postcode;			   //우편번호	
	private String address;				   //주소
	private String detailAddress;		   //상세주소
	private String title;				   //제목
	private String content;				   //내용
	private Date writeDt;				   //작성일시
	private String requestState;		   //의뢰상태
	private String requestStateNm;		   //의뢰상태
	private String regionCode;			   //지역코드
	private String regionCodeNm;			   //지역코드
	private int fileId;					   //파일아이디
	private String categoryCode;		   //분야코드
	private String categoryCodeNm;		   //분야코드
	
	//조인 컬럼
	private String usersName;			  //유저이름
	
	List<FileVO> fileVO;
}
