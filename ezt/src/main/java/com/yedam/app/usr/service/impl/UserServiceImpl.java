package com.yedam.app.usr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.RequestVO;
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

	//비밀번호 변경
	@Override
	public boolean updateUserPw(UserVO userVO) {
		return userMapper.updateUserPw(userVO) == 1;
	}

	//후기목록
	@Override
	public List<RequestVO> userReviewList(int writer) {
		return userMapper.selectUserReviewList(writer);
	}
	
	//의뢰목록
	@Override
	public List<RequestVO> userReqList(int usersNo) {
		return userMapper.selectUserReqList(usersNo);
	}



	

	

	
	
}
