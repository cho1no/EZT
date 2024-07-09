package com.yedam.app.usr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.FindWorkerCriteria;
import com.yedam.app.usr.service.UserReqCriteria;
import com.yedam.app.usr.service.UserRvwCriteria;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

	//비밀번호 변경
	@Override
	public boolean updateUserPw(UserVO userVO) {
		return userMapper.updateUserPw(userVO) == 1;
	}

	//후기목록
	@Override
	public List<ReviewVO> userReviewList(UserRvwCriteria cri) {
		return userMapper.selectUserRvwList(cri);
	}
	
	//의뢰목록
	@Override
	public List<RequestVO> userReqList(UserReqCriteria cri) {
		return userMapper.selectUserReqList(cri);
	}

	//회원탈퇴(상태 수정)
	@Override
	public boolean userStateUpdate(UserVO userVO) {
		return userMapper.updateUserState(userVO) == 1;
	}
	
	//후기 목록 페이징()
	@Override
	public int reviewGetTotal(UserRvwCriteria cri) {
		return userMapper.getTotalReviewCount(cri);
	}
	
	//의뢰 목록 페이징()
	@Override
	public int requestGetTotal(UserReqCriteria cri) {
		return userMapper.getTotalRequestCount(cri);
	}
	
	
	//작업자 찾기
	@Override
	public List<UserVO> selectFindWorkerList(FindWorkerCriteria cri) {
		return userMapper.selectFindWorkerList(cri);
	}
	//총 작업자 수
	@Override
	public int workerListGetTotal(FindWorkerCriteria cri) {
		return userMapper.getTotalWorkerCount(cri);
	}



	

	


	

	

	
	
}
