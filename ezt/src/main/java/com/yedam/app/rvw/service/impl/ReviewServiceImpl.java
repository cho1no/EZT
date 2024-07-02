package com.yedam.app.rvw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.rvw.mapper.ReviewMapper;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	
	//후기 전체조회
	@Override
	public List<ReviewVO> reviewList(Criteria cri) {
		
		return reviewMapper.reviewList(cri);
	}
	//후기 단건조회
	@Override
	public ReviewVO reviewInfo(ReviewVO reviewVO) {
		
		return reviewMapper.reviewInfo(reviewVO);
	}
	//후기 등록
	@Override
	public int insertReview(ReviewVO reviewVO) {
		int result = reviewMapper.insertReview(reviewVO);
		return result == 1 ? reviewVO.getReviewNo() : -1;
	}
	//후기 수정
	@Override
	public boolean updateReview(ReviewVO reviewVO) {
		
		return reviewMapper.updateReview(reviewVO) == 1;
	}
	//후기 삭제
	@Override
	public int deleteReview(int reviewNo) {
		
		return reviewMapper.deleteReview(reviewNo);
	}
	
	//전체 페이지 수 
	@Override
	public int getTotal(Criteria cri) {
		return reviewMapper.getTotalCount(cri);
	}

}
