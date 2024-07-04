package com.yedam.app.rvw.service;

public interface WorkerReplyService {
	//댓글 조회
	public WorkerReplyVO replyInfo(WorkerReplyVO replyVO);
	//댓글 등록
	public int insertReply(WorkerReplyVO replyVO);
	//댓글 수정
	public boolean updateReply(WorkerReplyVO replyVO);
	//댓글 삭제
	public int deleteReply(int workerReplyNo);
}
