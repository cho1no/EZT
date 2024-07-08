package com.yedam.app.api.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.api.service.DatahubService;
import com.yedam.app.api.service.NhDevService;
import com.yedam.app.api.service.PublicDataService;
import com.yedam.app.sgi.service.LoginUserVO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {
	@Autowired DatahubService datahubSvc;
	@Autowired PublicDataService publicDataSvc;
	@Autowired NhDevService nhDevSvc;
	@GetMapping("/getInfo")
	public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal LoginUserVO vo) {
		if (vo != null) {
			return ResponseEntity.ok(vo.getUserVO());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 되지 않았습니다.");
	}
	/** 
	 * datahub 신분증 확인 api
	 * @param map
	 * @return JSON
	 */
	@PostMapping("/idCardCheck")
	public Mono<Map> idCard(@RequestBody Map<String, String> map) {
		return datahubSvc.idCardCheck(map);
	}
	/** 
	 * datahub 운전면허증 확인 api
	 * @param map
	 * @return JSON
	 */
	@PostMapping("/driveLicenseCheck")
	public Mono<Map> driveLicense(@RequestBody Map<String, String> map) {
		log.info(map.toString());
		return datahubSvc.driveLicenseCheck(map);
	}

	/**
	 *  국가 기술 자격증 확인 api
	 * @param licenseId - String
	 * @return
	 */
	@GetMapping("/techCheck/{licenseId}")
	public Mono<Map> techLicense(@PathVariable String licenseId){
		return publicDataSvc.TechLicenseCheck(licenseId);
	}
	
	/**
	 *  NH 가상계좌 발급
	 * @return 계좌번호
	 */
	@GetMapping("/getVirtualAc")
	public Mono<String> getVirtualAc(){
		return nhDevSvc.getVirtualAc();
	}
	/**
	 *  NH 가상계좌 입금 조회
	 *  미입력시 전체조회
	 * @param vran : 조회할 계좌번호(미입력시 전체 조회)
	 * @return 입금 내역 리스트
	 */
	@GetMapping("/getVirtualAcRcvList")
	public List<Object> getVirtualAcRcvList(String vran){
		if (vran == null) vran="";
		List<Object> list = nhDevSvc.getVirtualAcRecieveList(vran);
		
		return list;
	}
	
}