package com.Z.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Z.mapper.ExamMapper;
import com.Z.pojo.Exam;
import com.Z.service.ExamService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 与考试表相关的业务
 * @author H
 *
 */
@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private Sid sid;
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Exam selectExamById(String id) {
		Example example = new Example(Exam.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		return examMapper.selectOneByExample(example);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Exam> selectExamListByAccountId(String accountId) {
		Example example = new Example(Exam.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("accountId", accountId);
		return examMapper.selectByExample(example);
	}

		
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveExam(String ename, String data, String hour, String minute, String description,String accountId) {
		Exam exam = new Exam();
		exam.setId(sid.nextShort());
		exam.setEname(ename);
		String time = data.replace("-", "/") +" "+ String.format("%02d",Integer.parseInt(hour)) + ":" + String.format("%02d",Integer.parseInt(minute));
		exam.setTime(time);
		exam.setDescription(description);
		exam.setAccountId(accountId);
		exam.seteSwitch((byte)0);
		examMapper.insert(exam);
	}

	/**
	 * @description 查询出所有开放的考试项目
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Exam> selectExamListBySwitch1() {
		Example example = new Example(Exam.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("eSwitch", 1);
		return examMapper.selectByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateESwitch(String id , int eSwitch) {
		Exam exam = new Exam();
		exam.setId(id);
		exam.seteSwitch((byte) eSwitch);
		examMapper.updateByPrimaryKeySelective(exam);
	}
}
