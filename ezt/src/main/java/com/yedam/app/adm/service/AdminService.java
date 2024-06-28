package com.yedam.app.adm.service;

import java.util.List;

import com.yedam.app.usr.service.UserVO;

public interface AdminService {
	public List<UserVO> getUsers();
	public UserVO getUser(int uno);
}
