package com.painye.usercenter.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.common.BaseResponse
 * @Description : 返回消息封装
 * @date 2024/7/10 20:25
 */

@Data
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -9007692718324246627L;

    private int code;
    private String msg;
    private String description;
    private T data;

    public BaseResponse(int code, String msg, T data, String description) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(int code, String msg, T data) {
        this(code, msg, data, null);
    }

    public BaseResponse(int code, String msg, String description) {
        this(code, msg, null, description);
    }


}
