package com.yedam.app.api.service;

import java.util.List;
import java.util.Map;

import reactor.core.publisher.Mono;

public interface NhDevService {
	// 가상계좌 발급
	public Mono<String> getVirtualAc();
	// 계좌 입금내역 조회
	public List<Object> getVirtualAcRecieveList(String Vran);
	// NH 계좌간 송금
	public Mono<Map> receivedTransferAccountNumber(Map<String, String> input);
	// 타행 계좌 송금
	public Mono<Map> receivedReceivedTransferOtherBank(Map<String, String> input);
	// 예치금 모계좌로 송금
	public Mono<Map> paymentPayoutAccountTransfer();
}
