package com.yedam.app.rvw.service;

import java.util.List;

import com.yedam.app.req.service.Criteria;

public interface ReviewService {

	//후기전체조회
	public List<ReviewVO> reviewList(Criteria cri);
	//후기단건조회
	public ReviewVO reviewInfo(ReviewVO reviewVO);
	//후기 등록
	public int insertReview(ReviewVO reviewVO);
	//후기 수정
	public boolean updateReview(ReviewVO reviewVO);
	//후기 삭제
	public int deleteReview(int reviewNo);
	
	
	
	//후기 전체 데이터 갯수
	public int getTotal(Criteria cri);
	
	
}
