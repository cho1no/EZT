package com.yedam.app.doc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.doc.mapper.ContractMapper;
import com.yedam.app.doc.mapper.ProposalMapper;
import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractMapper conMapper;
	
	@Autowired
	ProposalMapper ppsMapper;
	
	// 계약서 등록
	@Override
	public int conInsert(ContractVO contractVO) {
		
		int result = conMapper.insertConInfo(contractVO);
		if(contractVO.getDList() != null) {
			contractVO.getDList().forEach(e -> {
				e.setContractNo(contractVO.getContractNo());
				conMapper.insertConDetailInfo(e);
			});
		}
		if(contractVO.getFileList() != null) {
			conMapper.insertFileAttrConInfo(contractVO);
			contractVO.getFileList().forEach(e -> {
				e.setFileId(contractVO.getFileId());
				ppsMapper.insertFileInfo(e);
			});
		}
		return result ==1 ? contractVO.getContractNo() : -1;
	}
	
	// 계약서 조회
	@Override
	public ContractVO conInfo(ContractVO contracVO) {
		ContractVO con = conMapper.selectConInfo(contracVO.getProposalNo());
		List<ContractDetailVO> list = conMapper.selectConDetailInfo(con.getContractNo());
		con.setDList(list);
		List<FileVO> fileList = conMapper.selectConFileList(con.getContractNo());
		con.setFileList(fileList);
		return con;
	}
	
	// 계약서 수정
	@Override
	public int conUpdate(ContractVO contractVO) {
		
		int result = conMapper.updateConInfo(contractVO);

		if(contractVO.getDList() != null) {
			conMapper.deleteConDetailInfo(contractVO.getContractNo());
			contractVO.getDList().forEach(e -> {
				e.setContractNo(contractVO.getContractNo());
				conMapper.insertConDetailInfo(e);
			});
		}
		if(contractVO.getFileList() != null) {
			conMapper.deleteConFileInfo(contractVO);
			conMapper.insertFileAttrConInfo(contractVO);
			contractVO.getFileList().forEach(e -> {
				ppsMapper.insertFileInfo(e);
			});
		}
		
		return result ==1 ? contractVO.getContractNo() : -1;
	}
	
}
