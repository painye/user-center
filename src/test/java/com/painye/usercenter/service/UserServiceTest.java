package com.painye.usercenter.service;

import com.painye.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.View;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pan
 * @date 2024/7/2 7:44
 */
@SpringBootTest
public class UserServiceTest {

    Logger log = Logger.getLogger(UserServiceTest.class.getName());

    @Resource
    private UserService userService;
    @Autowired
    private View error;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUserName("t1");
        user.setUserAccount("t1");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("t1");
        user.setPhone("111111111");
        user.setEmail("1@2.com");
        user.setUserStatus(0);
        user.setIsDelete(0);
        user.setUserRole(0);
        user.setPlanetCode("");
        boolean save = userService.save(user);
        Assertions.assertTrue(save);
    }

    @Test
    public void testUserRegister(){
        String userAccount = "t2";
        String password = "123456";
        String checkPassword = "123456";
        Long l = 0L;
        try {
            l = userService.userRegister(userAccount, password, checkPassword);
        } catch (Exception e) {
            log.log(Level.SEVERE, "用户注册失败！", e);
        }
        System.out.println(l);
    }
}