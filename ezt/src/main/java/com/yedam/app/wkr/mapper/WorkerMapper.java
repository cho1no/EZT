package com.yedam.app.wkr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.LicenseVO;
import com.yedam.app.wkr.service.PortfolioVO;
import com.yedam.app.wkr.service.WorkerLcsCriteria;
import com.yedam.app.wkr.service.WorkerPFCriteria;
import com.yedam.app.wkr.service.WorkerReqCriteria;
import com.yedam.app.wkr.service.WorkerRvwCriteria;

@Mapper
public interface WorkerMapper {
	//작업자 정보조회(분야,지역)
	public List<Map<String, String>> selectCategoryInfo(int id);
	
	public List<Map<String, String>> selectRegionInfo(int id);
	
	//작업자 정보수정
	//닉네임,전화번호,이메일 수정
	public int updateWorkerInfo(UserVO userVO);
	//지역,카테고리 삭제 
	public int deleteWorkerRegion(UserVO userVO);
	public int deleteWorkerCategory(UserVO userVO);
	//지역,카테고리 추가
	public int insertWorkerCode(UserVO userVO);
	
	//비밀번호 변경
	public int updateWorkerPw(UserVO userVO);
	
	//경력증명서 목록 조회
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
	
	//작업자 탈퇴(상태수정)
	public int updateWorkerState(UserVO userVO);
	
	//작업자 경력서 갯수
	public int getWorkerTotalCareerList(WorkerPFCriteria cri);
	
	//작업자 리뷰 갯수
	public int getWorkerTotalReviewCount(WorkerRvwCriteria cri);
	
	//작업자 팀리뷰 갯수
	public int getWorkerTotalTeamReviewCount(WorkerRvwCriteria cri);
	
	//작업자 의뢰 갯수
	public int getWorkerTotalRequestCount(WorkerReqCriteria cri);
	
	//작업자 팀의뢰 갯수
	
	//작업자 견적서 갯수
	public int getWorkerTotalProposalCount(WorkerRvwCriteria cri);
	//작업자 계약서 갯수
	public int getWorkerTotalContractCount(WorkerRvwCriteria cri);
	
	//작업자 포트폴리오 갯수
	public int getWorkerTotalPortfolioCount(WorkerPFCriteria cri);
	
	//작업자 자격증 갯수
	public int getWorkerTotalLicenseCount(WorkerLcsCriteria cri);
	
	
}
