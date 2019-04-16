package com.Z.service;

import java.util.List;

import com.Z.pojo.Student;

public interface StudentService {
	List<Student> selectStudentListForRoom(String examId,String room);
	
	void updateStudentForReg(String examId, String snum , String regImg);
	
	Student selectStudentById(String id);
}
