package com.yedam.app.rvw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.rvw.mapper.WorkerReplyMapper;
import com.yedam.app.rvw.service.WorkerReplyService;
import com.yedam.app.rvw.service.WorkerReplyVO;
@Service
public class WorkerReplyServiceImpl implements WorkerReplyService {
	@Autowired
	WorkerReplyMapper replyMapper;
	
	//댓글 조회
	@Override
	public WorkerReplyVO replyInfo(WorkerReplyVO replyVO) {
		
		return replyMapper.replyInfo(replyVO);
	}
	//댓글 등록
	@Override
	public int insertReply(WorkerReplyVO replyVO) {
		int result = replyMapper.insertReply(replyVO);
		return result == 1 ? replyVO.getWorkerReplyNo() : -1;
	}
	
	//댓글 수정
	@Override
	public boolean updateReply(WorkerReplyVO replyVO) {
		
		return replyMapper.updateReply(replyVO) == 1;
	}
	
	//댓글 삭제
	@Override
	public int deleteReply(int workerReplyNo) {
		
		return replyMapper.deleteReply(workerReplyNo);
	}

}
