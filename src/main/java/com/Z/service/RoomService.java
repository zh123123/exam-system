package com.Z.service;

import java.util.List;

import com.Z.pojo.Room;

public interface RoomService {
	List<Room> selectAllRoomByExamId(String examId);
}
