package com.Z.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Z.config.ConstantConfig;
import com.Z.pojo.JSONResult;
import com.Z.pojo.Student;
import com.Z.service.StudentService;
import com.Z.utils.Base64Util;
import com.Z.utils.FaceUtil;

@RestController
public class StudentRecogController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private FaceUtil face;

	@PostMapping("/getStuList")
	public JSONResult getStuList(String examId, String room) {
		System.out.println("examId:" + examId);
		System.out.println("room:" + room);
		if (StringUtils.isBlank(room) || StringUtils.isBlank(examId)) {
			return JSONResult.errorMsg("参数出错");
		}

		List<Student> stuList = studentService.selectStudentListForRoom(examId, room);

		return JSONResult.ok(stuList);
	}

	@PostMapping("/faceRecog")
	public JSONResult faceRecog(String snum, String examId, MultipartFile file) {

		if (StringUtils.isBlank(snum) || file == null) {
			return JSONResult.errorMsg("参数出错");
		}

		System.out.println("------------------------studentrecog-----------------------------");
		System.out.println("snum ：" + snum);
		System.out.println("examId : " + examId);
		
		
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			byte[] bytes = file.getBytes();
			String image = Base64Util.encode(bytes);
			JSONObject res = face.search(image, examId + "_student");

			Object result = res.get("result");
			if (result.equals(null)) {
				return JSONResult.ok(false);
			}
			JSONObject result2 = (JSONObject) result;
			JSONObject user = result2.getJSONArray("user_list").getJSONObject(0);
			System.out.println("user_id : " + user.getString("user_id"));
			System.out.println("score : " + user.getDouble("score"));
			if (user.getString("user_id").equals(snum) && user.getDouble("score") > 80) {
				
				// 验证通过后将图片保存到本地
				is = file.getInputStream();
				String regImg = "/" + examId + "/stu_reg/" + snum + ".jpg";
				System.out.println("验证通过...");
				studentService.updateStudentForReg(examId, snum, regImg);
				File dirFile = new File(ConstantConfig.FILESPACE + regImg);
				if (dirFile.getParentFile() != null || !dirFile.getParentFile().isDirectory()) {
					dirFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(dirFile);
				IOUtils.copy(is, fos);

				return JSONResult.ok(true);
			} else {
				return JSONResult.ok(false);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JSONResult.errorMsg("验证失败");
		} finally {

			try {
				if (fos != null) {

					fos.flush();
					fos.close();
				}
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {

				e.printStackTrace();
				return JSONResult.errorMsg("验证失败");
			}

		}

	}
}
