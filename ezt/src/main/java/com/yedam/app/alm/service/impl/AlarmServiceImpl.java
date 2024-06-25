package com.yedam.app.alm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.alm.mapper.AlarmMapper;
import com.yedam.app.alm.service.AlarmService;
import com.yedam.app.alm.service.AlarmVO;

@Service
public class AlarmServiceImpl implements AlarmService{
	@Autowired
	AlarmMapper alarmMapper;
	@Override
	public List<AlarmVO> getAlarmList(int usersNo) {
		return alarmMapper.selectMyAllAlarm(usersNo);
	}
	
	@Override
	public AlarmVO getAlarm(int alarmNo) {
		return alarmMapper.selectMyAlarm(alarmNo);
	}
	@Override
	public int getCntNotRead(int usersNo) {
		return alarmMapper.selectNotReadAlarm(usersNo);
	}
	
	@Override
	public int sendAlarm(AlarmVO alarmVO) {
		return alarmMapper.insertAlarm(alarmVO);
	}

	@Override
	public int readAlarm(int alarmNo) {
		return alarmMapper.updateAlarm(alarmNo);
	}

	@Override
	public int readAllAlarm(int usersNo) {
		return alarmMapper.updateAllAlarm(usersNo);
	}

}
