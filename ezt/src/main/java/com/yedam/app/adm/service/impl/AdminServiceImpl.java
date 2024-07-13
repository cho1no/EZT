package com.yedam.app.adm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.adm.mapper.AdminMapper;
import com.yedam.app.adm.service.AdminService;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminMapper admMapper;
	
	// 통계관련
	@Override
	public Map<String, Object> getStatistic(){
		Map<String, Object> map = new HashMap<>();
		map.put("newJoin", admMapper.selectJoinStatistics()); // 일별 가입자 현황
		map.put("reqCategory", admMapper.selectReqCategoryStatistics()); // 의뢰 분야별 통계
		map.put("reqRegion", admMapper.selectReqRegionStatistics()); // 의뢰 지역별 통계
		map.put("crrWaitCnt", admMapper.selectCrrAcptWaitCnt());
		map.put("totalUsersCnt", admMapper.selectUsersCnt());
		return map;
	}
	

	// 의뢰 관련
	@Override
	public List<RequestVO> getRequests() {
		return admMapper.selectRequests();
	}

	@Override
	public RequestVO getRequest() {
		return null;
	}

	@Override
	public int deleteRequest() {
		return 0;
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
	@Override
	@Transactional
	public UnityContractVO postUnityContract(UnityContractVO vo) {
		admMapper.insertUnityContract(vo);	
		return admMapper.selectUnityContract(vo.getUnityContractNo());
	}
	@Override
	@Transactional
	public int putUnityContractBasicTf(int no) {
		admMapper.updateUnityContractBasicTfN();
		return admMapper.updateUnityContractBasicTfY(no);
	}
	
	// 경력 인증 관련
	@Override
	public List<CareerVO> getCareers() {
		return admMapper.selectCareers();
	}
	
	@Override
	public CareerVO getCareer(int no) {
		return admMapper.selectCareer(no);
	}

	@Override
	@Transactional
	public int setCareerAccept(int no) {
		admMapper.deleteCareerDeny(no);
		return admMapper.updateCareerAccept(no);
	}

	@Override
	public int setCareerDeny(int no) {
		return admMapper.updateCareerDeny(no);
	}

	@Override
	@Transactional
	public int postCareerDeny(Map<String, String> map) {
		admMapper.deleteCareerDeny(Integer.parseInt(map.get("careerNo")));
		admMapper.updateCareerDeny(Integer.parseInt(map.get("careerNo")));
		return admMapper.insertCareerDeny(map);
	}

	@Override
	public List<Map<String, Object>> getPayManages() {
		return admMapper.selectPayManages();
	}


	@Override
	public Map<String, Object> getPayManage(int payNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("info", admMapper.selectPayManage(payNo));
		map.put("details", admMapper.selectContractDetails(payNo));
		return map;
	}

	
}
