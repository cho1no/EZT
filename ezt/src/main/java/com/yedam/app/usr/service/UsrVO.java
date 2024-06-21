package com.yedam.app.usr.service;

import java.util.Date;

import lombok.Data;

@Data
public class UsrVO {
	private Integer usersNo;
	private String usersId;
	private String usersPw;
	private String usersEmail;
	private String usersBirth;
	private String usersRnn;
	private String usersGender;
	private String usersName;
	private String usersPhone;
	private String usersNick;
	private String usersRole;
	private Date usersJoinDt;
	private Date usersStateChangeDt;
	private String usersState;
	private Integer fileId;
}
