package com.painye.usercenter.model.vo;

import lombok.Data;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.model.vo.UserResponse
 * @Description : 服务器返回的响应
 * @date 2024/7/6 10:25
 */
@Data
public class UserResponse {
    private String resultStatus;
    private String resultMessage;
    private Object body;
}
