package com.yedam.app.adm.mapper;

import java.util.List;
import java.util.Map;

import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;

public interface AdminMapper {
	// 일별 가입자 통계
	public List<Map<String, Object>> selectJoinStatistics();
	// 의뢰 작업 분야별 통계
	public List<Map<String, Object>> selectReqCategoryStatistics();
	// 의뢰 지역별 통계
	public List<Map<String, Object>> selectReqRegionStatistics();
	// 경력인증 승인대기 개수
	public int selectCrrAcptWaitCnt();
	// 전체 유저 수 ( 일반유저, 작업자 )
	public int selectUsersCnt();
	// 전체 의뢰 수
	public int selectReqCnt();
	
	
	// 회원 정보 가져오기
	public List<UserVO> selectUsers();
	// 회원 단건 조회
	public UserVO selectUser(int uno);
	// 회원 정지
	public int updateUsersStatePause(int uno);
	// 회원 정지 해제
	public int updateUsersStateActive(int uno);
	
	
	// 의뢰 전체 가져오기
	public List<RequestVO> selectRequests();
	// 의뢰 단건 조회
//	public RequestVO selectRequest(int requestNo);
	// 의뢰 삭제
	public int deleteRequest();
	
	// 통일 계약서 전체 가져오기
	public List<UnityContractVO> selectUnityContracts();
	// 통일 계약서 단건 조회
	public UnityContractVO selectUnityContract(int no);
	// 통일 계약서 등록 하기
	public int insertUnityContract(UnityContractVO vo);
	// 통일 계약서 기본계약서 여부 처리
	public int updateUnityContractBasicTfN(); // 전체 N
	public int updateUnityContractBasicTfY(int no); // 전체 Y
	
	
	// 경력 인증 신청 전체 가져오기
	public List<CareerVO> selectCareers();
	// 경력 인증 단건 조회
	public CareerVO selectCareer(int no);
	// 경력 인증 승인
	public int updateCareerAccept(int no);
	// 경력 인증 반려
	public int updateCareerDeny(int no);
	// 경력 인증 반려 사유 등록
	public int insertCareerDeny(Map<String, String> map);
	// 경력 인증 반려 사유 삭제
	public int deleteCareerDeny(int no);
	
	
	// 결제 대금 관리 전체 조회
	public List<Map<String, Object>> selectPayManages();
	// 결제 대금 관리 상세 조회
	public Map<String, Object> selectPayManage(int payNo);
	// 계약서 상세 조회
	public List<ContractDetailVO> selectContractDetails(int payNo);
}
