package com.yedam.app.usr.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;

	@Override
	public UserVO userInfo(String id) {
		return userMapper.selectUserInfo(id);
	}

	@Override
	public Map<String, Object> updateUser(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = userMapper.updateUserInfo(userVO);
		if(result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", userVO);
		
		return map;
	}

	@Override
	public Map<String, Object> deleteUser(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		
		int result = userMapper.deleteUserInfo(userVO.getUsersNo());
			
		if(result == 1) {
			map.put("usersNo", userVO.getUsersNo());
		}
		return map;
	}

	
	
}
