package com.yedam.app.doc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.PartnershipContractVO;
import com.yedam.app.doc.service.SignsVO;
import com.yedam.app.doc.service.UnityContractVO;

public interface ContractMapper {
	
	// 은행 코드 조회
	public List<CommonCodeVO> selectBankcode();
	// 통일 계약서 조회
	public UnityContractVO selectUnityCon();
	// 계약서 등록된 통일 계약서 조회
	public UnityContractVO selectIncludetUnityCon(int contractNo);
	
	// -- 계약서 등록
	// 계약서 등록
	public int insertConInfo(ContractVO contractVO);
	// 계약서 상세 등록
	public int insertConDetailInfo(ContractDetailVO contractDetailVO);
	// 서명 등록
	public int insertSignInfo(SignsVO signsVO);
	
	
	// -- 계약서 상세
	// 계약서 조회
	public ContractVO selectConInfo(@Param("contractNo")int contractNo);
	// 계약서 상세 조회
	public List<ContractDetailVO> selectConDetailInfo(@Param("contractNo")int contractNo);
	// 서명 조회
	public SignsVO selectSignInfo(@Param("contractNo")int contractNo, @Param("usersNo")int usersNo);
	
	
	// -- 계약서 수정
	// 계약서 수정
	public int updateConInfo(ContractVO contractVO);
	// 계약서 상세 삭제
	public int deleteConDetailInfo(@Param("contractNo")int contractNo);
	// 서명 삭제
	public int delelteSignInfo(@Param("contractNo")int contractNo, @Param("usersNo")int usersNo);
	
	// -- 계약서 전송
	public int sendConInfo(ContractVO contractVO);
	
	// -- 동업 계약서
	// 분야 코드 조회
	public CommonCodeVO selectTeamWorkCode(@Param("teamNo")int teamNo, @Param("usersNo")int usersNo);
	// 동업 계약서 등록
	public int InsertPartnerCon(PartnershipContractVO partnershipContractVO);
	// 동업 계약서 조회
	public PartnershipContractVO selectPtnSelect(@Param("contractNo")int contractNo);
}
