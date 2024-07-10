package com.painye.usercenter.utils;

import com.painye.usercenter.common.BaseResponse;
import com.painye.usercenter.exception.ErrorCode;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.utils.ResponseUtils
 * @Description : 类描述
 * @date 2024/7/10 20:28
 */
public class ResponseUtils {

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "success", data);
    }

    public static  BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), errorCode.getMessage());
    }
    public static  BaseResponse error(int code,  String msg, String detail) {
        return new BaseResponse<>(code, msg, detail);
    }

}
