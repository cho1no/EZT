package com.yedam.app.rvw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.rvw.mapper.ReviewMapper;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	
	//후기 전체조회
	@Override
	public List<ReviewVO> reviewList() {
		
		return reviewMapper.reviewList();
	}
	//후기 단건조회
	@Override
	public ReviewVO reviewInfo(ReviewVO reviewVO) {
		
		return reviewMapper.reviewInfo(reviewVO);
	}
	//후기 등록
	@Override
	public int insertReview(ReviewVO reviewVO) {
		
		return 0;
	}
	//후기 수정
	@Override
	public boolean updateReview(ReviewVO reviewVO) {
		
		return false;
	}
	//후기 삭제
	@Override
	public int deleteReview(int reviewNo) {
		
		return 0;
	}

}
