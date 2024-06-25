package com.yedam.app.alm.service;

import java.util.Date;

import lombok.Data;

@Data
public class AlarmVO {
	private Integer alarmNo;
	private String title;
	private String content;
	private Date alarmTime;
	private Integer usersNo;
	private String readTf;
}
