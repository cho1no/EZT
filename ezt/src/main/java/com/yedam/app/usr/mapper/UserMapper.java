package com.yedam.app.usr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.pay.service.PayVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.usr.service.FindWorkerCriteria;
import com.yedam.app.usr.service.UserReqCriteria;
import com.yedam.app.usr.service.UserRvwCriteria;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.WorkerRvwCriteria;

@Mapper
public interface UserMapper {
	//사용자 정보조회
	public UserVO selectUserInfo(String id);
	
	//사용자 회원가입
	void saveUser(UserVO userVO);
	
	//작업자 회원가입
	void saveWorker(UserVO userVO);

	//중복체크
	public int idChk(String id);
	
	public int nickChk(String nick);
	
	public int emailChk(String email);
	
	public int rnnChk(String rnn);

	//사용자정보 수정
	public int updateUserInfo(UserVO userVO);
	public int insertUserImg(UserVO userVO);
	
	//비밀번호 조회
	public String selectEncPw(int usersNo);
	//비밀번호 변경
	int updatePw(@Param("usersNo") int usersNo, @Param("newPassword") String newPassword);
	
	//사용자 후기목록
	public List<ReviewVO> selectUserRvwList(UserRvwCriteria cri);

	//사용자 의뢰목록
	public List<RequestVO> selectUserReqList(UserReqCriteria cri);
	
	//사용자 계약서목록
	public List<ContractVO> selectUserContractList(WorkerRvwCriteria cri);
	//사용자 결제명세서목록
	public List<PayVO> selectUserPayList(WorkerRvwCriteria cri);
	
	//사용자 탈퇴(상태 수정)
	public int updateUserState(UserVO userVO);

	
	//사용자 리뷰 갯수
	public int getTotalReviewCount(UserRvwCriteria cri);
	
	//사용자 의뢰 갯수
	public int getTotalRequestCount(UserReqCriteria cri);
	
	//계약서 갯수
	public int getTotalUserCtt(WorkerRvwCriteria cri);
	//결제명세서 갯수
	public int getTotalUserPay(WorkerRvwCriteria cri);
	//작업자 찾기
	public List<UserVO> selectFindWorkerList(FindWorkerCriteria cri);
	
	//총 작업자 수
	public int getTotalWorkerCount(FindWorkerCriteria cri);
}
