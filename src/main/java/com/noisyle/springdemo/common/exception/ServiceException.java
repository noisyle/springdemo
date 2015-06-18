package com.noisyle.springdemo.common.exception;


public class ServiceException extends DAOException {
	private static final long serialVersionUID = -1890736872783817176L;
	
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
