package com.yedam.app.alm.service;

import java.util.List;

public interface AlarmService {
	
	// select all my alarm
	public List<AlarmVO> getAlarmList(int usersNo);
	
	// select my alarm
	public AlarmVO getAlarm(int alarmNo);
	
	// select not read alarm count
	public int getCntNotRead(int usersNo);
	
	// insert alarm
	public int sendAlarm(AlarmVO alarmVO);
	
	// update alarm read Tf
	public int readAlarm(int alarmNo);
	
	// update all alarm read Tf
	public int readAllAlarm(int usersNo);
}
