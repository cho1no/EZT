package com.yedam.app.sgi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yedam.app.usr.service.UsrVO;

import lombok.Getter;

@Getter
public class LoginUsrVO implements UserDetails{

	private UsrVO usrVO;
	
	public LoginUsrVO(UsrVO usrVO) {
		this.usrVO = usrVO;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority(usrVO.getUsersRole()));
		return auths;
	}

	@Override
	public String getPassword() {
		return usrVO.getUsersPw();
	}

	@Override
	public String getUsername() {
		return usrVO.getUsersId();
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
