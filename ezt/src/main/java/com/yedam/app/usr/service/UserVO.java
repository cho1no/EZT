package com.yedam.app.usr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //빌더패턴 자동생성
public class UserVO {
	private Integer usersNo;
	
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^([A-za-z]{0,0})(?=.*[a-zA-Z])(?=.*[0-9]).{4,20}$", message = "아이디는 영어,숫자가 포함된 4~20자리여야 합니다.")
	private String usersId;
	
	@NotEmpty(message = "비밀번호를 입력해주세요")
//  @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//          message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
	private String usersPw;
	private String usersPwCheck;
	private String usersNewPw;
	private String usersNewPwChk;
	
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")    
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String usersEmail;
	
	private String usersBirth;
	private String usersRnn;
	private String usersGender;
	private String usersName;
	private String usersPhone;
	
	@NotBlank(message = "닉네임은 필수 입력값입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
	private String usersNick;
	
	private String usersRole;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date usersJoinDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date usersStateChangeDt;
	
	private String usersState;
	private String usersStateNm;
	
	private Integer licenseNo;
	private String licenseDetailNo;
	private Integer fileId;
	
	//활동지역
	private List<String> regionCode;
	//작업분야(카테고리)
	private List<String> categoryCode;
	
	
	
}
