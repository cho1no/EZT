package com.yedam.app.doc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.doc.mapper.ContractMapper;
import com.yedam.app.doc.service.ContractDetailVO;
import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractMapper conMapper;
	
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
		return result ==1 ? contractVO.getContractNo() : -1;
	}
	
	// 계약서 조회
	@Override
	public ContractVO conInfo(ContractVO contracVO) {
		List<ContractDetailVO> list = conMapper.selectConDetailInfo(contracVO.getContractNo());
		ContractVO con = conMapper.selectConInfo(contracVO);
		con.setDList(list);
		return con;
	}

}
