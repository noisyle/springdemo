package com.noisyle.crowbar.core.constant;

public class SystemConstant {
	//数据状态
	public enum Status {
		INVALID(0, "无效"), VALID(1, "有效");
	
		private int value;
		private String text;
		
	    private Status(int value, String text) {
	        this.value = value;
	        this.text = text;
	    }
	    
	    public static Status getStatus(int value) {
	    	Status status = null;
	    	for(Status s : Status.values()){
	    		if(s.value == value){
	    			status = s;
	    		}
	    	}
	    	return status;
	    }
	    public int getValue() {
	    	return value;
	    }
	    public String getText() {
	    	return text;
	    }
	}
}
