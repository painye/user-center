package com.painye.usercenter.service;

import com.painye.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dell
 * @description 用户服务
 * @createDate 2024-07-02 07:40:15
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册：
     * 1、校验参数
     * 2、用户信息加密
     * 3、用户数据入库
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 新用户id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword) throws Exception;


    /**
     * 用户登录：
     * 1、校验参数
     * 2、查库
     * 3、信息脱敏
     *
     * @param userAccount
     * @param userPassword
     * @return
     * @throws Exception
     */
    User doLogin(String userAccount, String userPassword) throws Exception;
}
