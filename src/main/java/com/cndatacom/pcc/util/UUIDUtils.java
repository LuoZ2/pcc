package com.cndatacom.pcc.util;

import java.util.UUID;

public class UUIDUtils {
	static String uuidString = null;
	/**
	 *  返回的是带-的字符串
	 * @return
	 */
	public static String createUUID1() {
		uuidString = UUID.randomUUID().toString();
		return uuidString;
	}
	
	/**
	 *  返回的是不带-的字符串
	 * @return
	 */
	public static String createUUID2() {
		uuidString = UUID.randomUUID().toString();
		return uuidString.replace("-", "");
	}
}
