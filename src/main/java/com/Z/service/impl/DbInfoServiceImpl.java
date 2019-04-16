package com.Z.service.impl;

import java.util.ArrayList;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Z.config.ConstantConfig;
import com.Z.mapper.DbInfoMapper;
import com.Z.mapper.StudentMapper;
import com.Z.mapper.TeacherMapper;
import com.Z.pojo.DbInfo;
import com.Z.pojo.Student;
import com.Z.pojo.Teacher;
import com.Z.service.DbInfoService;
import com.Z.utils.Base64Util;
import com.Z.utils.DbAccess;
import com.Z.utils.FaceUtil;
import com.Z.utils.FileUtil;
import com.Z.utils.SaveResourceUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class DbInfoServiceImpl implements DbInfoService {

	@Autowired
	private Sid sid;
	@Autowired
	private DbInfoMapper dbInfoMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private FaceUtil faceUtil;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	/**
	 * @description 数据库中若存在该考试的数据库信息，则更新该条数据，若不存在，则插入
	 */
	public void saveDbInfo(String examId, String host, String port, String type, String dbName, String username,
			String password) {
		DbInfo dbInfo = new DbInfo();
		dbInfo.setId(sid.nextShort());
		dbInfo.setExamId(examId);
		dbInfo.setHost(host);
		dbInfo.setPort(port);
		dbInfo.setType(type);
		dbInfo.setDbName(dbName);
		dbInfo.setUsername(username);
		dbInfo.setPassword(password);
		
		if(selectDbInfoByExamId(examId) != null) {
			Example example = new Example(DbInfo.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("examId",examId);
			dbInfoMapper.updateByExampleSelective(dbInfo, example);
		}else {
			dbInfoMapper.insert(dbInfo);
		}
	}

	@Override
	public DbInfo selectDbInfoByExamId(String examId) {
		Example example = new Example(DbInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("examId",examId);
		return dbInfoMapper.selectOneByExample(example);
	}

	/**
	 * @throws Exception 
	 * @description 将用户外部提供的数据库视图导入系统的数据库中
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void initFromDbView(DbInfo dbInfo) throws Exception {
		DbAccess dbAccess = new DbAccess(dbInfo);
		
		//若已经初始化过
		Example example = new Example(Student.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("examId", dbInfo.getExamId());
		int count = studentMapper.selectCountByExample(example);
		if(count > 0) return ;
		
		//初始化学生信息
		ArrayList<Student> stuList = dbAccess.selectStuList();
		for (Student student : stuList) {
			student.setId(sid.nextShort());
			//获取服务器上的资源
			String resourcePath = dbInfo.getHost()+ "/" + student.getImg();
			student.setImg("/" + dbInfo.getExamId() + "/" + student.getImg());
			String diskPath = ConstantConfig.FILESPACE + student.getImg();
			SaveResourceUtil.saveImg(resourcePath , diskPath);
			//将图片存入人脸库
			byte[] bytes = FileUtil.readFileByBytes(diskPath);
			String image = Base64Util.encode(bytes);
			faceUtil.register(image, student.getExamId() + "_student" , student.getSnum(), "");
			studentMapper.insert(student);
			//Thread.sleep(300);
		}
		//初始化教师信息
		ArrayList<Teacher> teaList = dbAccess.selectTeaList();
		for (Teacher teacher : teaList) {
			teacher.setId(sid.nextShort());
			String resourcePath = dbInfo.getHost()+ "/" + teacher.getImg();
			teacher.setImg("/" + dbInfo.getExamId() + "/" + teacher.getImg());
			String diskPath = ConstantConfig.FILESPACE + teacher.getImg();
			SaveResourceUtil.saveImg(resourcePath, diskPath);
			//将图片存入人脸库
			byte[] bytes = FileUtil.readFileByBytes(diskPath);
			String image = Base64Util.encode(bytes);
			faceUtil.register(image, teacher.getExamId() + "_teacher" , teacher.getTnum() , teacher.getRoom());
			teacherMapper.insert(teacher);
			//Thread.sleep(300);
		}
	}

	/**
	 * @description 将用户提供的Excel数据导入数据库中
	 */
	@Override
	public void initFromExcel(DbInfo dbInfo) {
		// TODO Auto-generated method stub
		
	}
}
