package com.painye.usercenter.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.model.dto.UserLoginRequest
 * @Description : 用户登录请求json
 * @date 2024/7/6 10:17
 */
@Data
public class UserLoginRequest implements Serializable {
    @Size(min = 4, max = 50)
    private String userAccount;
    @Size(min = 8, max = 8)
    private String userPassword;
}
