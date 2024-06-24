package com.yedam.app.usr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.usr.service.UserVO;

@Mapper
public interface UserMapper {
	
	public UserVO selectUserInfo(String id);
	
	void saveUser(UserVO userVO);
}
