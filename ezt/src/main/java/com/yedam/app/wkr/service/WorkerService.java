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
	//지역,카테고리 삭제 
	public int deleteWorkerRegion(UserVO userVO);
	public int deleteWorkerCategory(UserVO userVO);
	//지역,카테고리 추가
	public int insertWorkerCode(UserVO userVO);
	
	//비밀번호 변경
	public boolean updateWorkerPw(UserVO userVO);

	//경력증명서 목록조회
	public CareerVO selectCareerList(UserVO userVO);
	
	//경력증명서 등록
	public int insertCareer(CareerVO careerVO);
	
	//회원탈퇴 (상태수정)
	public boolean workerStateUpdate(UserVO userVO);
}
