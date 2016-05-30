package com.centaline.trans.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * 
 * 文件名称为：URLAvailability.java
 * 
 * 文件功能简述： 描述一个URL或图片地址是否有效
 * 
 * 
 * 
 * 
 * */
public class URLAvailability {

	private static URL urlStr;
	private static HttpURLConnection connection;
	private static int state = -1;
	private static String succ;

	/**
	 * 功能描述 : 检测当前URL是否可连接或是否有效, 最多连接网络 5 次, 如果 5 次都不成功说明该地址不存在或视为无效地址.
	 * 
	 * @param url
	 *            指定URL网络地址
	 * 
	 * @return String
	 */
	public String isConnect(String url) {
		int counts = 0;
		succ = null;
		if (url == null || url.length() <= 0) {
			return succ;
		}
		while (counts < 2) {
			try {
				urlStr = new URL(url);
				connection = (HttpURLConnection) urlStr.openConnection();
				state = connection.getResponseCode();
				if (state == 200) {
					succ = connection.getURL().toString();
				}
				break;
			} catch (Exception ex) {
				counts++;
				continue;
			}
		}
		return succ;
	}
}