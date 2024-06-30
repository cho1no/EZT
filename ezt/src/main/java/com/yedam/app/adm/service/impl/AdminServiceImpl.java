package com.yedam.app.adm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.adm.mapper.AdminMapper;
import com.yedam.app.adm.service.AdminService;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.usr.service.UserVO;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminMapper admMapper;
	
	// 통계관련
	@Override
	public List<Map<String, Object>> getJoinStatistics() {
		return admMapper.selectJoinStatistics();
	}
	
	@Override
	public List<Map<String, Object>> getReqCategoryStatistics() {
		return admMapper.selectReqCategoryStatistics();
	}
	
	@Override
	public List<Map<String, Object>> getReqRegionStatistics() {
		return admMapper.selectReqRegionStatistics();
	}

	
	// 유저관련
	@Override
	public List<UserVO> getUsers() {
		return admMapper.selectUsers();
	}

	@Override
	public UserVO getUser(int uno) {
		return admMapper.selectUser(uno);
	}

	@Override
	public int setUserPause(int uno) {
		return admMapper.updateUsersStatePause(uno);
	}

	@Override
	public int setUserActive(int uno) {
		return admMapper.updateUsersStateActive(uno);
	}

	
	// 통일 계약서 관련
	@Override
	public List<UnityContractVO> getUnityContracts() {
		return admMapper.selectUnityContracts();
	}

	@Override
	public UnityContractVO getUnityContract(int no) {
		return admMapper.selectUnityContract(no);
	}

}
