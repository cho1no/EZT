package com.yedam.app.usr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.usr.service.UsrVO;

@Mapper
public interface UserMapper {
	
	public UsrVO selectUsrInfo(String id);
	
	void saveUsr(UsrVO usrVO);
}
