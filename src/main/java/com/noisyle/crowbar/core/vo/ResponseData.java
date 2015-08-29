package com.noisyle.crowbar.core.vo;

/**
 * Created by Justin on 2015/7/21.
 */
public class ResponseData {
    public enum Status {
        SUCCESS, INFO, ERROR
    }

    private Status status;
    private Object data;
    private String message;
    private int code;

    public static ResponseData buildResponseData(Status status, Object data, String message, int code) {
        ResponseData responseData = new ResponseData();
        responseData.status = status;
        responseData.data = data;
        responseData.message = message;
        responseData.code = code;

        return responseData;
    }

    public static ResponseData buildSuccessResponse(Object data) {
        return ResponseData.buildResponseData(Status.SUCCESS, data, null, 0);
    }

    public static ResponseData buildErrorResponse(String message) {
        return ResponseData.buildResponseData(Status.ERROR, null, message, 0);
    }

    public static ResponseData buildErrorResponse(String message, int code) {
        return ResponseData.buildResponseData(Status.ERROR, null, message, code);
    }

    public Status getStatus() {
        return this.status;
    }

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public static void main(String[] args) {
        String str = "Hello world!";
        ResponseData.buildSuccessResponse(str);
        ResponseData.buildErrorResponse(str);
        ResponseData.buildErrorResponse(str, 1);
    }

}
