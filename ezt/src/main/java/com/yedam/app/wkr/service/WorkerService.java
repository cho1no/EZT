package com.yedam.app.wkr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.usr.service.UserRvwCriteria;
import com.yedam.app.usr.service.UserVO;

@Service
public interface WorkerService {
	//작업자 정보조회
	public List<Map<String, String>> selectCategoryInfo(int id);
	
	public List<Map<String, String>> selectRegionInfo(int id);
	
	//작업자 정보수정
	public Map<String, Object> updateWorker(UserVO userVO);

	
	//비밀번호 변경
	public boolean updateWorkerPw(UserVO userVO);

	//경력증명서 목록조회
	public List<CareerVO> selectCareerList(WorkerPFCriteria cri);
	
	//경력증명서 등록
	public int insertCareer(CareerVO careerVO);
	
	//작업자 후기 목록
	public List<ReviewVO> selectWorkerReviewList(WorkerRvwCriteria cri);
	
	//작업자 팀 후기 목록
	public List<ReviewVO> selectWorkerTeamReviewList(WorkerRvwCriteria cri);
	
	//작업자 의뢰 목록
	public List<ReviewVO> selectWorkerRequestList(WorkerReqCriteria cri);
	//작업자 팀 의뢰목록
	public List<ReviewVO> selectWorkerTeamRequestList(UserVO userVO);
	
	//작업자 견적서 목록
	public List<ProposalVO> selectWorkerProposalList(WorkerRvwCriteria cri);
	//작업자 계약서 목록
	public List<ContractVO> selectWorkerContractList(WorkerRvwCriteria cri);
	
	//작업자 포트폴리오 목록
	public List<PortfolioVO> selectWorkerPortfolioList(WorkerPFCriteria cri);
	
	//작업자 포트폴리오 등록
	public int insertWorkerPortfolio(PortfolioVO portfolioVO);
	
	//작업자 자격증 목록
	public List<LicenseVO> selectWorkerLicenseList(WorkerLcsCriteria cri);
	
	//작업자 자격증 등록
	public int insertWorkerLicense(LicenseVO licenseVO);
	
	//회원탈퇴 (상태수정)
	public boolean workerStateUpdate(UserVO userVO);
	
	
	//전체 경력서 갯수
	public int workerCareerGetTotal(WorkerPFCriteria cri);
	
	//전체 리뷰 데이터 갯수
	public int workerReviewGetTotal(WorkerRvwCriteria cri);
	//전체 팀리뷰 데이터 갯수
	public int workerTeamReviewGetTotal(WorkerRvwCriteria cri);
	
	//전체 의뢰 데이터 갯수
	public int workerRequestGetTotal(WorkerReqCriteria cri);
	//전체 팀의뢰 데이터 갯수
	
	//전체 견적서 갯수
	public int workerProposalGetTotal(WorkerRvwCriteria cri);
	//전체 계약서 갯수
	public int workerContractGetTotal(WorkerRvwCriteria cri);
	
	//전체 포폴갯수
	public int workerPortfolioGetTotal(WorkerPFCriteria cri);
	
	//전체 자격증 갯수
	public int workerLicenseGetTotal(WorkerLcsCriteria cri);

}
