package com.noisyle.springdemo.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.noisyle.springdemo.common.util.JsonUtils;

public abstract class AbstractController {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	protected ResponseMessage responseMessage = new ResponseMessage();

	protected void addErrorMessage(String message) {
		responseMessage.setErrorMessage(message);
	}

	protected void addInfoMessage(String message) {
		responseMessage.setInfoMessage(message);
	}

	protected String getJsonString(Object obj) {
		return JsonUtils.toJson(obj);
	}
}