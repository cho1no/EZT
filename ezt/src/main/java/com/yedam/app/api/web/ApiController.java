package com.yedam.app.api.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.api.service.DatahubService;
import com.yedam.app.api.service.PublicDataService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {
	@Autowired
	DatahubService datahubSvc;
	@Autowired
	PublicDataService publicDataSvc;

	// datahub 신분증 확인 api
	@PostMapping("/idCardCheck")
	public Mono<Map> idCard(@RequestBody Map<String, String> map) {
		return datahubSvc.idCardCheck(map);
	}
	
	@PostMapping("/driveLicenseCheck")
	public Mono<Map> driveLicense(@RequestBody Map<String, String> map) {
		log.info(map.toString());
		return datahubSvc.driveLicenseCheck(map);
	}

	// 국가 기술 자격증 확인 api
	@GetMapping("/techCheck/{licenseId}")
	public Mono<Map> techLicense(@PathVariable String licenseId){
		return publicDataSvc.TechLicenseCheck(licenseId);
	}

}