package com.painye.usercenter.service;

import com.painye.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pan
 * @date 2024/7/2 7:44
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

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
        Long l = userService.userRegister(userAccount, password, checkPassword);
        System.out.println(l);
    }
}