package com.yedam.app.sgi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedam.app.sgi.service.LoginUsrVO;
import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UsrVO;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsrVO usrVO = userMapper.selectUsrInfo(username);
		
		if(usrVO == null) {
			throw new UsernameNotFoundException(username);
		}
		    
		return new LoginUsrVO(usrVO);
	}
	
	
}
