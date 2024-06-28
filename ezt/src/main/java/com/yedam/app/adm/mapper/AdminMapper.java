package com.yedam.app.adm.mapper;

import java.util.List;

import com.yedam.app.usr.service.UserVO;

public interface AdminMapper {
	// 회원 정보 가져오기
	public List<UserVO> selectUsers();
	
	public UserVO selectUser(int uno);
}
