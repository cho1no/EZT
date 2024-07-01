package com.yedam.app.usr.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;

public interface UserService {
	//사용자 정보조회
	public UserVO userInfo(String id);
	
	//수정
	public Map<String, Object> updateUser(UserVO userVO);
	
	//비밀번호 변경
	public boolean updateUserPw(UserVO userVO);

	//후기목록 조회
	public List<ReviewVO> userReviewList(int writer);
	
	//의뢰목록 조회
	public List<RequestVO> userReqList(int usersNo);

	//회원탈퇴 (상태 수정)
	public boolean userStateUpdate(UserVO userVO);
}
