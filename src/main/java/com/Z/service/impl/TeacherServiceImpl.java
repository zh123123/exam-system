package com.Z.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Z.mapper.TeacherMapper;
import com.Z.pojo.Teacher;
import com.Z.service.TeacherService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateTeacherForReg(String examId , String tnum , String regImg) {
		Teacher teacher = new Teacher();
		teacher.setRegImg(regImg);
		teacher.setLoginTime(new Date());
		Example example = new Example(Teacher.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("tnum", tnum);
		criteria.andEqualTo("examId", examId);
		teacherMapper.updateByExampleSelective(teacher, example);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Teacher> selectTeacherByRoom(String examId, String room) {
		Example example = new Example(Teacher.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("room", room);
		criteria.andEqualTo("examId", examId);
		return teacherMapper.selectByExample(example);
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Teacher selectTeacherById(String id) {
		return teacherMapper.selectByPrimaryKey(id);
	}

}
