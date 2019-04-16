package com.Z.service;

import java.util.List;

import com.Z.pojo.Exam;

public interface ExamService {
	Exam selectExamById(String id);
	
	List<Exam> selectExamListByAccountId(String accountId);
	
	void saveExam(String ename, String data ,String hour ,String minute , String description,String accountId);

	List<Exam> selectExamListBySwitch1();
	
	void updateESwitch(String id , int eSwitch);
}
