package com.painye.usercenter.service;

import com.painye.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

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
     * <p>用户登录：
     * <p>1、校验参数
     * <p>2、查库
     * <p>3、信息脱敏
     * <p>4、将登录成功的用户信息保存在session之中
     *
     * @param userAccount
     * @param userPassword
     * @param session
     * @return httpServletRequest
     * @throws Exception
     */
    User doLogin(String userAccount, String userPassword, HttpSession session) throws Exception;

    /**
     * <p>用户查询
     * 1、校验参数
     * 2、检查权限
     * 3、查库
     *
     * @param userAccount
     * @param session
     * @return
     * @throws Exception
     */
    List<User> searchUser(String userAccount, HttpSession session) throws Exception;

    /**
     * <p>用户查询
     * 1、校验参数
     * 2、检查权限
     * 3、删除用户数据
     *
     * @param userId
     * @param session
     * @return
     * @throws Exception
     */
    void revokeUser(Long userId, HttpSession session) throws Exception;

    /**
     * 获取当前登录用户的信息
     *
     * @param session
     * @return
     * @throws Exception
     */
    User getCurrentUser(HttpSession session) throws Exception;

    /**
     * 获取所有未被删除的用户
     *
     * @return
     * @throws Exception
     */
    List<User> searchAll() throws Exception;
}
