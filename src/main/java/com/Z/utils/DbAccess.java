package com.Z.utils;

/**
 * @description 查询用户提供的视图
 * @author H
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Z.pojo.DbInfo;
import com.Z.pojo.Student;
import com.Z.pojo.Teacher;

public class DbAccess {
	private DbInfo dbInfo;
	public DbAccess(DbInfo dbInfo) {
		this.dbInfo = dbInfo;
	}
	private Connection buildConnect() throws Exception {
		
		Connection conn = null;
		String url = "";
		switch (dbInfo.getType()) {
		case "mysql5":
		case "mariaDB":
			Class.forName("com.mysql.jdbc.Driver");
			//jdbc:mysql://localhost:3306/sqltestdb
			url = "jdbc:mysql://" + dbInfo.getHost().replaceAll("http://", "") + ":" + dbInfo.getPort() + "/" + dbInfo.getDbName();
			conn = DriverManager.getConnection(url , dbInfo.getUsername(),dbInfo.getPassword());
			break;
		case "mysql6":
		case "sqlserver":
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			url = "jdbc:sqlserver://" + dbInfo.getHost().replaceAll("http://", "") + ":" + dbInfo.getPort() + ";DatabaseName=" + dbInfo.getDbName();
			conn = DriverManager.getConnection(url,dbInfo.getUsername(),dbInfo.getPassword());
			break;
		case "oracle":
			
			break;

		default:
			break;
		}
		
		return conn;
		
	}
	
	public ArrayList<Student> selectStuList() throws Exception{
		Connection conn = buildConnect();
		String sql = "select snum,name,img,room from stu_view";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<Student> stuList = new ArrayList<>();
		while( rs.next() ) {
			Student student = new Student();
			student.setSnum(rs.getString("snum"));
			student.setName(rs.getString("name"));
			student.setImg(rs.getString("img"));
			student.setExamId(dbInfo.getExamId());
			student.setRoom(rs.getString("room"));
			stuList.add(student);
		}
//		System.out.println("---------------------student------------------------------");
//		for (Student student : stuList) {
//			System.out.println(student.getName());
//		}
		return stuList;
	}
	
	public ArrayList<Teacher> selectTeaList() throws Exception{
		Connection conn = buildConnect();
		String sql = "select tnum,name,img,room from tea_view";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<Teacher> teaList = new ArrayList<>();
		while( rs.next() ) {
			Teacher teacher = new Teacher();
			teacher.setTnum(rs.getString("tnum"));
			teacher.setName(rs.getString("name"));
			teacher.setImg(rs.getString("img"));
			teacher.setExamId(dbInfo.getExamId());
			teacher.setRoom(rs.getString("room"));
			teaList.add(teacher);
		}
//		System.out.println("---------------------teacher------------------------------");
//		for (Teacher teacher : teaList) {
//			System.out.println(teacher.getName());
//		}
		return teaList;
	}
}
