package com.yedam.app.adm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.adm.service.AdminService;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.jwt.service.impl.JwtTokenProvider;
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
	AdminService admSvc;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	// 로그인 토큰 발급
	@GetMapping("logInfo")
	public Map<String, Object> getLogInfo(@AuthenticationPrincipal LoginUserVO vo, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		map.put("token",  jwtTokenProvider.createToken(vo.getUserNo().toString(), vo.getUserVO().getUsersRole()));
//		req.getSession().setAttribute("token", map.get("token"));
		return map;
	}
	// 토큰에서 userId받기
	@GetMapping("getUserInfo/{token}")
	public String getAuth(@PathVariable String token) {
		return jwtTokenProvider.getUserPk(token);
	}
	
	
	// 통계(메인페이지)
	@GetMapping("/getStatistic")
	public Map<String, Object> getStatistic(){
		Map<String, Object> map = new HashMap<>();
		log.info(admSvc.getReqCategoryStatistics().toString());
		map.put("newJoin", admSvc.getJoinStatistics()); // 일별 가입자 현황
		map.put("reqCategory", admSvc.getReqCategoryStatistics()); // 의뢰 분야별 통계
		map.put("reqRegion", admSvc.getReqRegionStatistics()); // 의뢰 지역별 통계
		return map;
	}


	// 회원 전체 조회
	@GetMapping("/usersInfo")
	public List<UserVO> getUsersInfo() {
		return admSvc.getUsers();
	}
	
	// 회원 단건 조회
	@GetMapping("/userInfo/{usersNo}")
	public UserVO getUserInfo(@PathVariable int usersNo) {
		return admSvc.getUser(usersNo);
	}
	
	// 회원 활동정지
	@GetMapping("/userPause/{usersNo}")
	public int setUserPause(@PathVariable int usersNo) {
		return admSvc.setUserPause(usersNo);
	}
	// 회원 활동정지
	@GetMapping("/userActive/{usersNo}")
	public int setUserActive(@PathVariable int usersNo) {
		return admSvc.setUserActive(usersNo);
	}
	
	
	// 통일 계약서 전체 조회
	@GetMapping("/unityContractsInfo")
	public List<UnityContractVO> getUnityContracts(){
		return admSvc.getUnityContracts();
	}
	// 통일 계약서 단건 조회
	@GetMapping("/unityContractInfo/{no}")
	public UnityContractVO getUnityContract(@PathVariable int no) {
		return admSvc.getUnityContract(no);
	}
	// 통일 계약서 등록
	@PostMapping("/postUnityContract")
	public UnityContractVO postUnityContract(@RequestBody UnityContractVO vo) {
		return admSvc.postUnityContract(vo);
	}
	// 기본 계약서 업데이트
	@GetMapping("/putUnityBasic/{no}")
	public int putUnityContract(@PathVariable int no) {
		return admSvc.putUnityContractBasicTf(no);
	}
	
	// 경력 인증 전체 조회
	@GetMapping("/careersInfo")
	public List<CareerVO> getCareers(){
		return admSvc.getCareers();
	}
	// 경력 인증 단건 조회
	@GetMapping("/careerInfo/{careerNo}")
	public CareerVO getCareer(@PathVariable int careerNo) {
		return admSvc.getCareer(careerNo);
	}
	// 경력 인증 승인
	@GetMapping("/careerAccept/{careerNo}")
	public int setCareerAccept(@PathVariable int careerNo) {
		return admSvc.setCareerAccept(careerNo);
	}
	// 경력 인증 반려 <- 사용 X
	@GetMapping("/careerDeny/{careerNo}")
	public int setCareerDeny(@PathVariable int careerNo) {
		return admSvc.setCareerDeny(careerNo);
	}
	// 경력인증 반려 사유 등록
	@PostMapping("/careerDeny")
	public int postCareerDeny(@RequestBody Map<String, String> map) {
		return admSvc.postCareerDeny(map);
	}
}
