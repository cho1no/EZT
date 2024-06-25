package com.yedam.app.sgi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserVO;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO userVO = userMapper.selectUserInfo(username);
		
		if(userVO == null) {
			throw new UsernameNotFoundException(username);
		}
		    
		return new LoginUserVO(userVO);
	}
	
}
