package com.yedam.app.adm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.adm.mapper.AdminMapper;
import com.yedam.app.adm.service.AdminService;
import com.yedam.app.usr.service.UserVO;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminMapper admMapper;

	@Override
	public List<UserVO> getUsers() {
		return admMapper.selectUsers();
	}

	@Override
	public UserVO getUser(int uno) {
		return admMapper.selectUser(uno);
	}
}
