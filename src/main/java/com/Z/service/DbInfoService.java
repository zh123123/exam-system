package com.Z.service;

import com.Z.pojo.DbInfo;

public interface DbInfoService {
	
	DbInfo selectDbInfoByExamId(String examId);
	
	void saveDbInfo(String examId,String host,String port ,String type ,String dbName,String username,String password);
	
	void initFromDbView(DbInfo dbInfo) throws Exception;
	
	void initFromExcel(DbInfo dbInfo);
}
