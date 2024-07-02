package com.painye.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.painye.usercenter.model.domain.User;
import com.painye.usercenter.service.UserService;
import com.painye.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-02 07:40:15
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




