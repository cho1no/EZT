package com.yedam.app.usr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.app.usr.service.UserVO;

@Mapper
public interface UserMapper {
	//사용자 정보조회
	public UserVO selectUserInfo(String id);
			
	//사용자 회원가입
	void saveUser(UserVO userVO);
	
	//작업자 회원가입
	void saveWorker(UserVO userVO);
//	void insertLicense(UserVO userVO);
//	void insertActiveRegion(UserVO userVO);
//	void insertActiveCategory(UserVO userVO);

	//중복체크
	public int idChk(String id);
	
	public int nickChk(String nick);
	
	public int emailChk(String email);

	//사용자정보 수정
	public int updateUserInfo(UserVO userVO);
	
	//사용자정보 삭제
	public int deleteUserInfo(int userNo);
}
