package com.yedam.app.wkr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.tem.service.MemberVO;
import com.yedam.app.usr.service.UserVO;

@Service
public interface WorkerService {
	//작업자 정보조회
	public List<Map<String, Object>> selectCategoryInfo(int id);
	
	public List<Map<String, Object>> selectRegionInfo(int id);
	
	//작업자 정보수정
	public Map<String, Object> updateWorker(UserVO userVO);

	
	//비밀번호 변경
	public boolean updateWorkerPw(UserVO userVO);

	//경력증명서 목록조회
	public List<CareerVO> selectCareerList(UserVO userVO);
	
	//경력증명서 등록
	public int insertCareer(CareerVO careerVO);
	
	//작업자 후기 목록
	public List<ReviewVO> selectWorkerReviewList(UserVO userVO);
	
	//작업자 팀 후기 목록
	public List<ReviewVO> selectWorkerTeamReviewList(UserVO userVO);
	
	//작업자 의뢰 목록
	public List<ReviewVO> selectWorkerRequestList(UserVO userVO);
	//작업자 팀 의뢰목록
	public List<ReviewVO> selectWorkerTeamRequestList(UserVO userVO);
	
	//작업자 견적서 목록
	public List<ProposalVO> selectWorkerProposalList(UserVO userVO);
	//작업자 계약서 목록
	public List<ContractVO> selectWorkerContractList(UserVO userVO);
	
	//작업자 포트폴리오 목록
	public List<PortfolioVO> selectWorkerPortfolioList(UserVO userVO);
	
	//작업자 포트폴리오 등록
	public int insertWorkerPortfolio(PortfolioVO portfolioVO);
	
	
	
	//회원탈퇴 (상태수정)
	public boolean workerStateUpdate(UserVO userVO);
}
