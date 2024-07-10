package com.painye.usercenter.exception;

public enum ErrorCode {

    SUCCESS(200, "success"),
    PARAMETER_ERROR(40000, "请求参数错误"),
    PARAMETER_NULL(4001, "请求参数为空"),
    NOT_LOGIN(40100, "未登录"),
    NOT_AUTH(40101, "无权限"),
    SYSTEM_ERROR(50000, "系统内部错误");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误码信息
     */
    private final String message;


    /**
     * 错误详情
     */
    private final String detail;

    ErrorCode(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    ErrorCode(int code, String message) {
        this(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }


}
