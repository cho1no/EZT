package com.yedam.app.usr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yedam.app.common.service.SimpleFileService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.pay.service.PayVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.FindWorkerCriteria;
import com.yedam.app.usr.service.UserReqCriteria;
import com.yedam.app.usr.service.UserRvwCriteria;
import com.yedam.app.usr.service.UserService;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.WorkerRvwCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	SimpleFileService simpleFileService;
	
	@Autowired
	PasswordEncoder passwordEncoder; 

	@Override
	public UserVO userInfo(String id) {
		return userMapper.selectUserInfo(id);
	}

	//회원정보 수정
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
	
	@Override
	public int insertUserImg(UserVO userVO) {
		return userMapper.insertUserImg(userVO);
	}

	//비밀번호 조회
	@Override
	public String selectEncPw(int usersNo) {
		return userMapper.selectEncPw(usersNo);
	}
	
	// 비밀번호 변경
    @Override
    public int updatePw(int usersNo, String currentPw, String newPw) {
        // DB의 현재회원 비밀번호 조회
        String encPw = userMapper.selectEncPw(usersNo);

        // 현재 비밀번호 검증
        if (passwordEncoder.matches(currentPw, encPw)) {
            // 새로운 비밀번호 암호화
            String encodedNewPw = passwordEncoder.encode(newPw);
            return userMapper.updatePw(usersNo, encodedNewPw);
        }

        // 비밀번호가 일치하지 않는 경우
        return 0;
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

	//사용자 계약서목록
	@Override
	public List<ContractVO> selectUserContractList(WorkerRvwCriteria cri) {
		return userMapper.selectUserContractList(cri);
	}
	//사용자 결제명세서목록
	@Override
	public List<PayVO> selectUserPayList(WorkerRvwCriteria cri) {
		return userMapper.selectUserPayList(cri);
	}
	
	//회원탈퇴(상태 수정)
	@Override
	public int userStateUpdate(UserVO userVO) {
		return userMapper.updateUserState(userVO);
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
	
	//계약서목록 페이징()
	@Override
	public int getTotalUserCtt(WorkerRvwCriteria cri) {
		return userMapper.getTotalUserCtt(cri);
	}
	//결제명세서목록 페이징()
	@Override
	public int getTotalUserPay(WorkerRvwCriteria cri) {
		return userMapper.getTotalUserPay(cri);
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
