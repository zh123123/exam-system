package com.Z.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Z.pojo.Account;
import com.Z.pojo.DbInfo;
import com.Z.pojo.Exam;
import com.Z.pojo.JSONResult;
import com.Z.pojo.Room;
import com.Z.pojo.Student;
import com.Z.pojo.Teacher;
import com.Z.service.DbInfoService;
import com.Z.service.ExamService;
import com.Z.service.RoomService;
import com.Z.service.StudentService;
import com.Z.service.TeacherService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private DbInfoService dbInfoService;
	@Autowired
	private ExamService examService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private RoomService roomService ;
	
	@RequestMapping("")
	public String home(Account account , HttpSession session) {
		setExamListToSession(session);
		return "home";
	}
	
	@RequestMapping("/createExam")
	@ResponseBody
	public JSONResult createExam(String ename,String data,String hour ,String minute ,String description,String accountId) {
		System.out.println("ename:" + ename);
		System.out.println("data:" + data );
		System.out.println("hour:" + hour );
		System.out.println("minute:" + minute );
		System.out.println("description:" + description );
		System.out.println("accountId:" + accountId );
		if(StringUtils.isBlank(ename) || StringUtils.isBlank(data) || StringUtils.isBlank(hour) || 
				StringUtils.isBlank(minute) ||  StringUtils.isBlank(accountId)) {
			return JSONResult.errorMsg("参数出错。");
		}
		examService.saveExam(ename, data, hour, minute, description, accountId);
		return JSONResult.ok();
	}
	
	@RequestMapping("/saveDbInfo")
	@ResponseBody
	public JSONResult saveDbInfo(String examId,String host,String port ,String type ,@RequestParam("db_name") String dbName,String username,String password) {
		System.out.println("examId:" + examId);
		System.out.println("host:" + host );
		System.out.println("port:" + port );
		System.out.println("type:" + type );
		System.out.println("dbName:" + dbName );
		System.out.println("username:" + username );
		System.out.println("password:" + password );
		if(StringUtils.isBlank(examId) || StringUtils.isBlank(host) || StringUtils.isBlank(port) || 
				StringUtils.isBlank(type) ||  StringUtils.isBlank(dbName) ||  StringUtils.isBlank(username) ||  
				StringUtils.isBlank(password)) {
			return JSONResult.errorMsg("参数出错。");
		}
		dbInfoService.saveDbInfo(examId, host, port, type, dbName, username, password);
		
		return JSONResult.ok();
	}
	
	@PostMapping("/initDb")
	@ResponseBody
	public JSONResult initDb(String examId) {
		DbInfo dbInfo = dbInfoService.selectDbInfoByExamId(examId);
		try {
			dbInfoService.initFromDbView(dbInfo);
		} catch (Exception e) {
			//e.printStackTrace();
			return JSONResult.errorMsg("初始化出错");
		}
		
		return JSONResult.ok();
	}
	
	@RequestMapping("/create")
	public String create(HttpSession session) {

		setExamListToSession(session);
		return "start-create";
	}
	
	@RequestMapping("/db")
	public String db(HttpSession session) {
		setExamListToSession(session);
		return "start-db";
	}
	@RequestMapping("/excel")
	public String excel(HttpSession session) {
		setExamListToSession(session);
		return "start-excel";
	}
	
//	@RequestMapping("/exam")
//	public String exam(@RequestParam("exam") String examId,ModelMap map) {
//		Exam exam = examService.selectExamById(examId);
//		List<Student> stuList = studentService.selectStudentListForRoom(examId, "x3513b");
//		List<Teacher> teaList = teacherService.selectTeacherByRoom(examId, "x3513b");
//		map.addAttribute("exam", exam);
//		map.addAttribute("count",50);
//		if(!stuList.isEmpty())
//			map.addAttribute("stuList", stuList);
//		if(!teaList.isEmpty())
//			map.addAttribute("teaList", teaList);
//		return "exam";
//	}
	
	@RequestMapping("/exam")
	public String exam(@RequestParam("exam") String examId,ModelMap map) {
		Exam exam = examService.selectExamById(examId);
		//查出所有考场
		List<Room> rooms = roomService.selectAllRoomByExamId(examId);
		for (Room room : rooms) {
			System.out.println(room);
		}
		map.addAttribute("rooms", rooms);
		map.addAttribute("exam", exam);
		map.addAttribute("count",rooms.size());
		
		return "exam";
	}
	
	@RequestMapping("/room/info")
	public String roomInfo(String examId,String room,ModelMap map) {
		Exam exam = examService.selectExamById(examId);
		List<Student> stuList = studentService.selectStudentListForRoom(examId, room);
		List<Teacher> teaList = teacherService.selectTeacherByRoom(examId, room);
		Room roomInfo = new Room();
		roomInfo.setRoom(room);
		roomInfo.setCount(stuList.size());
		map.addAttribute("exam", exam);
		map.addAttribute("room", roomInfo);
		if(!stuList.isEmpty())
			map.addAttribute("stuList", stuList);
		if(!teaList.isEmpty())
			map.addAttribute("teaList", teaList);
		return "room-info";
	}
	

	@RequestMapping("/room/teacher")
	public String teacher(String examId,String room ,String teacherId ,ModelMap map) {
		Exam exam = examService.selectExamById(examId);
		Teacher teacher = teacherService.selectTeacherById(teacherId);
		map.addAttribute("exam", exam);
		map.addAttribute("teacher", teacher);
		return "teacher";
	}
	
	@RequestMapping("/room/student")
	public String student(String examId,String room ,String studentId ,ModelMap map) {
		Exam exam = examService.selectExamById(examId);
		Student student = studentService.selectStudentById(studentId);
		map.addAttribute("exam", exam);
		map.addAttribute("student", student);
		return "student";
	}
	
	@PostMapping("/exam/switch")
	@ResponseBody
	public JSONResult eSwitch(String examId , String value) {
		int eSwitch = value.equals("true") ? 1:0;
		examService.updateESwitch(examId, eSwitch);
		return JSONResult.ok();
	}
	
	
	@RequestMapping("/document")
	public String document() {
		
		return "document";
	}
	
	public void setExamListToSession(HttpSession session) {
		Account user = (Account) session.getAttribute("user");
		List<Exam> examList = examService.selectExamListByAccountId(user.getId());
		session.setAttribute("examList", examList);
	}
	
	
	
	
}
