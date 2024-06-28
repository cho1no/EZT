package com.yedam.app.usr.service;

import java.util.Map;

public interface UserService {
	//사용자 정보조회
	public UserVO userInfo(String id);
	
	//수정
	public Map<String, Object> updateUser(UserVO userVO);
	
	//삭제
	public Map<String, Object> deleteUser(UserVO userVO);
}
