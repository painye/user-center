package com.painye.usercenter.constants;
/**
 * @author pan
 * @date 2024/7/3 8:40
 */

/**
 * @author pan
 * @ClassName : com.painye.usercenter.constants.Constant
 * @Description : 常数类
 * @date 2024/7/3 8:40
 */
public class Constant {

    /**
     * 加密用到的盐
     */
    public static final String DIGEST_SALT = "salt";
    
    public static final String LOGIN_USER_MESSAGE = "LOGIN_USER_MESSAGE";

    public static final int LOGIN_ADMIN = 1;

    public static final int LOGIN_USER = 0;


    /**
     *响应------------------------------------------------
     */

    public static final String RESULT_STATUS_SUCCESS = "200";

    public static final String RESULT_STATUS_FAIL =  "500";
}
