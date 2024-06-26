package com.yedam.app.api.service;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface PublicDataService {
	public Mono<Map> TechLicenseCheck(String licenseId);
}
