package com.painye.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.painye.usercenter.model.domain.User;
import com.painye.usercenter.service.UserService;
import com.painye.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.painye.usercenter.constants.Constant;

/**
* @author dell
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-02 07:40:15
*/
@Service
public class  UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) throws Exception{
        //1. 校验用户的账户、密码、校验密码是否符合要求
        //  a. 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            throw new Exception("账号、密码和校验密码为空！");
        }
        //b. 账户不小于4位,c. 密码不小于8位
        if (userAccount.length()< 4 || userPassword.length() <8) {
            throw new Exception("账号长度小于4位，密码不小于8位！");
        }
        //  e. 账户不包含特殊字符
        String validPattern = "\\pP|\\PS|\\s+";
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
        String encryptPassword = DigestUtils.md5DigestAsHex((Constant.DIGEST_SALT+userPassword).getBytes());
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
}




