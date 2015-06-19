package com.noisyle.springdemo.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtils {
	/**
	 * 转换为json对象
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
//		JSONObject jsonObject = JSONObject.fromObject(obj);
//		String resultStr = String.valueOf(jsonObject);
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
