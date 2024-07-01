package com.yedam.app.adm.mapper;

import java.util.List;
import java.util.Map;

import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.usr.service.UserVO;

public interface AdminMapper {
	// 일별 가입자 통계
	public List<Map<String, Object>> selectJoinStatistics();
	// 의뢰 작업 분야별 통계
	public List<Map<String, Object>> selectReqCategoryStatistics();
	// 의뢰 지역별 통계
	public List<Map<String, Object>> selectReqRegionStatistics();
	
	
	// 회원 정보 가져오기
	public List<UserVO> selectUsers();
	// 회원 단건 조회
	public UserVO selectUser(int uno);
	// 회원 정지
	public int updateUsersStatePause(int uno);
	// 회원 정지 해제
	public int updateUsersStateActive(int uno);
	
	
	// 통일 계약서 전체 가져오기
	public List<UnityContractVO> selectUnityContracts();
	// 통일 계약서 단건 조회
	public UnityContractVO selectUnityContract(int no);
	// 통일 계약서 등록 하기
	public int insertUnityContract(UnityContractVO vo);
}
