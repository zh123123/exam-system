package com.Z.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Z.config.ConstantConfig;
import com.Z.pojo.Exam;
import com.Z.pojo.JSONResult;
import com.Z.service.ExamService;
import com.Z.service.TeacherService;
import com.Z.utils.Base64Util;
import com.Z.utils.FaceUtil;

@RestController
public class TeacherRecogController {
	
	@Autowired
	private FaceUtil face;
	@Autowired
	private ExamService examService;
	@Autowired
	private TeacherService teacherService;
	
	@PostMapping("/getExamList")
	public JSONResult getExamList() {
		List<Exam> examList = examService.selectExamListBySwitch1();
		return JSONResult.ok(examList);
	}
	
	
	@PostMapping("/teaLogin")
	public JSONResult teaLogin(MultipartFile file , String examId) {
		if(file == null) {
			return JSONResult.errorMsg("参数出错");
		}
		System.out.println("examId:" + examId);
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			
			byte[] bytes = file.getBytes();
			String image = Base64Util.encode(bytes);
			JSONObject res = face.search(image, examId + "_teacher");
			
			Object result = res.get("result");
			if(result.equals(null)) {
				return JSONResult.errorMsg("验证失败");
			}
			JSONObject result2 = (JSONObject) result;
			JSONObject user =result2.getJSONArray("user_list").getJSONObject(0);
			String tnum = user.getString("user_id");
			double score = user.getDouble("score");
			System.out.println("user_id : " + tnum);
			System.out.println("score : " + score);
			if(score > 80) {
				
				//验证通过后将图片保存到本地
				is = file.getInputStream();
				String regImg = "/" + examId + "/tea_reg/" + tnum + ".jpg";
				
				teacherService.updateTeacherForReg(examId , tnum , regImg);
				
				File dirFile = new File(ConstantConfig.FILESPACE + regImg);
				
				if(dirFile.getParentFile() != null || !dirFile.getParentFile().isDirectory()) {
					dirFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(dirFile);
				
				IOUtils.copy(is, fos);
				
				return JSONResult.ok(user.getString("user_info"));
				
			}else {
				return JSONResult.errorMsg("验证失败");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return JSONResult.errorMsg("验证失败");
		}finally {

			try {
				if (fos != null) {

					fos.flush();
					fos.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}

//	public static void main(String[] args) {
//		FaceUtil face = new FaceUtil();
//		
//		face.deleteGroup("18121967H99T0NXP_student");
//		face.deleteGroup("18121967H99T0NXP_teacher");
//	}
	
}
