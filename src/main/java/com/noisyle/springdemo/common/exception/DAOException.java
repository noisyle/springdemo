package com.noisyle.springdemo.common.exception;

import org.springframework.dao.DataAccessException;

public class DAOException extends DataAccessException {
	private static final long serialVersionUID = -1890736872783817176L;
	
    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
