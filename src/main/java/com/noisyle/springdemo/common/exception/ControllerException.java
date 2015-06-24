package com.noisyle.springdemo.common.exception;

import com.noisyle.springdemo.common.web.ResponseMessage;


public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = -3301055013047352027L;

	public ControllerException(String string, Throwable e, ResponseMessage responseMessage) {
		super(string, e);
		responseMessage.setErrorMessage(string);
	}

	public ControllerException(String string, ResponseMessage responseMessage) {
		super(string);
		responseMessage.setErrorMessage(string);
	}
}
