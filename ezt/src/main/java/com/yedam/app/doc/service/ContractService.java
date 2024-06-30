package com.yedam.app.doc.service;

public interface ContractService {

	// 계약서 등록&상세
	public int conInsert(ContractVO contracVO);
	
	// 계약서 단건조회
	public ContractVO conInfo(ContractVO contracVO);
}
