package com.painye.usercenter.exception;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.exception.BusinessException
 * @Description : 自定义业务类
 * @date 2024/7/10 20:47
 */
public class BusinessException extends Exception {

    private final int code;

    private final String detail;


    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.detail = errorCode.getDetail();
    }

    public BusinessException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.detail = detail;
    }

    public BusinessException(int code, String msg, String detail) {
        super(msg);
        this.code = code;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }
}
