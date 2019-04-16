package com.Z.mapper;

import java.util.List;

import com.Z.pojo.Room;

public interface RoomMapperCustom {
	List<Room> selectAllRoomByExamId(String examId);
}
