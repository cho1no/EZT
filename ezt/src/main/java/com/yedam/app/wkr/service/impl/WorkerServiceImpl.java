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
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.mapper.WorkerMapper;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.LicenseVO;
import com.yedam.app.wkr.service.PortfolioVO;
import com.yedam.app.wkr.service.WorkerLcsCriteria;
import com.yedam.app.wkr.service.WorkerReqCriteria;
import com.yedam.app.wkr.service.WorkerRvwCriteria;
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
	public List<ReviewVO> selectWorkerReviewList(WorkerRvwCriteria cri) {
		return workerMapper.selectWorkerReviewList(cri);
	}
	//작업자 팀후기목록
	@Override
	public List<ReviewVO> selectWorkerTeamReviewList(WorkerRvwCriteria cri) {
		return workerMapper.selectWorkerTeamReviewList(cri);
	}
	
	
	//작업자 의뢰 목록
	@Override
	public List<ReviewVO> selectWorkerRequestList(WorkerReqCriteria cri) {
		return workerMapper.selectWorkerRequestList(cri);
	}
	//작업자 팀의뢰 목록
	@Override
	public List<ReviewVO> selectWorkerTeamRequestList(UserVO userVO) {
		return workerMapper.selectWorkerTeamRequestList(userVO);
	}
	//작업자 견적서 목록
	@Override
	public List<ProposalVO> selectWorkerProposalList(WorkerRvwCriteria cri) {
		return workerMapper.selectWorkerProposalList(cri);
	}
	//작업자 계약서 목록
	@Override
	public List<ContractVO> selectWorkerContractList(WorkerRvwCriteria cri) {
		return workerMapper.selectWorkerContractList(cri);
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
	
	//작업자 자격증 목록
	@Override
	public List<LicenseVO> selectWorkerLicenseList(WorkerLcsCriteria cri) {
		return workerMapper.selectWorkerLicenseList(cri);
	}
	//작업자 자격증 등록
	@Override
	public int insertWorkerLicense(LicenseVO licenseVO) {
		return workerMapper.insertWorkerLicense(licenseVO);
	}
	
	//후기목록 페이징
	@Override
	public int workerReviewGetTotal(WorkerRvwCriteria cri) {
		return workerMapper.getWorkerTotalReviewCount(cri);
	}
	//팀후기 목록 페이징
	@Override
	public int workerTeamReviewGetTotal(WorkerRvwCriteria cri) {
		return workerMapper.getWorkerTotalTeamReviewCount(cri);
	}
	
	//의뢰목록 페이징
	@Override
	public int workerRequestGetTotal(WorkerReqCriteria cri) {
		return workerMapper.getWorkerTotalRequestCount(cri);
	}
	
	//견적서목록 페이징
	@Override
	public int workerProposalGetTotal(WorkerRvwCriteria cri) {
		return workerMapper.getWorkerTotalProposalCount(cri);
	}

	//계약서목록 페이징
	@Override
	public int workerContractGetTotal(WorkerRvwCriteria cri) {
		return workerMapper.getWorkerTotalContractCount(cri);
	}
	

	//자격증목록 페이징
	@Override
	public int workerLicenseGetTotal(WorkerLcsCriteria cri) {
		return workerMapper.getWorkerTotalLicenseCount(cri);
	}

	

	//작업자 탈퇴(상태 수정)
	@Override
	public boolean workerStateUpdate(UserVO userVO) {
		return workerMapper.updateWorkerState(userVO) == 1;
	}

	
	

	
}
