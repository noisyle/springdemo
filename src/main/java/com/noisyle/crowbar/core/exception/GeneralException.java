package com.noisyle.crowbar.core.exception;



public class GeneralException extends RuntimeException {
	
	private static final long serialVersionUID = 6343416547967148440L;
	
	private Object[] args = null;

	public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public GeneralException(String message, Object... args) {
    	super(message);
    	this.args = args;
    }
    
    public GeneralException(String message, Throwable cause, Object... args) {
    	super(message, cause);
    	this.args = args;
    }

    public Object[] getArgs() {
    	return args;
	}
}
