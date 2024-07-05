package com.yedam.app.wkr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.tem.service.MemberVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.mapper.WorkerMapper;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.PortfolioVO;
import com.yedam.app.wkr.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService{
	@Autowired
	WorkerMapper workerMapper;
	
	//작업자 정보 조회
	@Override
	public List<Map<String, Object>> selectCategoryInfo(int id) {
		return workerMapper.selectCategoryInfo(id);
	}

	@Override
	public List<Map<String, Object>> selectRegionInfo(int id) {
		return workerMapper.selectRegionInfo(id);
	}

	//작업자 정보 수정
	@Transactional
	@Override
	public Map<String, Object> updateWorker(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = workerMapper.updateWorkerInfo(userVO);
		if(result == 1) {
			isSuccessed = true;
		}
		
		workerMapper.deleteWorkerCategory(userVO);
		workerMapper.deleteWorkerRegion(userVO);
		workerMapper.insertWorkerCode(userVO);
		
		map.put("result", isSuccessed);
		map.put("target", userVO);
		
		return map;
	}
	
	//작업자 비밀번호 변경
	@Override
	public boolean updateWorkerPw(UserVO userVO) {
		return workerMapper.updateWorkerPw(userVO) == 1;
	}

	
	//작업자 경력증명서 목록
	@Override
	public List<CareerVO> selectCareerList(UserVO userVO) {
		return workerMapper.selectCareerList(userVO);
	}
	//작업자 경력증명서 등록
	@Override
	public int insertCareer(CareerVO careerVO) {
		return workerMapper.insertCareer(careerVO);
	}

	
	//작업자 후기목록
	@Override
	public List<ReviewVO> selectWorkerReviewList(UserVO userVO) {
		return workerMapper.selectWorkerReviewList(userVO);
	}
	//작업자 팀후기목록
	@Override
	public List<ReviewVO> selectWorkerTeamReviewList(UserVO userVO) {
		return workerMapper.selectWorkerTeamReviewList(userVO);
	}
	
	
	//작업자 의뢰 목록
	@Override
	public List<ReviewVO> selectWorkerRequestList(UserVO userVO) {
		return workerMapper.selectWorkerRequestList(userVO);
	}
	
	
	//작업자 견적서 목록
	@Override
	public List<ProposalVO> selectWorkerProposalList(UserVO userVO) {
		return workerMapper.selectWorkerProposalList(userVO);
	}
	//작업자 계약서 목록
	@Override
	public List<ContractVO> selectWorkerContractList(UserVO userVO) {
		return workerMapper.selectWorkerContractList(userVO);
	}

	//작업자 팀후기 목록
	@Override
	public List<ReviewVO> selectWorkerTeamRequestList(UserVO userVO) {
		return null;
	}
	
	//작업자 포트폴리오 목록
	@Override
	public List<PortfolioVO> selectWorkerPortfolioList(UserVO userVO) {
		return workerMapper.selectWorkerPortfolioList(userVO);
	}
	//작업자 포트폴리오 등록
	@Override
	public int insertWorkerPortfolio(PortfolioVO portfolioVO) {
		return workerMapper.insertWorkerPortfolio(portfolioVO);
	}
	
	
	

	
	//작업자 탈퇴(상태 수정)
	@Override
	public boolean workerStateUpdate(UserVO userVO) {
		return workerMapper.updateWorkerState(userVO) == 1;
	}
}
