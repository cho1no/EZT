package com.yedam.app.rvw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.fie.mapper.FileMapper;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.rvw.mapper.ReviewMapper;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.rvw.service.WorkerReplyVO;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	@Autowired
	FileMapper fileMapper;
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
		//파일 등록
		if(reviewVO.getFileVO() != null) {
			reviewMapper.insertFileAttrRvwInfo(reviewVO);
			reviewVO.getFileVO().forEach(e->{
				e.setFileId(reviewVO.getFileId());
				fileMapper.insertFileInfo(e);
			});
		}
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
	
	//후기 전체 페이지 수 
	@Override
	public int getTotal(Criteria cri) {
		return reviewMapper.getTotalCount(cri);
	}
	
	
	
	
	//댓글 조회
	@Override
	public WorkerReplyVO replyInfo(WorkerReplyVO replyVO) {
		
		return reviewMapper.replyInfo(replyVO);
	}
	//댓글 등록
	@Override
	public int insertReply(WorkerReplyVO replyVO) {
		int result = reviewMapper.insertReply(replyVO);
		return result == 1 ? replyVO.getWorkerReplyNo() : -1;
	}
	
	//댓글 수정
	@Override
	public boolean updateReply(WorkerReplyVO replyVO) {
		
		return reviewMapper.updateReply(replyVO) == 1;
	}
	
	//댓글 삭제
	@Override
	public int deleteReply(int workerReplyNo) {
		
		return reviewMapper.deleteReply(workerReplyNo);
	}
	
	

}
