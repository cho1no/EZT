package com.yedam.app.rvw.service;

import java.util.List;

public interface ReviewService {

	//후기전체조회
	public List<ReviewVO> reviewList();
	//후기단건조회
	public ReviewVO reviewInfo(ReviewVO reviewVO);
	//후기 등록
	public int insertReview(ReviewVO reviewVO);
	//후기 수정
	public boolean updateReview(ReviewVO reviewVO);
	//후기 삭제
	public int deleteReview(int reviewNo);
}
