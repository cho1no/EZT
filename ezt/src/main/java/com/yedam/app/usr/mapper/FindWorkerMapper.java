package com.yedam.app.usr.mapper;

import java.util.List;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.LicenseVO;
import com.yedam.app.wkr.service.PortfolioVO;

public interface FindWorkerMapper {
	// 경력사항 전체조회
	public List<CareerVO> selectCareers(int no);
	// 자격증 전체조회
	public List<LicenseVO> selectLicenes(int no);
	
	// 후기 평점 조회
	public float avgReview(int no);
	// 후기 갯수 조회
	public int countReview(int no);
	// 후기
	// 작업자 포폴 이미지 가져오기
	public List<PortfolioVO> selectPortFiles(int usersNo);
	// 작업자 리뷰 가져오기
	public List<ReviewVO> selectReviews(int no);
	// 포폴 상세
	public PortfolioVO selectPortfolioInfo(int pno);
	// 포폴 파일 리스트
	public List<FileVO> selectPortInfoFiles(int fid);
}
