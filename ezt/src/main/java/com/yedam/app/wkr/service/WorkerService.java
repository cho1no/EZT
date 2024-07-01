package com.yedam.app.wkr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.app.usr.service.UserVO;

@Service
public interface WorkerService {
	//작업자 정보조회
	public List<Map<String, Object>> selectCategoryInfo(int id);
	
	public List<Map<String, Object>> selectRegionInfo(int id);
	
	//작업자 정보수정
	public Map<String, Object> updateWorker(UserVO userVO);
	
	//비밀번호 변경
	public boolean updateWorkerPw(UserVO userVO);
	
	
	//회원탈퇴 (상태수정)
	public boolean workerStateUpdate(UserVO userVO);
}
