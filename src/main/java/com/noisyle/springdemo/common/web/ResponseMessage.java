package com.noisyle.springdemo.common.web;

public class ResponseMessage {
	/**
	 * 业务提示信息
	 */
	private String content = "";
	private long type = Type.NONE;

	/**
	 * 业务提示信息类型
	 */
	public static class Type {
		final public static long NONE = 0;
		final public static long INFO = 1;
		final public static long ERROR = 2;
	}

	public void setErrorMessage(String errorMessage) {
		this.content = errorMessage;
		this.type = Type.ERROR;
	}

	public void setInfoMessage(String infoMessage) {
		this.content = infoMessage;
		this.type = Type.INFO;
	}

	public long getType() {
		return type;
	}

	public String getContent() {
		String temp = this.content;
		this.type = Type.NONE;
		this.content = "";
		return temp;
	}
}
