package com.yedam.app.alm.mapper;

import java.util.List;

import com.yedam.app.alm.service.AlarmVO;

public interface AlarmMapper {
	
	// 알람 리스트
	public List<AlarmVO> selectMyAllAlarm(int usersNo);

	// 알람 단건 조회
	public AlarmVO selectMyAlarm(int alarmNo);
	// 읽지 않은 알람 수
	public int selectNotReadAlarm(int userNo); 
	
	// 알람 입력
	public int insertAlarm(AlarmVO alarmVO);
	
	
	// 단건 읽음 처리
	public int updateAlarm(int alarmNo);
	
	// 전체 읽음 처치
	public int updateAllAlarm(int usersNo);
}
