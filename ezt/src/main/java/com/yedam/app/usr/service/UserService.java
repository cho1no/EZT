package com.yedam.app.usr.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.req.service.RequestVO;

public interface UserService {
	//사용자 정보조회
	public UserVO userInfo(String id);
	
	//수정
	public Map<String, Object> updateUser(UserVO userVO);
	
	//삭제
	public Map<String, Object> deleteUser(UserVO userVO);
	
	//비밀번호 변경
	public boolean updateUserPw(UserVO userVO);

	//후기목록 조회
	public List<RequestVO> userReviewList(int writer);
	
	//의뢰목록 조회
	public List<RequestVO> userReqList(int usersNo);

}
