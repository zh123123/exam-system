package com.Z.service;

import java.util.List;

import com.Z.pojo.Teacher;

public interface TeacherService {

	void updateTeacherForReg(String examId ,String tnum ,String regImg);
	
	List<Teacher> selectTeacherByRoom(String examId,String room);
	
	Teacher selectTeacherById(String id);
}
