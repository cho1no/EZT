package com.yedam.app.sgu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UsrVO;

import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SignUpService {
	//회원가입시 저장시간 넣어줄 DateRime형
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
	Date time = new Date();
	String localTime = format.format(time);
	
	@Autowired
	UserMapper userMapper;
	
	@Transactional
	public void joinUsr(UsrVO usrVO) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usrVO.setUsersPw(passwordEncoder.encode(usrVO.getUsersPw()));
		usrVO.setUsersRole("USER");
		userMapper.saveUsr(usrVO);
	}

}
