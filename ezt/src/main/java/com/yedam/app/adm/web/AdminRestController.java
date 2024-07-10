package com.yedam.app.adm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.adm.security.CheckToken;
import com.yedam.app.adm.security.JwtProvider;
import com.yedam.app.adm.service.AdminService;
import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.common.service.CommonCodeVO;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/adm")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class AdminRestController {
	@Autowired
	CommonCodeService ccSvc;
	@Autowired
	AdminService admSvc;
	@Autowired
	JwtProvider jwtProvider;

	// 로그인 토큰 발급
	@GetMapping("jwt")
	public ResponseEntity<?> getLogInfo(@AuthenticationPrincipal LoginUserVO vo) {
		if (vo != null && vo.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			String token = jwtProvider.createToken(vo.getUserVO());
			log.info(token);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
	}
	// 토큰 확인
	@CheckToken
	@GetMapping("/checkJwt")
	public ResponseEntity<?> isOk() {
		return ResponseEntity.ok().build();
	}
	// 본인 정보 토큰 변환
	@CheckToken
	@GetMapping("/getMyInfo")
	public UserVO getMyInfo(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		return jwtProvider.getUserInfo(token);
	}
	
	// 통계(메인페이지)
	@CheckToken
	@GetMapping("/getStatistic")
	public Map<String, Object> getStatistic() {
		Map<String, Object> map = new HashMap<>();
		log.info(admSvc.getReqCategoryStatistics().toString());
		map.put("newJoin", admSvc.getJoinStatistics()); // 일별 가입자 현황
		map.put("reqCategory", admSvc.getReqCategoryStatistics()); // 의뢰 분야별 통계
		map.put("reqRegion", admSvc.getReqRegionStatistics()); // 의뢰 지역별 통계
		return map;
	}

	// 회원 전체 조회
	@CheckToken
	@GetMapping("/usersInfo")
	public List<UserVO> getUsersInfo() {
		return admSvc.getUsers();
	}

	// 회원 단건 조회
	@CheckToken
	@GetMapping("/userInfo/{usersNo}")
	public UserVO getUserInfo(@PathVariable int usersNo) {
		return admSvc.getUser(usersNo);
	}

	// 회원 활동정지
	@CheckToken
	@GetMapping("/userPause/{usersNo}")
	public int setUserPause(@PathVariable int usersNo) {
		return admSvc.setUserPause(usersNo);
	}

	// 회원 활동정지
	@CheckToken
	@GetMapping("/userActive/{usersNo}")
	public int setUserActive(@PathVariable int usersNo) {
		return admSvc.setUserActive(usersNo);
	}
	
	// 공통 코드 가져오기
	@CheckToken
	@GetMapping("/getCommonCodes/{codeType}")
	public List<CommonCodeVO> getCommonCodesByType(@PathVariable String codeType){
		return ccSvc.selectCommonCodeAll(codeType);
	}
	
	
	// 의뢰 전체 조회
	@CheckToken
	@GetMapping("/requestsInfo")
	public List<RequestVO> getRequests(){
		return admSvc.getRequests();
	}
	
	// 통일 계약서 전체 조회
	@CheckToken
	@GetMapping("/unityContractsInfo")
	public List<UnityContractVO> getUnityContracts() {
		return admSvc.getUnityContracts();
	}

	// 통일 계약서 단건 조회
	@CheckToken
	@GetMapping("/unityContractInfo/{no}")
	public UnityContractVO getUnityContract(@PathVariable int no) {
		return admSvc.getUnityContract(no);
	}

	// 통일 계약서 등록
	@CheckToken
	@PostMapping("/postUnityContract")
	public UnityContractVO postUnityContract(@RequestBody UnityContractVO vo) {
		return admSvc.postUnityContract(vo);
	}

	// 기본 계약서 업데이트
	@CheckToken
	@GetMapping("/putUnityBasic/{no}")
	public int putUnityContract(@PathVariable int no) {
		return admSvc.putUnityContractBasicTf(no);
	}

	// 경력 인증 전체 조회
	@CheckToken
	@GetMapping("/careersInfo")
	public List<CareerVO> getCareers() {
		return admSvc.getCareers();
	}

	// 경력 인증 단건 조회
	@CheckToken
	@GetMapping("/careerInfo/{careerNo}")
	public CareerVO getCareer(@PathVariable int careerNo) {
		return admSvc.getCareer(careerNo);
	}

	// 경력 인증 승인
	@CheckToken
	@GetMapping("/careerAccept/{careerNo}")
	public int setCareerAccept(@PathVariable int careerNo) {
		return admSvc.setCareerAccept(careerNo);
	}

	// 경력 인증 반려 <- 사용 X
	@CheckToken
	@GetMapping("/careerDeny/{careerNo}")
	public int setCareerDeny(@PathVariable int careerNo) {
		return admSvc.setCareerDeny(careerNo);
	}

	// 경력인증 반려 사유 등록
	@CheckToken
	@PostMapping("/careerDeny")
	public int postCareerDeny(@RequestBody Map<String, String> map) {
		return admSvc.postCareerDeny(map);
	}
	
	@GetMapping("/testThis")
	public List<Map<String, Object>> testThis(){
		return admSvc.getPayManages();
	}
}
