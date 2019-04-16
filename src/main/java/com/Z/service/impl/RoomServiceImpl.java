package com.Z.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Z.mapper.RoomMapperCustom;
import com.Z.pojo.Room;
import com.Z.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomMapperCustom roomMapper;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Room> selectAllRoomByExamId(String examId) {
		
		return roomMapper.selectAllRoomByExamId(examId);
	}

}
