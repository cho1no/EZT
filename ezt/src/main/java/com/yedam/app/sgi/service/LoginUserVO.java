package com.yedam.app.sgi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yedam.app.usr.service.UserVO;

import lombok.Getter;

@Getter
public class LoginUserVO implements UserDetails{

	private UserVO userVO;
	
	public LoginUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority(userVO.getUsersRole()));
		return auths;
	}

	@Override
	public String getPassword() {
		return userVO.getUsersPw();
	}

	@Override
	public String getUsername() {
		return userVO.getUsersNo().toString();
	}
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
}
