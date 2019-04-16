package com.Z.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;

/**
 * 对百度AI库操作的工具类
 * @author H
 *
 */
@Component
public class FaceUtil {
	//设置APPID/AK/SK
    private String appId = "14275842";
    private String apiKey = "G3G6j4P49GzAS5qU7OVXCvMi";
    private String secretKey = "41MZ74lO9HqzKZ4CkNzQUeZDIz7P6FWG";
    
//    public Face(String app_id,String api_key ,String secret_key) {
//    	this.app_id = app_id;
//    	this.api_key = api_key;
//    	this.secret_key = secret_key;
//    }
    
    /**
     * 人脸检测 
     * @param  imagePath:待检测的图片的BASE64编码
     * @throws IOException 
     * @return JSONObject 检测结果
     */
    public JSONObject detect(String image) throws IOException {
        // 初始化一个AipFace
        AipFace client = new AipFace(appId, apiKey, secretKey);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        // 调用接口
        JSONObject res = client.detect( image, "BASE64", new HashMap<String, String>());
        System.out.println(res.toString(2));
        return res;
    }
    /**
     * 	人脸对比
     * @param image1 ， image2 为待对比图片的BASE64编码
     * @return json数据中的score为人脸相似度得分
     * @throws IOException 
     */
    public JSONObject match(String image1, String image2) throws IOException {
    	
    	//初始化AipFace
    	AipFace client = new AipFace(appId, apiKey, secretKey);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        
		// image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
		//这里统一使用BASE64
        MatchRequest req1 = new MatchRequest(image1, "BASE64");
        MatchRequest req2 = new MatchRequest(image2, "BASE64");
        ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(req1);
        requests.add(req2);
        JSONObject res = client.match(requests);
        System.out.println(res.toString(2));
        return res;
    }
    /**
     *   人脸搜索
     * @param image  进行搜索的人脸图片的BASE64编码
     * @param groupIds	从指定的group中进行查找 
     * @return
     * @throws IOException
     */
    public JSONObject search( String image , String... groupIds) throws IOException {

    	AipFace client = new AipFace(appId, apiKey, secretKey);
		
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("quality_control", "NORMAL");
		//options.put("liveness_control", "LOW");
		//默认为1
		//options.put("max_user_num", "3");

		String imageType = "BASE64";
		StringBuilder groupIdList = new StringBuilder();
		for (String g : groupIds) {
			groupIdList.append(","+g);
		}
		groupIdList.delete(0, 1);
		// 人脸搜索
		JSONObject res = client.search(image, imageType, groupIdList.toString(), options);
		System.out.println(res.toString(2));
		return res;
	}
    
    /**
     * 	人脸注册
     * @param image		注册提交的人脸图片的BASE64编码
     * @param groupId
     * @param userId	
     * @return
     * @throws IOException
     */
  	public JSONObject register(String image , String groupId , String userId ,String userInfo) throws IOException {
  		AipFace client = new AipFace(appId, apiKey, secretKey);
  		
  	    // 传入可选参数调用接口
  	    HashMap<String, String> options = new HashMap<String, String>();
  	    options.put("user_info", userInfo);
  	    options.put("quality_control", "NORMAL");
  	    //options.put("liveness_control", "LOW");
  		
  	    String imageType = "BASE64";
  	    
  	    // 人脸注册
  	    JSONObject res = client.addUser(image, imageType, groupId, userId, options);
  	    System.out.println(res.toString(2));
  	    return res;
  	}
  	
  	/**
  	 *  删除用户组
  	 * @param groupId 待删除的用户组id
  	 */
  	public void deleteGroup(String groupId) {
  		AipFace client = new AipFace(appId, apiKey, secretKey);
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();  
	    // 删除用户组
	    JSONObject res = client.groupDelete(groupId, options);
	    System.out.println(res.toString(2));
	}
  	
  	public JSONObject deleteUser(String groupId , String userId) {
  		AipFace client = new AipFace(appId, apiKey, secretKey);
  	    // 传入可选参数调用接口
  	    HashMap<String, String> options = new HashMap<String, String>();
  	    // 删除用户
  	    JSONObject res = client.deleteUser(groupId, userId, options);
  	    System.out.println(res.toString(2));
  	    return res;
  	}
  	/**
  	 *  人脸删除
  	 * @param userId	人脸所在的用户id
  	 * @param groupId	人脸所在的用户组id
  	 * @param faceToken	人脸的faceToken
  	 * @return 
  	 */
	public JSONObject delete(String userId,String groupId,String faceToken) {
		AipFace client = new AipFace(appId, apiKey, secretKey);
		// 传入可选参数调用接口
		
	    HashMap<String, String> options = new HashMap<String, String>();
	    // 人脸删除
	    JSONObject res = client.faceDelete(userId, groupId, faceToken, options);
	    System.out.println(res.toString(2));
	    return res;
	}
	/**
	 * 用户列表查询
	 * @param userId	用户id
	 * @param groupId	用户组id
	 */
	public JSONObject queryUser(String userId , String groupId) {
		AipFace client = new AipFace(appId, apiKey, secretKey);
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    // 用户信息查询
	    JSONObject res = client.getUser(userId, groupId, options);
	    System.out.println(res.toString(2));
		return res;

	}
}
