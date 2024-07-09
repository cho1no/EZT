package com.yedam.app.doc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.mapper.ContractMapper;
import com.yedam.app.doc.mapper.FileMapper;
import com.yedam.app.doc.mapper.ProposalMapper;
import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.PartnershipContractVO;
import com.yedam.app.doc.service.SignsVO;
import com.yedam.app.doc.service.UnityContractVO;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractMapper conMapper;

	@Autowired
	ProposalMapper ppsMapper;

	@Autowired
	FileMapper fileMapper;

	// 은행 코드 조회
	@Override
	public List<CommonCodeVO> bankcodeSelect() {
		List<CommonCodeVO> codeVO = conMapper.selectBankcode();
		return codeVO;
	}
	// 통일 계약서 조회
	@Override
	public UnityContractVO unityConSelect() {
		UnityContractVO unityVO = conMapper.selectUnityCon();
		return unityVO;
	}
	// 계약서 등록된 통일 계약서 조회
	@Override
	public UnityContractVO IncludeUnityCon(int contractNo) {
		UnityContractVO unityVO = conMapper.selectIncludetUnityCon(contractNo);
		return unityVO;
	}
	
	// 계약서 등록
	@Override
	public int conInsert(ContractVO contractVO) {
		// 계약서 등록
		int result = conMapper.insertConInfo(contractVO);
		if (contractVO.getDList() != null) {
			contractVO.getDList().forEach(e -> {
				e.setContractNo(contractVO.getContractNo());
				conMapper.insertConDetailInfo(e);
			});
		}

		// 파일 등록
		if (contractVO.getFileList() != null) {
			fileMapper.insertFileAttrConInfo(contractVO);
			contractVO.getFileList().forEach(e -> {
				e.setFileId(contractVO.getFileId());
				fileMapper.insertFileInfo(e);
			});
		}

		// 서명 등록
		if (contractVO.getWorSign() != null) {
			if (contractVO.getWorSign().getSignsContent().length() > 10) {
				SignsVO sign = contractVO.getWorSign();
				sign.setContractNo(contractVO.getContractNo());
				sign.setUsersNo(contractVO.getWorkerInfo());
				conMapper.insertSignInfo(sign);
			}
		}
		if (contractVO.getReqSign() != null) {
			if (contractVO.getReqSign().getSignsContent().length() > 10) {
				SignsVO sign = contractVO.getWorSign();
				sign.setContractNo(contractVO.getContractNo());
				sign.setUsersNo(contractVO.getRequesterInfo());
				conMapper.insertSignInfo(sign);
			}
		}

		return result == 1 ? contractVO.getContractNo() : -1;
	}

	// 계약서 조회
	@Override
	public ContractVO conInfo(ContractVO contracVO) {
		// 단건 조회
		ContractVO con = conMapper.selectConInfo(contracVO.getContractNo());

		// 상세 조회
		List<ContractDetailVO> list = conMapper.selectConDetailInfo(con.getContractNo());
		con.setDList(list);

		// 파일 조회
		List<FileVO> fileList = fileMapper.selectConFileList(con.getContractNo());
		con.setFileList(fileList);

		// 서명 조회
		SignsVO worSign = conMapper.selectSignInfo(con.getContractNo(), con.getWorkerInfo());
		if (worSign == null) {
			SignsVO falseworSign = new SignsVO();
			falseworSign.setContractNo(-1);
			con.setWorSign(falseworSign);

		} else {
			con.setWorSign(worSign);
		}

		SignsVO reqSign = conMapper.selectSignInfo(con.getContractNo(), con.getRequesterInfo());
		if (reqSign == null) {
			SignsVO falseReqSign = new SignsVO();
			falseReqSign.setContractNo(-1);
			con.setReqSign(falseReqSign);

		} else {
			con.setReqSign(reqSign);
		}

		return con;
	}

	// 계약서 수정
	@Override
	public int conUpdate(ContractVO contractVO) {

		// 계약서 수정
		int result = conMapper.updateConInfo(contractVO);
		// 계약서 상세 삭제 후 등록
		if (contractVO.getDList() != null) {
			conMapper.deleteConDetailInfo(contractVO.getContractNo());
			contractVO.getDList().forEach(e -> {
				e.setContractNo(contractVO.getContractNo());
				conMapper.insertConDetailInfo(e);
			});
		}

		// 파일 상세 삭제 후 등록 or 등록된 파일 없으면 새로 등록
		if (contractVO.getFileList() != null) {

			if (contractVO.getFileId() != 0) {
				fileMapper.deleteFileInfo(contractVO.getFileId());
			} else {
				fileMapper.insertFileAttrConInfo(contractVO);
			}
			contractVO.getFileList().forEach(e -> {
				e.setFileId(contractVO.getFileId());
				fileMapper.insertFileInfo(e);
			});
		}

		// 서명 등록
		if (contractVO.getWorSign() != null) {
			if (contractVO.getWorSign().getSignsContent().length() > 10) {
				conMapper.delelteSignInfo(contractVO.getContractNo(), contractVO.getWorkerInfo());
				SignsVO sign = contractVO.getWorSign();
				sign.setContractNo(contractVO.getContractNo());
				sign.setUsersNo(contractVO.getWorkerInfo());
				conMapper.insertSignInfo(sign);
			}
		}
		if (contractVO.getReqSign() != null) {
			if (contractVO.getReqSign().getSignsContent().length() > 10) {
				conMapper.delelteSignInfo(contractVO.getContractNo(), contractVO.getRequesterInfo());
				SignsVO sign = contractVO.getReqSign();
				sign.setContractNo(contractVO.getContractNo());
				sign.setUsersNo(contractVO.getRequesterInfo());
				conMapper.insertSignInfo(sign);
			}
		}
		
		return result == 1 ? contractVO.getContractNo() : -1;
	}
	
	// 파일 조회
	@Override
	public List<FileVO> fileSelect(ContractVO contractVO) {
		return fileMapper.selectConFileList(contractVO.getContractNo());
	}
	
	// 계약서 전송
	@Override
	public int conSend(ContractVO contractVO) {
		int result = conMapper.sendConInfo(contractVO);
		return result == 1 ? contractVO.getContractNo() : -1;
	}
	
	// 동업 계약서
	// 분야 코드 조회
	@Override
	public CommonCodeVO workCodeSelect(int teamNo, int usersNo) {
		return conMapper.selectTeamWorkCode(teamNo, usersNo);
	}
	// 동업 계약서 등록
	@Override
	public int ptnConInsert(PartnershipContractVO partnershipContractVO) {
		int result = conMapper.InsertPartnerCon(partnershipContractVO);
		return result == 1 ? partnershipContractVO.getContractNo() : -1;
	}
	// 동업 계약서 조회
	@Override
	public PartnershipContractVO ptnConSelect(int contractNo) {
		return conMapper.selectPtnSelect(contractNo);
	}
}
