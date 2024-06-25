package com.yedam.app.usr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.usr.service.UserVO;

@Mapper
public interface UserMapper {
	
	public UserVO selectUserInfo(String id);
		
	// 회원가입
	void saveUser(UserVO userVO);
	
	//중복체크
	public int idChk(String id);
	
	public int nickChk(String nick);
	
	public int emailChk(String email);
	
}
