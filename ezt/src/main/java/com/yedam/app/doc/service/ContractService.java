package com.yedam.app.doc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.common.service.FileVO;

public interface ContractService {
	
	// 은행 코드 조회
	public List<CommonCodeVO> bankcodeSelect();
	// 통일 계약서 조회
	public UnityContractVO unityConSelect();
	// 계약서 등록된 통일 계약서 조회
	public UnityContractVO IncludeUnityCon(int contractNo);

	// 계약서 등록&상세
	public int conInsert(ContractVO contracVO);
	
	// 계약서 단건조회
	public ContractVO conInfo(ContractVO contracVO);
	
	// 계약서 수정
	public int conUpdate(ContractVO contractVO);
	
	// 파일 조회
	public List<FileVO> fileSelect(ContractVO contractVO);
	
	// 계약서 전송
	public int conSend(ContractVO contractVO);
	
	// 동업 계약서
	// 분야 코드 조회
	public CommonCodeVO workCodeSelect(int teamNo, int usersNo);
	public CommonCodeVO leaderCodeSelect(int requestNo);
	
	// 동업 계약서 등록
	public int ptnConInsert(PartnershipContractVO partnershipContractVO);
	// 동업 계약서 조회
	public PartnershipContractVO ptnConSelect(int contractNo);
	
}
