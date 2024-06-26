package com.yedam.app.api.service;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface DatahubService {
	// 신분증 확인
	public Mono<Map> idCardCheck(Map<String, String> map);
	// 운전면허증 확인
	public Mono<Map> driveLicenseCheck(Map<String, String> map);
}
