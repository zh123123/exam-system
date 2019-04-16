package com.Z.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * 保存目标资源到本地
 * @author H
 *
 */
public class SaveResourceUtil {
	
	/**
	 * 
	 * @param resPath	网络资源路径	
	 * @param diskPath	保存到本地磁盘的路径
	 * @throws IOException
	 * 
	 */
	public static void saveImg(String resourcePath, String diskPath) {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(resourcePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			is = conn.getInputStream();
			File file = new File(diskPath);
			if(file.getParentFile() != null || !file.getParentFile().isDirectory()) {
				file.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file);
			IOUtils.copy(is, fos);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fos !=null ) {
					fos.flush();
					fos.close();
				}
				if(is != null)
					is.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
