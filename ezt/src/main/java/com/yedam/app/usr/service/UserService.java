package com.yedam.app.usr.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.pay.service.PayVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.wkr.service.WorkerRvwCriteria;

public interface UserService {
	
	//사용자 정보조회
	public UserVO userInfo(String id);
	
	//수정
	public Map<String, Object> updateUser(UserVO userVO);
	public int insertUserImg(UserVO userVO);
	
	//비밀번호 조회
	public String selectEncPw(int usersNo);
	//비밀번호 변경
	public int updatePw(int usersNo, String currentPw, String newPassword);

	//후기목록 조회
	public List<ReviewVO> userReviewList(UserRvwCriteria cri);
	
	//의뢰목록 조회
	public List<RequestVO> userReqList(UserReqCriteria cri);
	
	//사용자 계약서목록
	public List<ContractVO> selectUserContractList(WorkerRvwCriteria cri);
	//사용자 결제명세서목록
	public List<PayVO> selectUserPayList(WorkerRvwCriteria cri);

	//회원탈퇴 (상태 수정)
	public int userStateUpdate(UserVO userVO);
	
	//전체 리뷰 데이터 갯수
	public int reviewGetTotal(UserRvwCriteria cri);
	
	//전체 의뢰 데이터 갯수
	public int requestGetTotal(UserReqCriteria cri);

	//계약서 데.갯
	public int getTotalUserCtt(WorkerRvwCriteria cri);
	//결제명세서 데.갤
	public int getTotalUserPay(WorkerRvwCriteria cri);
	//작업자 찾기
	public List<UserVO> selectFindWorkerList(FindWorkerCriteria cri);
	
	//총 작업자 수
	public int workerListGetTotal(FindWorkerCriteria cri);
}
