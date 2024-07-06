package com.painye.usercenter.model.dto;
/**
 * @author pan
 * @date 2024/7/6 8:35
 */

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.model.dto.UserRegisterRequest
 * @Description : userRegister接口中json请求内容的封装类
 * @date 2024/7/6 8:35
 */
@Data
public class     UserRegisterRequest implements Serializable {

    @Size(min = 4, max = 50)
    private String userAccount;
    @Size(min = 8, max = 8)
    private String userPassword;
    @Size(min = 8, max = 8)
    private String checkPassword;
}
