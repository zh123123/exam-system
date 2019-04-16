package com.Z.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Z.mapper.StudentMapper;
import com.Z.pojo.Student;
import com.Z.service.StudentService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 于student表相关的业务类
 * @author H
 *
 */
@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Student> selectStudentListForRoom(String examId,String room) {
		Example example = new Example(Student.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("room", room);
		criteria.andEqualTo("examId", examId);
		return studentMapper.selectByExample(example);
	}

	@Override
	public void updateStudentForReg(String examId, String snum, String regImg) {

		Student student = new Student();
		student.setRegImg(regImg);
		student.setRegStatus((byte)1);
		Example example = new Example(Student.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("snum", snum);
		criteria.andEqualTo("examId", examId);
		studentMapper.updateByExampleSelective(student, example);
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Student selectStudentById(String id) {
		return studentMapper.selectByPrimaryKey(id);
	}

	
	
}
