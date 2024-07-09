package com.painye.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.painye.usercenter.model.domain.User;
import com.painye.usercenter.service.UserService;
import com.painye.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.painye.usercenter.constants.Constant;

/**
 * @author dell
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-07-02 07:40:15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;
    @Qualifier("httpServletRequest")
    @Autowired
    private ServletRequest httpServletRequest;

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) throws Exception {
        //1. 校验用户的账户、密码、校验密码是否符合要求
        //  a. 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new Exception("账号、密码和校验密码为空！");
        }
        //b. 账户不小于4位,c. 密码不小于8位
        if (userAccount.length() < 2 || userPassword.length() < 8) {
            throw new Exception("账号长度小于2位，密码不小于8位！");
        }
        //  e. 账户不包含特殊字符
        String validPattern = "[!@#$%^&*(),.?\":{}|<>~`\\\\/\\[\\]\\-_=+]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new Exception("账户包含特殊字符！");
        }
        //  f. 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new Exception("密码和校验密码不相同！");
        }
        //  d. 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new Exception("账户重复！");
        }
        //2. 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((Constant.DIGEST_SALT + userPassword).getBytes());
        //3. 向数据库中插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result = this.save(user);
        if (!result) {
            throw new Exception("插入用户数据失败");
        }
        logger.info(String.format("数据库中添加用户{'%s'}成功！", userAccount));
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpSession session) throws Exception {

        //1. 校验用户的账户、密码是否符合要求
        //  a. 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new Exception("账号、密码为空");
        }
        //b. 账户不小于4位,c. 密码不小于8位
        if (userAccount.length() < 2 || userPassword.length() < 8) {
            throw new Exception("账号长度小于2位，密码不小于8位！");
        }
        //  e. 账户不包含特殊字符
        String validPattern = "[!@#$%^&*(),.?\":{}|<>~`\\\\/\\[\\]\\-_=+]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new Exception("账户包含特殊字符！");
        }

        //2、对密码做摘要用于和数据库中的信息比对
        String encryptPassword = DigestUtils.md5DigestAsHex((Constant.DIGEST_SALT + userPassword).getBytes());

        //3、查询数据库
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new Exception("没有匹配的账号和密码！！！");
        }
        User safetyUser = user.toSafetyUser();

        //4、将用户登录信息保存在session中
        logger.info(String.format("本次请求中的session_id:{%s}", session.getId()));
        session.setAttribute(Constant.LOGIN_USER_MESSAGE, safetyUser);
        return safetyUser;
    }

    @Override
    public List<User> searchUser(String userAccount, HttpSession session) throws Exception {
        //1、校验参数
        if (StringUtils.isBlank(userAccount)) {
            throw new Exception("用户名为空!");
        }
        if (userAccount.length() < 2) {
            throw new Exception("用户名长度小于2");
        }
        //2、校验是否登录
        getLoginUserRole(session);
        //3、查询用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("userAccount", userAccount);
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    public void revokeUser(Long userId, HttpSession session) throws Exception {
        //1、校验参数
        if (userId < 0) {
            throw new Exception("userId < 0!");
        }
        //2、校验管理员权限
        int role = getLoginUserRole(session);
        if (role != Constant.LOGIN_ADMIN) {
            throw new Exception("登录用户权限不足，请登录管理员");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userId);
        int i = userMapper.deleteById(userId);
        if (i == 0) {
            throw new Exception("注销用户出错！");
        }
    }

    @Override
    public User getCurrentUser(HttpSession session) throws Exception {
        User user = new User();
        User loginUser = (User) session.getAttribute(Constant.LOGIN_USER_MESSAGE);
        if (loginUser == null) {
            throw new Exception("当前没有登录用户");
        }
        Long id = loginUser.getId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new Exception("{"+id+"}用户信息不存在");
        }
        return user.toSafetyUser();
    }

    @Override
    public List<User> searchAll() throws Exception {
        List<User> userList = null;
        userList = userMapper.selectList(null);
        return userList;
    }

    public int getLoginUserRole(HttpSession session) throws Exception{
        logger.info(String.format("本次请求中的session_id:{%s}", session.getId()));
        User loginUser = (User) session.getAttribute(Constant.LOGIN_USER_MESSAGE);
        if (loginUser == null) {
            throw new Exception("请先登录!");
        }
        int role =  loginUser.getUserRole();
        if (role != Constant.LOGIN_USER && role != Constant.LOGIN_ADMIN ) {
            throw new Exception(String.format("用户[%s]权限出错，请检查", loginUser.getUserName()));
        }
        return role;
    }
}




