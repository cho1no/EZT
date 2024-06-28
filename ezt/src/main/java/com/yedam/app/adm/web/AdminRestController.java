package com.yedam.app.adm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.adm.service.AdminService;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;

@RestController
@RequestMapping("/adm")
@CrossOrigin("http://localhost:3030")
public class AdminRestController {
	@Autowired
	AdminService admSvc;
//	@Autowired
//	UserService sguSvc;
	// 로그인 유저 반환
	@GetMapping("logInfo")
	public Map<String, Object> getLogInfo(@AuthenticationPrincipal LoginUserVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", vo.getUsername());
		map.put("auth", vo.getAuthorities());

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
}
