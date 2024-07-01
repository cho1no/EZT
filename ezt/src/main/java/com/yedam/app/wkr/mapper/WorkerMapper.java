package com.yedam.app.wkr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.usr.service.UserVO;

@Mapper
public interface WorkerMapper {
	//작업자 정보조회(분야,지역)
	public List<Map<String, Object>> selectCategoryInfo(int id);
	
	public List<Map<String, Object>> selectRegionInfo(int id);
	
	//작업자 정보수정
	public int updateWorkerInfo(UserVO userVO);
	
	//비밀번호 변경
	public int updateWorkerPw(UserVO userVO);
	
	
	//작업자 탈퇴(상태수정)
	public int updateWorkerState(UserVO userVO);
}
