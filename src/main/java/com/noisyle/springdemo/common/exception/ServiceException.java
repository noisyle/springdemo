package com.noisyle.springdemo.common.exception;


public class ServiceException extends DAOException {
	
	private static final long serialVersionUID = 6343416547967148440L;

	public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
