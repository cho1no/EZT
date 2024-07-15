package com.yedam.app.rvw.mapper;

import java.util.List;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.rvw.service.WorkerReplyVO;

public interface ReviewMapper {
	
	//후기 전체조회
	public List<ReviewVO> reviewList(Criteria cri);
	
	//후기 단건조회
	public ReviewVO reviewInfo(ReviewVO reviewVO);

	//후기 등록
	public int insertReview(ReviewVO reviewVO);
	
	//후기 수정
	public int updateReview(ReviewVO reviewVO);
	
	//후기 삭제
	public int deleteReview(int reviewNo);
	
	
	
	//후기 전체 게시물 갯수
	public int getTotalCount(Criteria cri);
	
	
	
	
	//댓글 조회
	public WorkerReplyVO replyInfo(WorkerReplyVO replyVO);

	//댓글 등록
	public int insertReply(WorkerReplyVO replyVO);
	
	//댓글 수정
	public int updateReply(WorkerReplyVO replyVO);
	
	//댓글 삭제
	public int deleteReply(int workerReplyNo);
	
	//파일 등록
	public int insertFileAttrRvwInfo(ReviewVO reviewVO);

	
}
