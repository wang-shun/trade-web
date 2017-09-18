package com.centaline.scheduler.sync.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	/**
	 * 获取文件中的字符串内容 默认utf-8
	 * @return
	 * @throws IOException 
	 */
	public static String getFileInfo(String path){
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = null;
			while((line = reader.readLine()) != null){
				builder.append(line);
			}
			reader.close();
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
		return builder.toString();
	}
	
}
