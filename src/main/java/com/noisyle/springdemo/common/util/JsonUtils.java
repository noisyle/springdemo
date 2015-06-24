package com.noisyle.springdemo.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtils {
	/**
	 * 转换为json字符串
	 */
	public static String toJson(Object obj) {
		ObjectMapper om = new ObjectMapper();
		String resultStr = "";
		try {
			resultStr = om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}
}
